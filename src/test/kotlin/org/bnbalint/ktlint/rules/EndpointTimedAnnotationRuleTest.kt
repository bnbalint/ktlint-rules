package org.bnbalint.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import org.junit.jupiter.api.Test

// use env var KTLINT_UNIT_TEST_DUMP_AST=on to print the AST

class EndpointTimedAnnotationRuleTest {
    private val wrappingRuleAssertThat = assertThatRule { EndpointTimedAnnotationRule() }


    @Test
    fun endpointTimedAnnotationTest() {

        val code =
            """
            @RequestMapping(
                value = ["/months"],
                method = [RequestMethod.GET],
                produces = [JSON]
            )
            fun getFeedback(): ResponseEntity<*> {
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasLintViolationWithoutAutoCorrect(1, 2, "No Timed annotation found for endpoint")
    }

    @Test
    fun endpointTimedAnnotationTest_disable() {

        val code =
            """
            // comment!
            @Suppress("ktlint:bnbalint-ktlint-rules:endpoint-timed-annotation")
            @RequestMapping(value = ["/test"], method = [RequestMethod.GET])
            fun testFunction(
                @RequestParam(param) param: String?,
                @CookieValue(value = idKey, required = false) id: String?
            ): ResponseEntity<*> {
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }

    @Test
    fun endpointTimedAnnotationTest_disableWithCommentAbove() {

        val code =
            """
            // comment!
            @Suppress("ktlint:bnbalint-ktlint-rules:endpoint-timed-annotation")
            @RequestMapping(value = ["/test"], method = [RequestMethod.HEAD], params = ["ok"])
            fun defaultHead(@RequestParam("ok") ok: String?): ResponseEntity<*> {
                return ResponseEntity<Any>(HttpStatus.OK)
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }

    @Test
    fun endpointTimedAnnotationTest_disable_success() {

        val code =
            """
            @Suppress("ktlint:bnbalint-ktlint-rules:endpoint-timed-annotation")
            @RequestMapping(value = ["/test"], method = [RequestMethod.HEAD], params = ["ok"])
            fun defaultHead(@RequestParam("ok") ok: String?): ResponseEntity<*> {
                return ResponseEntity<Any>(HttpStatus.OK)
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }


    @Test
    fun endpointTimedAnnotationTest_noError() {

        val code =
            """
            @Timed
            @RequestMapping(
                value = ["/months"],
                method = [RequestMethod.GET],
                produces = [JSON]
            )
            fun getFeedback(): ResponseEntity<*> {
            }
            """.trimIndent()

        wrappingRuleAssertThat(code)
            .hasNoLintViolations()
    }
}