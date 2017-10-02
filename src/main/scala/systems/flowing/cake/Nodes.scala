package systems.flowing.cake

import scala.collection.mutable.ArrayBuffer

// TODO neat implicit typeclasses
/**
 * Structures that contain nodes.
 *
 * @type Node the type of information stored in the nodes.
 */
trait Nodes[N] {
    def nodes: Seq[_ <: N]
    def size: Int = nodes length
}

object Nodes {
    def random(a: Double, b: Double) = new Initializer {
        def initialize(structure: Directed) = for (i <- 0 until structure.size; j <- 0 until structure.size) {
            structure(i, j) = Some(Util.random(a, b))
            structure(j, i) = Some(Util.random(a, b))
        }
    }

    // TODO [Node]
    def inputs(n: Int) = Util.results(n, () => { new Input {} }).toBuffer.asInstanceOf[ArrayBuffer[Input]]
    def states(n: Int) = Util.results(n, () => { new State {} }).toBuffer.asInstanceOf[ArrayBuffer[State]]
    def outputs(n: Int) = Util.results(n, () => { new Output {} }).toBuffer.asInstanceOf[ArrayBuffer[Output]]    
}

    trait Name {
        val name: Option[String]
    }

trait State {
    var state = Double.NaN
}

trait Input extends State
trait Output extends State

// TODO rename Hierarchy
trait Order {
    val order: Int
}