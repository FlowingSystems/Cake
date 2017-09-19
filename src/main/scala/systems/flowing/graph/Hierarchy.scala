package systems.flowing.graph

trait Hierarchy extends IO {
    this: Graph =>

    /**
     * Same as Flow.node, but indices are guaranteed to be sorted by order
     */
    def signal(i: Int): Unit

    val nodes: List[Node with Order]

    val order = nodes.map(_.asInstanceOf[Node with Order].order)

    override def output = {
        propagate
        outputStates
    }

    private[graph] def propagate = {
    }
}