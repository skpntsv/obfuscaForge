package ru.nsu.skopintsev

data class ObfuscationRule(val original: String, val obfuscated: String)

fun String.isNotKeyword(): Boolean {
    val keywords = setOf(
        "abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
        "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
        "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
        "interface", "long", "native", "new", "package", "private", "protected", "public",
        "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
        "throw", "throws", "transient", "try", "void", "volatile", "while",
        "main", "String", "System.out.println"
    )
    return !keywords.contains(this)
}
