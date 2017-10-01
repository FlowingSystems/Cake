package systems.flowing.cake.nodes

trait Hierarchy extends Nodes {
    type Node <: Order

    val order = nodes.map(_.order).max
    val lowest = nodes.map(_.order).min
}