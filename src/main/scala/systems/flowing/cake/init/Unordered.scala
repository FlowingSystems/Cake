package systems.flowing.cake.init

import systems.flowing.cake._
import store._

import scala.collection.mutable.ArrayBuffer
import scala.reflect.runtime.universe._

class Random(a: Double, b: Double) extends Initializer {
    def init(structure: Store with Nodes) = for (i <- 0 until structure.size; j <- 0 until structure.size) {
        structure(i, j) = Util.random(a, b)
        structure(j, i) = Util.random(a, b)
    }
}

object Unordered {
    def inputs(n: Int) = Util.results(n, () => { new Input {} }).toBuffer.asInstanceOf[ArrayBuffer[Input]]
    def states(n: Int) = Util.results(n, () => { new State {} }).toBuffer.asInstanceOf[ArrayBuffer[State]]
    def outputs(n: Int) = Util.results(n, () => { new Output {} }).toBuffer.asInstanceOf[ArrayBuffer[Output]]
    def random(a: Double, b: Double) = new Random(a, b)
}