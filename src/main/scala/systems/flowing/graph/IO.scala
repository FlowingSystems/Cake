package systems.flowing.graph

import scala.util.Random
import scala.collection.mutable.ArrayBuffer

trait IO {
    def input(xs: List[Option[Double]]): Unit
    def output(): List[Option[Double]]

    class Connector(from: IO, to: IO) extends IO {
        def input(xs: List[Option[Double]]) = from.input(xs)
        def output = {
            to.input(from.output)
            to.output
        }
    }

    def >>(to: IO) = new Connector(this, to)
}

class Range(from: Double, to: Double) extends IO {
    def input(xs: List[Option[Double]]) = ()
    def output = List(Some(from + Random.nextDouble*(to-from)))
}

object Range {
    def apply(from: Double, to: Double) = new Range(from, to)
}

class Repeat(n: Int, from: IO) extends IO {
    def input(xs: List[Option[Double]]) = ()
    def output = {
        val buffer = new ArrayBuffer[Option[Double]]
        for(i <- 0 until n) buffer.appendAll(from.output)
        buffer toList
    }
}

object Repeat {
    def apply(n: Int, from: IO) = new Repeat(n, from)
}

object Print extends IO {
    def input(xs: List[Option[Double]]) = println(xs)
    def output = List()
}