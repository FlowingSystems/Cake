package systems.flowing.cake.io

object Revolve {
    def apply(combs: (String, Seq[Seq[Double]])*)(next: Int => Int) = {
        var i = 0

        new IO with Source {
            def output = {
                val res = Map(combs map { case (name: String, cs: Seq[Seq[Double]]) => (name -> cs(i)) }: _*)
                i = next(i)
                res
            }
        };
    }
}