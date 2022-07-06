import javax.management.ValueExp

interface Node
interface ValueExpressionNode : Node

interface PureTermNode : ValueExpressionNode

class NumberNode(val data: Number) : PureTermNode {
    override fun toString(): String = data.toString()
}

class InfNode() : PureTermNode {
    override fun toString() = "\\infty"
}

class VariableNode(val character: Char) : PureTermNode

class IntegralNode(
    val from: ValueExpressionNode,
    val to: ValueExpressionNode,
    val what: ValueExpressionNode,
    val by: VariableNode
) : PureTermNode {
    override fun toString() = "\\int_{$from}^{$to}$what d$by"
}

class SumNode(
    val from: StatementNode,
    val to: ValueExpressionNode,
    val what: ValueExpressionNode
) : PureTermNode {
    override fun toString() = "\\sum_{$from}^{$to}$what"
}

class ParaNode(val opening: Char, val closing: Char, val content: Node) : PureTermNode {
    override fun toString() = "\\left$opening $content \\right$closing"
}

interface StatementNode : PureTermNode

class RelationStatementNode(val relation: Char, val left: ValueExpressionNode, val right: ValueExpressionNode) :
    StatementNode {
    override fun toString() = "$left $relation $right"
}

class Fraction(val top: ValueExpressionNode, val bottom: ValueExpressionNode) : PureTermNode {
    override fun toString() = "\\frac{$top}{$bottom}"
}

class Derivative(val by: Char) : PureTermNode {
    override fun toString() = "\\frac{d}{d$by}"
}

interface UnaryOperator : Node

class PartialDerivative(val by: Char) : UnaryOperator {
    override fun toString() = "\\frac{\\partial}{\\partial $by}"
}

class PowerNode(val bottom: ValueExpressionNode, val top: ValueExpressionNode) : PureTermNode {
    override fun toString() = "{$bottom}^{$top}"
}


class TermNode(val unaryOperator: UnaryOperator?, val node: PureTermNode) : Node {
    override fun toString(): String {
        if (unaryOperator == null) {
            return node.toString()
        }
        return "$unaryOperator $node"
    }
}