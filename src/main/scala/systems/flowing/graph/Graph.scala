package systems.flowing.graph

trait Graph extends Nodes with Store with IO {
    def edges: List[(Int, Int)] = connectionsSatisfying(_.isDefined)
    def edgesSatisfying(c: Double => Boolean) = edges filter { case (i, j) => c(this(i, j).get) }
    def missing: List[(Int, Int)] = connectionsSatisfying(!_.isDefined)

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

    def input(xs: List[Option[Double]]) = {
        val nodesWithInput = nodes.filter(_.isInstanceOf[Input]) zip xs
        nodesWithInput foreach { 
            case (node: Input, x: Option[Double]) => node.state = x
        }
    }

    def output = outputStates

    protected def outputStates = nodes filter (_.isInstanceOf[Output]) map { case node: Output => node.state }

    private def connections: List[(Int, Int)] = ((0 until size) map {
        List.fill(size)(_)
    } flatten) zip (List.fill(size)(0 until size) flatten) toList
    private def connectionsSatisfying(c: Option[Double] => Boolean) = connections filter { case (i, j) => c(this(i, j)) }    

    override def toString = {
        nodesToString + "\n" + storageToString
    }
}