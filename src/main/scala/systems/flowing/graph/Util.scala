package systems.flowing.graph

import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer

object Util {
    def random(a: Double, b: Double) = Some(a + (b - a)*scala.util.Random.nextDouble)

    def results[T](n: Int, f: ()=>T): List[T] = (0 until n) map { _=>f() } toList

    def listToMatrix[T](xs: List[T]): List[List[T]] = {
        val n = math.sqrt(xs length)
        assert(n % 2 == 0, "number of elements must be square (provided: " + xs.length + ")")
        return xs grouped n.toInt toList
    }

    def listToBuffer2D[T](xss: List[List[T]]): ArrayBuffer[ArrayBuffer[T]] =
        (xss.toBuffer map ((xs: List[T]) => (xs toBuffer).asInstanceOf[ArrayBuffer[T]])).asInstanceOf[ArrayBuffer[ArrayBuffer[T]]]

    def bufferToList2D[T](matrix: ArrayBuffer[ArrayBuffer[T]]): List[List[T]] =
        (matrix map (_ toList)) toList

    def packDoubleToOption2D[T](xss: List[List[Double]]) =
        xss map { _ map { x => if (x.isNaN) None else Some(x) } }

    def goodDimensions(nodes: List[Node], xss: List[List[Double]]) =
        assert(nodes.length == xss.length, "weight matrix dimension (" + xss.length + ") doesn't match the number of nodes (" + nodes.length + ")")
}