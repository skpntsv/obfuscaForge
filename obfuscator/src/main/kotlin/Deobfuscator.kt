package ru.nsu.skopintsev

import java.io.File
import java.nio.file.Files
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun deobfuscate(directoryPath: String, jsonFilePath: String) {
    val directory = File(directoryPath)
    if (!directory.exists() || !directory.isDirectory) {
        println("Указанная директория не существует или не является директорией.")
        return
    }

    val jsonFile = File(jsonFilePath)
    if (!jsonFile.exists() || !jsonFile.isFile) {
        println("Указанный JSON файл не существует или не является файлом.")
        return
    }

    val gson = Gson()
    val json = String(Files.readAllBytes(jsonFile.toPath()))
    val rules: List<ObfuscationRule> = gson.fromJson(json, object : TypeToken<List<ObfuscationRule>>() {}.type)

    val files = directory.walkTopDown().filter { it.isFile && it.extension == "java" }

    println("Деобфускация проекта[${directory.name}]...")
    files.forEach { file ->
        println("Деобфускация файла: ${file.nameWithoutExtension}: ${file.absolutePath}")
        val obfuscatedContent = file.readText()
        val deobfuscatedContent = deobfuscateJavaCode(obfuscatedContent, rules)
        file.writeText(deobfuscatedContent)
        println("Деобфускация файла ${file.name} прошла успешно")
    }
    println("Деобфускация проекта[${directory.name}] завершена!")
}

fun deobfuscateJavaCode(code: String, rules: List<ObfuscationRule>): String {
    var deobfuscatedCode = code
    rules.forEach { rule ->
        deobfuscatedCode = deobfuscatedCode.replace(rule.obfuscated, rule.original)
    }
    return deobfuscatedCode
}
