package ru.nsu.skopintsev

import com.google.gson.Gson
import java.io.File
import java.io.IOException
import java.nio.file.Files

fun main() {
    val config = loadConfig("config.json")
    when (config.action) {
        "obfuscate" -> {
            val projectPath = config.projectPath
            if (config.obfuscate.encodeStrings || config.obfuscate.obfuscateVariables) {
                obfuscate(projectPath, config.obfuscate)
            } else {
                println("Нет параметров для обфускации.")
            }
        }
        "deobfuscate" -> {
            val projectPath = config.projectPath
            val jsonFilePath = config.deobfuscate.jsonFilePath
            deobfuscate(projectPath, jsonFilePath)
        }
        else -> println("Неверный выбор действия\n" +
                "obfuscate - обфускация\n" +
                "deobfuscate - деобфускация")
    }
}

fun loadConfig(configFilePath: String): Config {
    val gson = Gson()
    try {
        val json = Files.readString(File(configFilePath).toPath())
        return gson.fromJson(json, Config::class.java)
    } catch (e: IOException) {
        throw Exception(e.message)
    }
}
