package systems.flowing.cake

import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime._
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

/**
 * Structures that contain nodes.
 *
 * @type Node the type of information stored in the nodes.
 */
trait Nodes[N] {
    def nodes: Seq[_ <: N]
    def size: Int = nodes length
    // TODO memoize access on typetag, clear cache on +=
    def of[T : ClassTag]: Map[Int, T] =
        nodes.zipWithIndex collect { case (n: T, i: Int) => (i -> n) } toMap
}

object Nodes {
    def random(a: Double, b: Double) = new Initializer {
        def initialize(structure: Directed) = for (i <- 0 until structure.size; j <- 0 until structure.size) {
            structure(i, j) = Some(Util.random(a, b))
            structure(j, i) = Some(Util.random(a, b))
        }
    }

    def nodes[T : TypeTag](fs: (()=>T, Int)*) = fs map { case (create: (()=>T), n: Int) =>
        Util.results(n, create).toBuffer.asInstanceOf[ArrayBuffer[T]]
    } flatten
}

trait Name {
    val name: Option[String]
}

trait State {
    var state = Double.NaN
}

trait Input extends State
trait Output extends State

trait Order {
    val order: Int
}