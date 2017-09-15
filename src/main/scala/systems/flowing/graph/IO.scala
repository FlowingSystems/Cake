package systems.flowing.graph

import scala.util.Random
import scala.collection.mutable.ArrayBuffer

trait IO {
    val input: Option[List[Option[Double]] => Unit]
    val output: Option[() => List[Option[Double]]]

    private def ei(f: Option[List[Option[Double]] => Unit], xs: List[Option[Double]]) = f match { case Some(f) => f(xs) }
    private def eo(f: Option[() => List[Option[Double]]]) = f match { case Some(f) => f() }

    def pull: Unit = eo(output)
    def pull(n: Int): Unit = for (i <- 0 until n) pull

    def >>(to: IO) = {
        assert(this.output.isDefined)
        assert(to.input.isDefined)        
        new {
            val input = this.input
            val output = Some(() => {
                ei(to.input, eo(this.output))
                if (to.output.isDefined) eo(to.output) else List[Option[Double]]()
            })
        } with IO
    }
}

object Range {
    def apply(from: Double, to: Double) = new {
        val input = None
        val output = Some(() => List(Some(from + Random.nextDouble*(to-from))))
    } with IO
}

object Repeat {
    def apply(n: Int, from: IO) = {
        var buffer = new ArrayBuffer[Option[Double]]
        new {
            val input = None
            val output = Some(() => {
                for(i <- 0 until n) buffer.appendAll(from.output.get())
                val xs = buffer.toList
                buffer = new ArrayBuffer
                xs
            })
        } with IO
    }
}

object Print extends IO {
    val input = Some((xs: List[Option[Double]]) => println(xs))
    val output = None
}