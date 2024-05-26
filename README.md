# ObfuscaForge - {Обфускатор Java-кода}

Этот проект представляет собой обфускатор Java-кода, который позволяет скрыть исходный код Java-проекта путем изменения названия переменных и кодирования строковых литералов.

## Как это работает

Обфускатор Java-кода написан на языке Kotlin и использует регулярные выражения для поиска и замены строковых литералов и идентификаторов переменных в исходном Java-коде. Он также поддерживает сохранение правил обфускации в JSON-файле для последующей деобфускации.

## Настройка и запуск

### 1. **Клонирование репозитория**

   ```bash
   git clone https://github.com/skpntsv/obfuscaForge.git
   cd obfuscator
   ```

### 2. **Настройка конфигурации**

   Создайте файл `config.json` в корневой директории проекта и укажите параметры обфускации и деобфускации:
   
   ```json
   {
       "action": "obfuscate", 
       "projectPath": "path/to/project",
       "obfuscate": {
           "encodeStrings": true,
           "obfuscateVariables": true
       },
       "deobfuscate": {
           "jsonFilePath": "path/to/rules.json"
       }
   }
   ```

   - `action`: Действие, которое нужно выполнить (`obfuscate` или `deobfuscate`).
   - `projectPath`: Путь к директории с Java-проектом.
   - `obfuscate.encodeStrings`: Обфускация строковых литералов (`true`/`false`).
   - `obfuscate.obfuscateVariables`: Обфускация переменных (`true`/`false`).
   - `deobfuscate.jsonFilePath`: Путь к JSON-файлу с правилами обфускации.

### 3. **Запуск**

   Запустите программу, выполнив следующую команду в корневой директории проекта:

   ```bash
   ./gradlew build
   ./gradlew run
   ```

   Программа прочитает конфигурацию из файла `config.json` и выполнит указанное действие (обфускацию или деобфускацию) для указанного Java-проекта.

## Функции

- `obfuscateJavaCode`: Функция обфускации Java-кода, которая изменяет строковые литералы и идентификаторы переменных в исходном коде.
- `deobfuscateJavaCode`: Функция деобфускации Java-кода, которая восстанавливает исходный код на основе правил обфускации, сохраненных в JSON-файле.
- `obfuscateString`: Функция кодирования строковых литералов в формат Unicode для обфускации.
- `generateRandomVariableName`: Функция генерации случайных идентификаторов переменных для обфускации.

## Пример использования

Пример конфигурации `config.json` для обфускации:

```json
{
    "action": "obfuscate",
    "projectPath": "src/test/resources/testProject",
    "obfuscate": {
        "encodeStrings": true,
        "obfuscateVariables": true
    },
    "deobfuscate": {
        "jsonFilePath": "src/test/resources/rules.json"
    }
}
```

## Тестирование

Для запуска тестов используйте команду:

```bash
./gradlew test
```