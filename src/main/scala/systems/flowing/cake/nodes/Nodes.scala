package systems.flowing.cake.nodes

/**
 * Structures that contain nodes.
 *
 * @type Node the type of information stored in the nodes.
 */
trait Nodes {
    type Node
    val nodes: Seq[_ <: Node]
    def size: Int = nodes length
    def indices(c: Node => Boolean) = (nodes zip nodes.indices) filter { case (n: Node, i: Int) => c(n) } map { case (n: Node, i: Int) => i }
}