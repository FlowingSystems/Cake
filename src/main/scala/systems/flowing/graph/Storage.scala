package systems.flowing.graph

import scala.collection.mutable.ArrayBuffer

trait Storage {
    def apply(i: Int, j: Int): Option[Double] = apply(i)(j)
    def update(i: Int, j: Int, weight: Option[Double])
    def apply(i: Int): List[Option[Double]]
    def update(i: Int, weights: List[Option[Double]])
}

trait Matrix extends Storage {
    val matrix: ArrayBuffer[ArrayBuffer[Option[Double]]]
    def apply(i: Int) = matrix(i) toList
    def update(i: Int, weights: List[Option[Double]]) = matrix(i) = weights.toBuffer.asInstanceOf[ArrayBuffer[Option[Double]]]
    def update(i: Int, j: Int, weight: Option[Double]) = matrix(i)(j) = weight
}