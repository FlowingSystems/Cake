package systems.flowing.cake.io

object Print extends IO with Sink {
    def input(cs: Channels) = cs foreach {
        case(name: String, values: Seq[Double]) => println(s"$name:\n ${values.mkString(" ")}\n")
    }
}