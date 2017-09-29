package systems.flowing.cake.store

import systems.flowing.cake._

import scala.collection.mutable.ArrayBuffer

trait AdjacencyList {
    this: Store =>

    val list = ArrayBuffer[collection.mutable.Map[Int, Double]]()
    def apply(i: Int) = list(i) toMap

    def update(i: Int, j: Int, weight: Option[Double]): Unit = {
        for (_ <- 0 until (i - list.length + 1)) list += collection.mutable.Map[Int, Double]()
        if (weight.isDefined) list(i)(j) = weight.get
        else list(i).remove(j)
    }

    def update(i: Int, weights: Map[Int, Double]): Unit = {
        Util.expandTo(list, i, collection.mutable.Map[Int, Double]())
        list(i) = collection.mutable.Map(weights.toSeq: _*)
    }

    def to(i: Int) = Util.cleanMap(list.map(_.get(i)).map {
        case None => Double.NaN
        case Some(x) => x
    })

    def storeToString = ???
}