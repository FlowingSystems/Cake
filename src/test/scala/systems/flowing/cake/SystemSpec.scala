package systems.flowing.cake

import init._
import store._
import io._

import org.scalatest._

trait System extends Graph with Flow with IO with Sink {
    type Node = State

    nodes foreach (_.state = Util.random(-1, 1).get)

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
                Unordered.inputs(2) ++
                Unordered.states(10) ++
                Unordered.outputs(2)
            val init = Unordered.random(-1, 1)
        } with System with AdjacencyMatrix with Init
    }
}