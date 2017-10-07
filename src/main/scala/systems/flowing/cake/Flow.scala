package systems.flowing.cake

trait Flow {
    this: Nodes[_] with Directed =>

    def flow(f: Int=>Unit) =
        nodes.zipWithIndex.
        filter(!_._1.isInstanceOf[Input]).
        map(_._2).
        foreach(f)
}