package systems.flowing.cake.io

object Revolve {
    def apply(around: String, xss: Seq[Seq[Double]]) = {
        var i = 0

        new IO with Source {
            def output = {
                val res = Map(around -> xss(i))
                i = if (i+1 < xss.length) i+1 else 0
                res
            }
        };
    }
}