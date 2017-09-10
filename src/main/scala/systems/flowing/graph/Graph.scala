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

    def normalizeRow(i: Int): Unit = {
        val sum = (this(i) map {
            case None => 0.0
            case Some(x) => Math.abs(x)
        } sum) / 2

        for (j <- 0 until size) this(i, j) match {
            case None => ()
            case Some(x) => this(i, j) = Some(x / sum)
        }
    }

    def normalize = for (i <- 0 until size) normalizeRow(i)
}

trait Propagate extends Nodes {
    def pre(): Unit
    def node(i: Int): Unit
    def post: Unit

    def propagate = {
        pre
        indices foreach node _
        post
    }
}

trait IO extends Nodes {
    val inputs: () => List[Double]
    val outputs: List[Double] => Unit
    def setInputs = nodes filter (_.isInstanceOf[Input]) zip inputs() foreach { case (node: Input, input: Double) => node.state = Some(input) }
}