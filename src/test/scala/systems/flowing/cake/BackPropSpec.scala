package systems.flowing.cake

import init._
import store._
import io._

import org.scalatest._

trait Error {
    this: State =>
    var error: Double = 0.0
}

trait Target {
    this: Output =>
    var target: Double = 0.0
}

trait BackProp extends Hierarchy with Signal with Feedback with IO {
    type Node = State with Order

    def sig(x: Double) = 1 / (1 + Math.pow(Math.E, -x))
    def dsig(x: Double) = x * (x - 1)

    def signal(i: Int) = nodes(i).state = sig(this(i) map { case (i: Int, weight: Double) => nodes(i).state * weight } sum)

    def feedback(i: Int) = {
    }

    def input(channels: Channels) = {
        (nodes filter (_.isInstanceOf[Input]) zip channels("inputValues")) foreach {
            case (node: Input, x: Double) => node.state = x
        }

        (nodes filter (_.isInstanceOf[Input]) zip channels("targetValues")) foreach {
            case (node: Input, x: Double) => node.state = x
        }

        propagate
        backpropagate
    }

    // TODO get[Output]
    def output = Map(
        "outputs" -> (nodes filter (_.isInstanceOf[Output]) map (_.asInstanceOf[Output].state)),
        "errors" -> (nodes filter (_.isInstanceOf[Output]) map (_.asInstanceOf[Error].error))
    )
}

class BackPropSpec extends FlatSpec with Matchers {
    "BackProp" should "print an output" in {
        val bp = new {
            val nodes = Ordered(2, 2, 1).nodes(
                () => new {val order = 0} with Input with Order,
                () => new {val order = 1} with State with Order with Error,
                () => new {val order = 2} with Output with Order with Error with Target
            )
            val init = Ordered(2, 2, 1).random(-1, 1)
        } with BackProp with AdjacencyList with Init

        val inputs = List(List(0.0, 1.0))
        val targets = List(List(1.0, 1.0))
        val training = (Revolve("inputValues", inputs) + Revolve("targetValues", targets)) >> bp >> Print
        training output
    }
}