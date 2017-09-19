package systems.flowing.graph

import scala.collection.mutable.ArrayBuffer

trait Matrix extends Store {
    val matrix = new ArrayBuffer[ArrayBuffer[Option[Double]]]
    def apply(i: Int) = matrix(i) toList
    def to(i: Int) = matrix map { xs: ArrayBuffer[Option[Double]] => xs(i) } toList
    def storageToString = matrix.map(_.mkString(" ")).mkString("\n")

    def update(i: Int, j: Int, weight: Option[Double]) = {
        for (_ <- 0 until (i - matrix.length + 1)) matrix += new ArrayBuffer
        for (_ <- 0 until (j - matrix(i).length + 1)) matrix(i) += None
        matrix(i)(j) = weight
    }

    def update(i: Int, weights: List[Option[Double]]) = {
        for (_ <- 0 until (i - matrix.length + 1)) matrix += new ArrayBuffer
        matrix(i) = weights.toBuffer.asInstanceOf[ArrayBuffer[Option[Double]]]
    }
}