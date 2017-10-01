package systems.flowing.cake

import scala.collection.mutable.ArrayBuffer

object Util {
    def expandTo[T](xs: ArrayBuffer[T], i: Int, x: => T): Unit
        = for (_ <- 0 until (i - xs.length + 1)) xs += x
    
    def cleanMap(xs: Seq[Double]): Map[Int, Double] = pruneNaN(indexMap(xs))

    def random(a: Double, b: Double): Double = a + (b - a)*scala.util.Random.nextDouble

    def results[T](n: Int, f: ()=>T): List[T] = (0 until n) map { _=>f() } toList

    private def indexMap[T](xs: Seq[T]) = Map(xs.zipWithIndex map { case (x: T, i: Int) => (i, x) }: _*)
    private def pruneNaN(m: Map[Int, Double]) = m filter { case (_, x: Double) => !x.isNaN }
}