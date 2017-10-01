package systems.flowing.cake


/**
 * Structures that have weighed directed connections between pairs of nodes.
 *
 * General rule: methods never fail
 */
trait Directed {
    /**
     * - None for connections outside of boundaries of the store
     * - None for undefined connections inside the boundaries
     * - Some[Double] for defined connections inside the boundaries
     */
    def apply(i: Int, j: Int): Option[Double] = apply(i).get(j)

    /**
     * Map() for points outside the boundaries
     */
    def apply(i: Int): Map[Int, Double]

    /**
     * - Some(weight) => store is expanded to include connection (i, j) if necessary, connection is stored
     * - None => connection is undefined
     */
    def update(i: Int, j: Int, weight: Option[Double]): Unit

    /**
     * If necessary, store is expanded to include connections (i, _)
     */
    def update(i: Int, weights: Map[Int, Double]): Unit

    /**
     * - Map() for points outside the boundaries
     * - Map() for points without connections
     */
    def to(i: Int): Map[Int, Double]
}