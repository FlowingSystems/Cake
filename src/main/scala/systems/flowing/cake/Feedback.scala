package systems.flowing.cake

trait Feedback {
    this: Nodes[_ <: Order] with Hierarchy =>

    /**
     * Same as signal, but indices are in reverse order
     */
    def feedback(i: Int): Unit

    def backpropagate(c: Any=>Boolean) = nodes.zipWithIndex.
        sortBy(ni => -ni._1.order).
        filter(ni => c(ni._1)).
        map(ni => ni._2).
        foreach(feedback)
}