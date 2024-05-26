package ru.nsu.skopintsev

import java.io.File
import java.nio.file.Files
import com.google.gson.Gson
import java.security.SecureRandom

fun obfuscate(directoryPath: String, config: ObfuscateConfig) {
    val directory = File(directoryPath)
    if (!directory.exists() || !directory.isDirectory) {
        println("Указанная директория не существует или не является директорией.")
        return
    }

    val files = directory.walkTopDown().filter { it.isFile && it.extension == "java" }
    val gson = Gson()
    val allRules = mutableListOf<ObfuscationRule>()

    files.forEach { file ->
        println("Обфускация файла: ${file.nameWithoutExtension}: ${file.absolutePath}")
        val originalContent = file.readText()
        val rules = mutableListOf<ObfuscationRule>()
        val obfuscatedContent = obfuscateJavaCode(originalContent, rules, config)
        allRules.addAll(rules)
        file.writeText(obfuscatedContent)
        println("Обфускация файла ${file.name} прошла успешно")
    }

    val json = gson.toJson(allRules)
    val rulesFile = File(directory, "rules.json")
    Files.write(rulesFile.toPath(), json.toByteArray())
    println("Правила обфускации сохранены в ${rulesFile.absolutePath}")
}

fun obfuscateJavaCode(code: String, rules: MutableList<ObfuscationRule>, config: ObfuscateConfig): String {
    var obfuscatedCode = code

    if (config.encodeStrings) {
        val stringLiteralRegex = "\"(?:\\\\.|[^\\\\\"])*\"".toRegex()
        stringLiteralRegex.findAll(code).forEach { matchResult ->
            val originalString = matchResult.value
            val encodedString = obfuscateString(originalString)
            obfuscatedCode = obfuscatedCode.replace(originalString, encodedString)
            rules.add(ObfuscationRule(originalString, encodedString))
            println("\t$originalString -> $encodedString")
        }
    }

    if (config.obfuscateVariables) {
        val variableDeclarationRegex = "\\b(int|float|double|char|String|boolean|byte|short|long)\\b\\s+([a-zA-Z_][a-zA-Z0-9_]*)".toRegex()
        variableDeclarationRegex.findAll(code).forEach { matchResult ->
            val originalVariable = matchResult.groupValues[2]
            if (rules.none { it.original == originalVariable }) {
                val obfuscatedVariable = generateRandomVariableName(8)
                rules.add(ObfuscationRule(originalVariable, obfuscatedVariable))
                obfuscatedCode = obfuscatedCode.replace("\\b$originalVariable\\b".toRegex(), obfuscatedVariable)
                println("\t$originalVariable -> $obfuscatedVariable")
            } else {
                val rule = rules.find { it.original == originalVariable }
                if (rule != null) {
                    obfuscatedCode = obfuscatedCode.replace("\\b$originalVariable\\b".toRegex(), rule.obfuscated)
                }
            }
        }
    }

    return obfuscatedCode
}

fun obfuscateString(input: String): String {
    val builder = StringBuilder()
    for (char in input) {
        builder.append("\\u").append(String.format("%04x", char.code))
    }
    return builder.toString()
}

fun generateRandomVariableName(length: Int): String {
    val firstCharSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_"
    val charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val random = SecureRandom()

    val firstChar = firstCharSet[random.nextInt(firstCharSet.length)]
    val otherChars = (1 until length)
        .map { charSet[random.nextInt(charSet.length)] }
        .joinToString("")

    return firstChar + otherChars
}
