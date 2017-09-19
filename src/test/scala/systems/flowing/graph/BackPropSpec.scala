import systems.flowing.graph._
import systems.flowing.graph.structure._

import org.scalatest._

trait BackProp extends Graph with Hierarchy {
    val nodes: List[Node with Order with State]

    def signal(i: Int) = {
        println(i)
        val wn = (this(i) zip nodes) filter { case (weight: Option[Double], n: Node with State) => weight.isDefined && n.state.isDefined }
        val s = (wn map { case (weight: Option[Double], n: Node with State) => weight.get * n.state.get } ).sum
        nodes(i).state = Some(s)
    }
}

class BackPropSpec extends FlatSpec with Matchers {
    val bp = new {
        val nodes = Ordered(List(1, 1)).nodes
        val init = Ordered(List(1, 1)).random(-1, 1)
    } with BackProp with Matrix with Init

    "BackProp" should "print an output" in {
        (Range(0, 1) >> bp >> Print) output
    }
}