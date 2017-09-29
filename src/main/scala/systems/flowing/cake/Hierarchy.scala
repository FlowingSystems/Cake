package systems.flowing.cake

trait Hierarchy extends Graph {
    type Node <: Order
    val order = nodes.map(_.order)
}