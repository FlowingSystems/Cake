package systems.flowing.graph

trait Builder { 
    def goodDimensions(nodes: List[Node], xss: List[List[Double]]) =
        assert(nodes.length == xss.length, "weight matrix dimension (" + xss.length + ") doesn't match the number of nodes (" + nodes.length + ")")
    //    def <<(from: Graph): Graph
}

trait NoParameters extends Builder {
    protected def build(nodes: List[Node], xss: List[List[Option[Double]]]): Graph

    def apply(nodes: List[Node], xs: List[Option[Double]]) = {
        val n = math.sqrt(xs length)
        assert(n % 2 == 0, "number of elements must be square (provided: " + xs.length + ")")
        build(nodes, xs sliding n.toInt toList)
    }

    /**
     * n*n empty connections
     */
    def apply(n: Int) = build(List.fill(10)(Empty), 0 until n map { _ => List.fill(n)(None) } toList)
}