package systems.flowing.cake

trait Signal {
    this: Nodes[_ <: Order] with Hierarchy =>

    /**
     * Same as Flow.node, but indices are guaranteed to be sorted by order
     */
    def signal(i: Int): Unit

    def propagate(c: Any=>Boolean) = nodes.zipWithIndex.
        sortBy(ni => ni._1.order).
        filter(ni => c(ni._1)).
        map(ni => ni._2).
        foreach(signal)
}