package systems.flowing.cake

trait Feedback {
    this: Nodes[_ <: Order] with Hierarchy =>

    /**
     * Same as signal, but indices are in reverse order
     */
    def feedback(f: Int=>Unit) =
        nodes.zipWithIndex.
        filter(!_._1.isInstanceOf[Input]).
        sortBy(-_._1.order).
        map(_._2).
        foreach(f)
}