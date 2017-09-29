package systems.flowing.cake

import scala.collection.mutable.ArrayBuffer

trait Nodes {
    type Node
    val nodes: ArrayBuffer[_ <: Node]
    def size: Int = nodes length
    def indices(c: Node => Boolean) = (nodes zip nodes.indices) filter { case (n: Node, i: Int) => c(n) } map { case (n: Node, i: Int) => i }
    def nodesToString = nodes.mkString(" ")
}

trait Name {
    val name: Option[String]
}

trait State {
    var state = Double.NaN
}

trait Input extends State
trait Output extends State

trait Order {
    val order: Int
}