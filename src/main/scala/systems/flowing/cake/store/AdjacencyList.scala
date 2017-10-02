package systems.flowing.cake.store

import systems.flowing.cake._

import scala.collection.mutable.ArrayBuffer

trait AdjacencyList {
    this: Directed =>

    private var list = ArrayBuffer[collection.mutable.Map[Int, Double]]()
    def apply(i: Int) = list(i) toMap

    def update(i: Int, j: Int, weight: Option[Double]): Unit = {
        Util.expandTo(list, j, collection.mutable.Map[Int, Double]())
        if (weight.isDefined) list(j)(i) = weight.get
        else list(j).remove(i)
    }

    def update(i: Int, weights: Map[Int, Double]): Unit = {
        Util.expandTo(list, i, collection.mutable.Map[Int, Double]())
        list(i) = collection.mutable.Map(weights.toSeq: _*)
    }

    def from(i: Int) = Util.cleanMap(list.map(_.get(i)).map {
        case None => Double.NaN
        case Some(x) => x
    })

    def reset: Unit = list = ArrayBuffer[collection.mutable.Map[Int, Double]]()
}