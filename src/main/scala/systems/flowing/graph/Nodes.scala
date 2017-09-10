package systems.flowing.graph

trait Node

trait Name {
    var name: Option[String] = None
}

trait State {
    var state: Option[Double] = None
}

trait Input extends State
trait Output extends State

case object Empty extends Node

trait Nodes {
    val nodes: List[Node]
    def size: Int = nodes length
    def indices = 0 until size
}