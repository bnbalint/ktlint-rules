package org.bnbalint.ktlint.rules

import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes

/**
 * Crude detection of the usage of the print function
 */
class NoPrintRule :
    Rule(
        ruleId = RuleId("$CUSTOM_RULE_SET_ID:no-print"),
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
        if (node.elementType == KtStubElementTypes.REFERENCE_EXPRESSION) {
            val name = node.firstChildNode.text
            if (name.length == 5 && name.contains("print")) {
                emit(
                    node.startOffset,
                    "Usage of print function",
                    false,
                )
            }
        }
    }
}