package systems.flowing.cake.io

trait IO {
    self =>
    def input(channels: Channels): Unit
    def output(): Channels

    def >>(to: IO) = new IO {
        def input(cs: Channels) = self.input(cs)
        def output = {
            to.input(self.output)
            to.output
        }
    }
    
    def +(b: IO) = new IO {
        def input(cs: Channels) = {
            self.input(cs)
            b.input(cs)
        }

        def output = self.output ++ b.output
    }
}

trait Source {
    this: IO =>
    def input(cs: Channels) = ()
}

trait Sink {
    this: IO =>
    def output = Map[String, Seq[Double]]()
}