package systems.flowing.cake

import store._

// TODO checks for store mutation to retain treeness
trait Tree extends Hierarchy with AdjacencyList {
    override def update(i: Int, j: Int, weight: Option[Double]): Unit = {
        assert(Math.abs(nodes(i).order - nodes(j).order) == 1)
        super.update(i, j, weight)
    }

    override def update(i: Int, weights: Map[Int, Double]): Unit = weights foreach { case (j: Int, weight: Double) => this.update(i, j, Some(weight)) }
}