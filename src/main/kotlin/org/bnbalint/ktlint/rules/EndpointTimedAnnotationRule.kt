package org.bnbalint.ktlint.rules


import com.pinterest.ktlint.rule.engine.core.api.ElementType.CONSTRUCTOR_CALLEE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.IDENTIFIER
import com.pinterest.ktlint.rule.engine.core.api.ElementType.MODIFIER_LIST
import com.pinterest.ktlint.rule.engine.core.api.ElementType.REFERENCE_EXPRESSION
import com.pinterest.ktlint.rule.engine.core.api.ElementType.TYPE_REFERENCE
import com.pinterest.ktlint.rule.engine.core.api.ElementType.USER_TYPE
import com.pinterest.ktlint.rule.engine.core.api.Rule
import com.pinterest.ktlint.rule.engine.core.api.RuleId
import org.jetbrains.kotlin.KtNodeTypes.ANNOTATION_ENTRY
import org.jetbrains.kotlin.KtNodeTypes.FUN
import org.jetbrains.kotlin.com.intellij.lang.ASTNode

/**
 * When @RequestMapping is Present, also require that @Timed is present
 */
class EndpointTimedAnnotationRule :
    Rule(
        ruleId = RuleId("$CUSTOM_RULE_SET_ID:endpoint-timed-annotation"),
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
        // if the node is a function, grab the names of all the annotations
        if (node.elementType == FUN) {
            val annotationNames = mutableListOf<String>()

            // grab the MODIFIER_LIST child node (annotations live here)
            val modifierNode = node.findChildByType(MODIFIER_LIST)

            // store the offset for RequestMapping here
            var requestMappingLine = 0

            if (modifierNode != null) {
                val children = modifierNode.getChildren(null)

                for (child in children) {
                    // grab ANNOTATION_ENTRY nodes
                    if (child.elementType == ANNOTATION_ENTRY) {
                        // grab the name from the nested structure
                        val identifier = child.findChildByType(CONSTRUCTOR_CALLEE)
                            ?.findChildByType(TYPE_REFERENCE)
                            ?.findChildByType(USER_TYPE)
                            ?.findChildByType(REFERENCE_EXPRESSION)
                            ?.findChildByType(IDENTIFIER)

                        val name = identifier?.text

                        if (name != null) {
                            annotationNames.add(name)

                            // save the offset, so we can link the error to the correct line
                            if (name == "RequestMapping"){
                                requestMappingLine = identifier.startOffset
                            }
                        }
                    }
                }
            }

            // this is an endpoint
            if ("RequestMapping" in annotationNames) {
                // endpoints must also have one of these security annotations
                if ("Timed" !in annotationNames ) {
                    emit(
                        requestMappingLine,
                        "No Timed annotation found for endpoint",
                        false,
                    )
                }
            }
        }
    }
}
