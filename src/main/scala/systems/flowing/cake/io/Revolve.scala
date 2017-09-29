package systems.flowing.cake.io

class Revolve(around: String, xss: Seq[Seq[Double]]) extends IO with Source {
    var i = 0
    def output = {
        val res = Map(around -> xss(i))
        i = if (i+1 < xss.length) i+1 else 0
        res
    }
}

object Revolve {
    def apply(around: String, xss: Seq[Seq[Double]]) = new Revolve(around, xss)
}