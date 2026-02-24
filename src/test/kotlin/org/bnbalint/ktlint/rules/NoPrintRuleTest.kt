package org.bnbalint.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.junit.jupiter.api.Test

// use env var KTLINT_UNIT_TEST_DUMP_AST=on to print the AST

class NoPrintRuleTest {
    private val wrappingRuleAssertThat = assertThatRule { NoPrintRule() }


    @Test
    fun noPrintRule() {

        val code =
            """
            fun test(){
                print("roger roger!")
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasLintViolationWithoutAutoCorrect(2, 5, "Usage of print function")
    }

    @Test
    fun noPrintRule_noError() {

        val code =
            """
            fun test(){
                log.info("roger roger!")
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }
}