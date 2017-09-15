package systems.flowing.graph

import scala.collection.mutable.ArrayBuffer

trait Matrix extends Storage {
    val matrix = Util.listToBuffer2D(Util.listToMatrix(from))
    def apply(i: Int) = matrix(i) toList
    def update(i: Int, weights: List[Option[Double]]) = matrix(i) = weights.toBuffer.asInstanceOf[ArrayBuffer[Option[Double]]]
    def update(i: Int, j: Int, weight: Option[Double]) = matrix(i)(j) = weight
}