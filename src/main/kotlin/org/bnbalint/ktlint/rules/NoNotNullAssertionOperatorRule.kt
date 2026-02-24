package org.bnbalint.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.KtNodeTypes.OPERATION_REFERENCE
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * Detects usage of !!
 */
class NoNotNullAssertionOperatorRule :
    Rule(
        ruleId = RuleId("$CUSTOM_RULE_SET_ID:no-not-null-assertion-operator"),
        about =
        About(
            maintainer = "bnbalint",
            repositoryUrl = "https://github.com/bnbalint/ktlint-rules"
        ),
    ) {
    override fun beforeVisitChildNodes(
        node: ASTNode,
        autoCorrect: Boolean,
        emit: (offset: Int, errorMessage: String, canBeAutoCorrected: Boolean) -> Unit,
    ) {
        if (node.elementType == OPERATION_REFERENCE) {
            val value = node.firstChildNode.text
            if (value.length == 2 && value.contains("!!")) {
                emit(
                    node.startOffset,
                    "Usage of NotNullAssertion operator",
                    false,
                )
            }
        }
    }
}
