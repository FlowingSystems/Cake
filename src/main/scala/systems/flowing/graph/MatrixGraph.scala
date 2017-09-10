package systems.flowing.graph

import scala.collection.mutable.ArrayBuffer

/**
 * Example graph implementation using a matrix
 */
class MatrixGraph(val nodes: List[Node], val matrix: ArrayBuffer[ArrayBuffer[Option[Double]]]) extends Graph with Matrix

object MatrixGraph extends Builder with NoParameters {
    def build(nodes: List[Node], xss: List[List[Option[Double]]]) = new MatrixGraph(nodes, Util.listToBuffer2D(xss))
//    def <<[T](MatrixGraph: MatrixGraph[T]) = new MatrixGraph(MatrixGraph.xss map { _ clone })
}