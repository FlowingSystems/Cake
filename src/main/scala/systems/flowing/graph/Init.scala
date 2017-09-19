package systems.flowing.graph

trait Init {
    this: Graph =>
    val init: Initializer
    init.init(this)
}

trait Initializer {
    def init(graph: Graph)
}