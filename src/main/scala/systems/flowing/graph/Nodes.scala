package systems.flowing.graph

protected trait Nodes {
    val nodes: List[Node]
    def size: Int = nodes length
    def indices(c: Node => Boolean) = (nodes zip nodes.indices) filter { case (n: Node, i: Int) => c(n) } map { case (n: Node, i: Int) => i }
}

trait Node

trait Name {
    this: Node =>
    val name: Option[String]
}

trait State {
    this: Node =>
    var state: Option[Double] = None
}

trait Input extends State {
    this: Node =>
}

trait Output extends State {
    this: Node =>
}

trait Row {
    this: Node =>

    val row: Int
}

object Empty extends Node