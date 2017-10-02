package systems.flowing.cake

import store._
import io._

import org.scalatest._

trait Error {
    this: State =>
    var error: Double = 0.0
}

trait Target extends Error {
    this: Output =>
    var target: Double = 0.0
}

class BackPropSpec extends FlatSpec with Matchers {
    "BackProp" should "print an output" in {
        val learningRate = 0.001

        val bp = new Nodes[State with Order]
                with Hierarchy
                with Directed
                with Signal
                with Feedback
                with IO
                with AdjacencyList {
            val nodes = Hierarchy(2, 2, 1).nodes(
                () => new {val order = 0} with Input with Order,
                () => new {val order = 1} with State with Order with Error,
                () => new {val order = 2} with Output with Order with Target
            )

            def sig(x: Double) = 1 / (1 + Math.pow(Math.E, -x))
            def dsig(x: Double) = x * (x - 1)

            def signal(i: Int) = nodes(i).state = sig(this(i) map { case (i: Int, weight: Double) => nodes(i).state * weight } sum)

            def feedback(i: Int) = {
                nodes(i) match {
                    case o: Output with Target => o.error = (o.target - o.state) * dsig(o.state)
                    case s: State with Error => s.error = from(i) map { edge =>
                        this(i, edge._1).get * nodes(edge._1).asInstanceOf[Error].error * dsig(s.state)
                    } sum
                    case _ => () // Input has no error
                }

                this(i) = this(i) map { case (from: Int, weight: Double) =>
                    from -> (weight + nodes(i).asInstanceOf[Error].error * learningRate * nodes(from).state)
                }
            }

            def input(channels: Channels) = {
                channels("inputValues") zip nodes.filter(_.isInstanceOf[Input]) foreach {
                    case (x: Double, node: Input) => node.state = x
                }

                propagate(!_.isInstanceOf[Input])

                channels("targetValues") zip nodes.filter(_.isInstanceOf[Target]) foreach {
                    case (x: Double, node: Target) => node.target = x
                }

                backpropagate(!_.isInstanceOf[Input])
            }

            def output = Map(
                "outputs" -> (nodes collect { case n: Output => n.state }),
                "errors" -> (nodes collect { case n: Error => n.error })
            )
        }

        bp <= Hierarchy(2, 2, 1).random(-1, 1)

        val inputs = List(List(0.0, 0.0), List(0.0, 1.0), List(1.0, 0.0), List(1.0, 1.0))

        val or = List(0.0, 1.0, 1.0, 1.0) map (x=>List(x))

        val training = (Revolve("inputValues", inputs) + Revolve("targetValues", or)) >> bp >> Print
        training output
    }
}