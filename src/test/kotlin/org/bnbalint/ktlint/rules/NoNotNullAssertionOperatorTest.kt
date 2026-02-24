package org.bnbalint.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.junit.jupiter.api.Test

// use env var KTLINT_UNIT_TEST_DUMP_AST=on to print the AST

class NoNotNullAssertionOperatorTest {
    private val wrappingRuleAssertThat = assertThatRule { NoNotNullAssertionOperatorRule() }


    @Test
    fun noNonNullAssertionOperatorRule() {

        val code =
            """
            fun runningCode(){
                val stringVar : String? = null
                stringVar!!.substring(0,1)
                
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasLintViolationWithoutAutoCorrect(3, 14, "Usage of NotNullAssertion operator")
    }

    @Test
    fun noNonNullAssertionOperatorRule_noError() {

        val code =
            """
            fun runningCode(){
                val stringVar : String? = null
                stringVar?.substring(0,1)
                
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }
}