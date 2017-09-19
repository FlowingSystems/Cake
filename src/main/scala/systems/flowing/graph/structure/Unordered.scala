package systems.flowing.graph.structure

import systems.flowing.graph._

class Random(a: Double, b: Double) extends Initializer {
    def init(graph: Graph) = for (i <- 0 until graph.size; j <- 0 until graph.size)
        graph(i, j) = Util.random(a, b)
}

object Unordered {
    def inputs(n: Int) = Util.results(n, () => { new Node with Input })
    def states(n: Int) = Util.results(n, () => { new Node with State })
    def outputs(n: Int) = Util.results(n, () => { new Node with Output })
    def random(a: Double, b: Double) = new Random(a, b)
}