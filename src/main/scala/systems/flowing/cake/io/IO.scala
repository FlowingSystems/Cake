package systems.flowing.cake.io

trait IO {
    def input(channels: Channels): Unit
    def output(): Channels

    def >>(to: IO) = new Pipe(this, to)
    def +(b: IO) = new Plus(this, b)
}

trait Source {
    this: IO =>
    def input(cs: Channels) = ()
}

trait Sink {
    this: IO =>
    def output = Map[String, Seq[Double]]()
}