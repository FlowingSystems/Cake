package systems.flowing.graph

import scala.collection.mutable.Buffer
import scala.collection.mutable.ArrayBuffer

object Util {
    def listToBuffer2D[T](xss: List[List[T]]) = (xss.toBuffer map ((xs: List[T]) => (xs toBuffer).asInstanceOf[ArrayBuffer[T]])).asInstanceOf[ArrayBuffer[ArrayBuffer[T]]]
    def packDoubleToOption2D[T](xss: List[List[Double]]) = xss map { _ map { x => if (x.isNaN) None else Some(x) } }
}