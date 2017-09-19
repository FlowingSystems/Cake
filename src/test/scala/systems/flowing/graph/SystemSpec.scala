import systems.flowing.graph._
import systems.flowing.graph.structure._

import org.scalatest._

trait System extends Graph with Flow {
    val nodes: List[Node with State]

    def node(i: Int) = {
        val wn = (this(i) zip nodes) filter { case (weight: Option[Double], n: Node with State) => weight.isDefined && n.state.isDefined }
        val s = (wn map { case (weight: Option[Double], n: Node with State) => weight.get * n.state.get } ).sum
        nodes(i).state = Some(s)
    }
}

class SystemSpec extends FlatSpec with Matchers {
    val sys = new {
        val nodes = Unordered.inputs(2) ++ Unordered.states(10) ++ Unordered.outputs(2)
        val init = Unordered.random(-1, 1)
    } with System with Matrix with Init

    "System" should "print an output" in {
        (Repeat(2, Range(0, 1)) >> sys >> Print) output
    }
}