package systems.flowing.graph

trait Graph extends Nodes with Storage {
    def edges: List[(Int, Int)] = connectionsSatisfying(_.isDefined)
    def edgesSatisfying(c: Double => Boolean) = edges filter { case (i, j) => c(this(i, j).get) }
    def missing: List[(Int, Int)] = connectionsSatisfying(!_.isDefined)

    private def connections: List[(Int, Int)] = ((0 until size) map {
        List.fill(size)(_)
    } flatten) zip (List.fill(size)(0 until size) flatten) toList
    private def connectionsSatisfying(c: Option[Double] => Boolean) = connections filter { case (i, j) => c(this(i, j)) }    

    /**
    * Adds a new element to the collection of connections
    * 
    * @param from  a list with connections to the new element
    */
    // def +=(from: List[Option[Double]])

    def normalizeWeights(i: Int): Unit = {
        val sum = (this(i) map {
            case None => 0.0
            case Some(x) => Math.abs(x)
        } sum) / 2

        for (j <- 0 until size) this(i, j) match {
            case None => ()
            case Some(x) => this(i, j) = Some(x / sum)
        }
    }

    def normalize = for (i <- 0 until size) normalizeWeights(i)
}

trait Flow {
    this: Graph with IO =>

    def node(i: Int): Unit

    val input = Some((xs: List[Option[Double]]) => (nodes filter (_.isInstanceOf[Input]) zip xs) foreach { case (node: Input, x: Option[Double]) => node.state = x })
    val output = Some(() => {
        indices(!_.isInstanceOf[Input]) foreach node
        nodes filter (_.isInstanceOf[Output]) map { case node: Output => node.state }
    })
}

trait Hierarchy {
    this: Graph with IO =>

    val nodes: List[Node with Row]
}

trait Forward {
    this: Hierarchy =>
}

trait Backward {
    this: Hierarchy =>
}