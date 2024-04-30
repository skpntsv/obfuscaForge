package ru.nsu.skopintsev

import java.io.File

fun main() {
    val directory = File("./src/test/kotlin")
    val files = directory.walkTopDown().filter { it.isFile && it.extension == "java" }

    files.forEach { file ->
        println("Обфускация файла: ${file.nameWithoutExtension}: ${file.absolutePath}")
        val originalContent = file.readText()
        val obfuscatedContent = obfuscateJavaCode(originalContent)
        file.writeText(obfuscatedContent)
        println("Обфускация файла ${file.name} прошла успешно")
    }
}

fun obfuscateJavaCode(code: String): String {
    val stringLiteralRegex = "\"(?:\\\\.|[^\\\\\"])*\"".toRegex()

    var obfuscatedCode = code
    stringLiteralRegex.findAll(code).forEach { matchResult ->
        val originalString = matchResult.value
        val encodedString = obfuscateString(originalString)
        obfuscatedCode = obfuscatedCode.replace(originalString, encodedString)
        println("\t$originalString -> $encodedString")
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