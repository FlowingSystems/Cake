package systems.flowing.cake.store

import systems.flowing.cake._

import scala.collection.mutable.ArrayBuffer

trait AdjacencyMatrix {
    this: Directed =>
    
    val matrix = new ArrayBuffer[ArrayBuffer[Double]]

    override def apply(i: Int, j: Int) = 
        if (j >= matrix.length) None
        else if (i >= matrix(j).length)  None
        else {
            val weight = matrix(j)(i)
            if (weight isNaN) None else Some(weight)
        }

    def apply(i: Int) = if (i < matrix.length) Util.cleanMap(matrix(i)) else Map[Int, Double]()

    def update(i: Int, j: Int, weight: Option[Double]) =
        if (weight.isDefined || (i < matrix.length && j < matrix(i).length)) {
            Util.expandTo(matrix, i, new ArrayBuffer[Double])
            Util.expandTo(matrix(i), j, Double.NaN)
            matrix(i)(j) = if (weight isDefined) weight.get else Double.NaN
        }

    def update(i: Int, weights: Map[Int, Double]): Unit = {
        val withMissing = List.fill(weights.keys.max)(Double.NaN).to[ArrayBuffer]
        weights foreach { case (i: Int, x: Double) => withMissing(i) = x }
        Util.expandTo(matrix, i, new ArrayBuffer[Double])
        matrix(i) = withMissing
    }

    def to(i: Int) = Util.cleanMap(matrix map { xs: ArrayBuffer[Double] => xs(i) })
}