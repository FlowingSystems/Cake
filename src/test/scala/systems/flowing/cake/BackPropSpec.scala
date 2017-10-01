package systems.flowing.cake

import init._
import store._
import io._
import nodes._

import org.scalatest._

trait Error {
    this: State =>
    var error: Double = 0.0
}

trait Target extends Error {
    this: Output =>
    var target: Double = 0.0
}

trait BackProp extends Nodes with Directed with Hierarchy with Signal with Feedback with IO {
    type Node = State with Order

    val learningRate: Double

    def sig(x: Double) = 1 / (1 + Math.pow(Math.E, -x))
    def dsig(x: Double) = x * (x - 1)

    def signal(i: Int) = nodes(i).state = sig(this(i) map { case (i: Int, weight: Double) => nodes(i).state * weight } sum)

    def feedback(i: Int) = {
        nodes(i) match {
            case o: Output with Target => o.error = (o.target - o.state) * dsig(o.state)
            case s: State with Error => s.error = dsig(s.state)
            case _ => () // Input has no error
        }

        this(i) = this(i) map { case (from: Int, weight: Double) =>
            from -> (weight + nodes(i).asInstanceOf[Error].error * learningRate * nodes(from).state)
        }
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

    def output = Map(
        "outputs" -> (nodes collect { case n: Output => n.state }),
        "errors" -> (nodes collect { case n: Error => n.error })
    )
}

class BackPropSpec extends FlatSpec with Matchers {
    "BackProp" should "print an output" in {
        // TODO complete cake
        val bp = new {
            val learningRate = 0.001

            val nodes = Ordered(2, 2, 1).nodes(
                () => new {val order = 0} with Input with Order,
                () => new {val order = 1} with State with Order with Error,
                () => new {val order = 2} with Output with Order with Target
            )

            val init = Ordered(2, 2, 1).random(-1, 1)
        } with BackProp with AdjacencyList with Init

        val inputs = List(List(0.0, 0.0))
        val targets = List(List(1.0))
        val training = (Revolve("inputValues", inputs) + Revolve("targetValues", targets)) >> bp >> Print
        training output
    }
}