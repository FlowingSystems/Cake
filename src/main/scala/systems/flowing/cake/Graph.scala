package systems.flowing.cake

import store._
import init._

import collection.mutable.ArrayBuffer

trait Graph extends Nodes with Store {
    def edges: List[(Int, Int)] = connectionsSatisfying(_.isDefined)
    def edgesSatisfying(c: Double => Boolean) = edges filter { case (i, j) => c(this(i, j).get) }
    def missing: List[(Int, Int)] = connectionsSatisfying(!_.isDefined)

    def normalizeWeights(i: Int): Unit = {
        val weights = this(i)
        val sum = weights.values.map(Math.abs(_)).sum
        val avg = sum / weights.size
        for (j <- weights.keys) this(i, j) = Some(weights(j) / avg)
    }

    def normalize = for (i <- 0 until size) normalizeWeights(i)

    def fillInputs(xs: List[Double]) = {
        val nodesWithInput = nodes.filter(_.isInstanceOf[Input]) zip xs
        nodesWithInput foreach {
            case (node: Input, x: Double) => node.state = x
        }
    }

    // TODO use type tags
    def outputStates = nodes filter (_.isInstanceOf[Output]) map (_.asInstanceOf[Output].state) toList

    private def connections: List[(Int, Int)] = ((0 until size) map {
        List.fill(size)(_)
    } flatten) zip (List.fill(size)(0 until size) flatten) toList
    private def connectionsSatisfying(c: Option[Double] => Boolean) = connections filter { case (i, j) => c(this(i, j)) }    

    override def toString = nodesToString + "\n" + storeToString
}