package ru.nsu.skopintsev

import kotlin.test.Test
import kotlin.test.assertEquals

class ObfuscateTest {

    @Test
    fun testObfuscateAndDeobfuscate() {
        val originalCode = """
            public class Test {
                public static void main(String[] args) {
                    String message = "Hello, World!";
                    System.out.println(message);
                    
                    int a = 5;
                    int b = 10;
                    
                    System.out.println(a + b);
                }
            }
        """.trimIndent()

        val rules = mutableListOf<ObfuscationRule>()
        val config = ObfuscateConfig(encodeStrings = true, obfuscateVariables = true)
        val obfuscatedCode = obfuscateJavaCode(originalCode, rules, config)
        val deobfuscatedCode = deobfuscateJavaCode(obfuscatedCode, rules)

        assertEquals(originalCode, deobfuscatedCode)
    }
}
