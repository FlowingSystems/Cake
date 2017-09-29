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

    private def connections: List[(Int, Int)] = ((0 until size) map {
        List.fill(size)(_)
    } flatten) zip (List.fill(size)(0 until size) flatten) toList
    private def connectionsSatisfying(c: Option[Double] => Boolean) = connections filter { case (i, j) => c(this(i, j)) }    

    override def toString = nodesToString + "\n" + storeToString
}