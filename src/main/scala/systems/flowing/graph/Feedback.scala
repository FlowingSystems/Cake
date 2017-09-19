package systems.flowing.graph

trait Feedback {
    this: Hierarchy =>

    val nodes: List[Node with Order with Error]

    /**
     * Same as Hierarchy.signal, but indices are in reverse order
     */
    def feedback(i: Int): Unit

    override def output = {
        propagate
        backpropagate
        List()
    }

    private def backpropagate = {
    }
}