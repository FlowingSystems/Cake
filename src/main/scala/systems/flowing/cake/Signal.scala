package systems.flowing.cake

trait Signal {
    this: Nodes[_ <: Order] with Hierarchy =>

    /**
     * Same as Flow.node, but indices are guaranteed to be sorted by order
     */
    def signal(f: Int=>Unit) =
        nodes.zipWithIndex.
        filter(!_._1.isInstanceOf[Input]).
        sortBy(_._1.order).
        map(_._2).
        foreach(f)
}