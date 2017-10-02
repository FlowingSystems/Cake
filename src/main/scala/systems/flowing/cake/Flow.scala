package systems.flowing.cake

trait Flow {
    this: Nodes[_] with Directed =>

    def node(i: Int): Unit

    def process = nodes.zipWithIndex.
        filter (!_._1.isInstanceOf[Input]).
        foreach { x => node(x._2) }
}