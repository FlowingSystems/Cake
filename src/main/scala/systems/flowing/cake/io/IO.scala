package systems.flowing.cake.io

trait IO {
    self =>
    def input(channels: Map[String, Seq[Double]]): Unit
    def output(): Map[String, Seq[Double]]

    def >>(to: IO) = new IO {
        def input(cs: Map[String, Seq[Double]]) = self.input(cs)
        def output = {
            to.input(self.output)
            to.output
        }
    }
    
    def +(b: IO) = new IO {
        def input(cs: Map[String, Seq[Double]]) = {
            self.input(cs)
            b.input(cs)
        }

        def output = self.output ++ b.output
    }
}

trait Source {
    this: IO =>
    def input(cs: Map[String, Seq[Double]]) = ()
}

trait Sink {
    this: IO =>
    def output = Map[String, Seq[Double]]()
}