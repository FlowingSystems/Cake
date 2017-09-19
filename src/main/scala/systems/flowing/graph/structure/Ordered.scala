package systems.flowing.graph.structure

import systems.flowing.graph._

class OrderedRandom(ns: List[Int], a: Double, b: Double) extends Initializer {
    def init(graph: Graph) = {
        var offset = 0

        ns zip ns.tail foreach { case (n1, n2) =>
            for (i <- 0 to n1; j <- 0 to n2) graph(offset+i, offset+j) = Util.random(a, b)
            offset += n1
        }

        if (graph.isInstanceOf[Feedback]) println("detected feedback")
    }
}


class Ordered(ns: List[Int]) {
    def nodes = {
        assert(ns.length > 1, s"number of layers has to exceed 1 (provided: %{ns.length})")
        assert(!ns.exists(_<1), "layers must contain a positive number of nodes")

        val inputs = Util.results(ns.head, () => { new {val order = 0} with Node with Input with Order })

        val (_+:ms:+_) = ns
        val middle = ms.zipWithIndex map { case (n, i) =>
            Util.results(n, () => { new {val order = i+1} with Node with State with Order })
        } flatten

        val outputs = Util.results(ns.last, () => { new {val order = ns.length-1} with Node with Output with Order })

        inputs ++ middle ++ outputs
    }

    def random(a: Double, b: Double) = new OrderedRandom(ns, a, b)
}

object Ordered {
    /**
    * [number of input nodes, layers..., number of output nodes]
    */
    def apply(ns: List[Int]) = new Ordered(ns)
}