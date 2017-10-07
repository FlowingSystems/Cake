package systems.flowing.cake.io

object Print {
    def apply(epoch: Int) = {
        var i = 1
        
        new IO with Sink {
            def input(cs: Channels): Unit =
                if (i == epoch) {
                    cs foreach { case(name: String, values: Seq[Double]) =>
                        println(s"$name:\n ${values.mkString(" ")}\n")
                    }
                    i = 1
                } else i += 1
        }
    }
}