package systems.flowing.graph

protected trait Storage {
    val from: List[Option[Double]]
    def apply(i: Int, j: Int): Option[Double] = apply(i)(j)
    def update(i: Int, j: Int, weight: Option[Double])
    def apply(i: Int): List[Option[Double]]
    def update(i: Int, weights: List[Option[Double]])
}