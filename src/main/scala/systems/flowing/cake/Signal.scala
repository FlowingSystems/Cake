package systems.flowing.cake

trait Signal {
    this: Hierarchy =>

    /**
     * Same as Flow.node, but indices are guaranteed to be sorted by order
     */
    def signal(i: Int): Unit

    def propagate = nodes.sortBy(_.order).indices map signal
}