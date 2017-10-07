package systems.flowing.cake

import systems.flowing.cake._

import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime._
import scala.reflect.runtime.universe._

trait Hierarchy {
    this: Nodes[_ <: Order] =>
    def order(n: Int) = nodes.zipWithIndex collect { case (o: Order, i: Int) if o.order == n => i }
    lazy val highest = nodes.map(_.order).max
    lazy val lowest = nodes.map(_.order).min
}

object Hierarchy {
    def apply(ns: Int*) = new {
        def random(a: Double, b: Double, back: Boolean = false) = new Initializer {
            def initialize(structure: Directed) = {
                var offset = 0

                ns zip ns.tail foreach { case (n1, n2) =>
                    for (i <- 0 until n1; j <- 0 until n2) structure(offset+i, offset+j+n1) = Some(Util.random(a, b))
                    offset += n1
                }
            }
        }

        def nodes[T <: Order : TypeTag](createInLayer: (()=>T)*) =
            (ns.zipWithIndex map { case (n: Int, i: Int) =>
                Util.results(n, createInLayer(i))
            }).flatten.toBuffer.asInstanceOf[ArrayBuffer[T]]
    }
}