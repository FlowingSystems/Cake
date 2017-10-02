package systems.flowing.cake

import store._
import io._

import org.scalatest._

trait System extends Nodes[State] with Directed with Flow with IO with Sink {
    // type Node = State

//    val nodes: Seq[_ <: State]

    nodes foreach (_.state = Util.random(-1, 1))

    def input(cs: Channels) = {
        (nodes filter(_.isInstanceOf[Input]) zip cs("inputValues")) foreach { 
            case (n: Input, x: Double) => n.state = x
        }
        process
    }

    def node(i: Int) = {
        val s = this(i) map { case (from: Int, weight: Double) =>
            nodes(from).state * weight
        }
        nodes(i).state = s sum
    }
}

class SystemSpec extends FlatSpec with Matchers {
    "System" should "print an output" in {
        val sys = new {
            val nodes =
                Nodes.inputs(2) ++
                Nodes.states(10) ++
                Nodes.outputs(2)
        } with System with AdjacencyMatrix

        sys <= Nodes.random(-1, 1)
    }
}