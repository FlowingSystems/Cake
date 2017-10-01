package systems.flowing.cake.init

import systems.flowing.cake._
import store._
import nodes._

import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

// TODO implement back
class OrderedRandom(ns: List[Int], a: Double, b: Double, back: Boolean) extends Initializer {
    def init(structure: Directed with Nodes) = {
        var offset = 0

        ns zip ns.tail foreach { case (n1, n2) =>
            for (i <- 0 until n1; j <- 0 until n2) structure(offset+j+n1, offset+i) = Some(Util.random(a, b))
            offset += n1
        }
    }
}

class Ordered(ns: List[Int]) {
    def nodes[T <: Order : TypeTag](createInLayer: (()=>T)*) =
        (ns.zipWithIndex map { case (n: Int, i: Int) =>
            Util.results(n, createInLayer(i))
        }).flatten.toBuffer.asInstanceOf[ArrayBuffer[T]]

    def random(a: Double, b: Double, back: Boolean = false) = new OrderedRandom(ns, a, b, back)
}

object Ordered {
    def apply(ns: Int*) = new Ordered(ns toList)
}