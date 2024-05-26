package ru.nsu.skopintsev

import java.util.*

fun main() {
    println("Выберите действие: ")
    println("1. Обфусцировать")
    println("2. Деобфусцировать")

    val input = Scanner(System.`in`)
    when (input.nextInt()) {
        1 -> {
            println("Введите путь к директории с Java проектом:")
            val directoryPath = readln()
            obfuscate(directoryPath)
        }

        2 -> {
            println("Введите путь к директории с Java проектом:")
            val directoryPath = readln()

            println("Введите путь к JSON файлу с правилами обфускации:")
            val jsonFilePath = readln()
            deobfuscate(directoryPath, jsonFilePath)
        }
        else -> println("Неверный выбор действия.")
    }
}
