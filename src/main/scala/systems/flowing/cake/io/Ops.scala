package systems.flowing.cake.io

class Pipe(from: IO, to: IO) extends IO {
    def input(cs: Channels) = from.input(cs)
    def output = {
        to.input(from.output)
        to.output
    }
}

class Plus(a: IO, b: IO) extends IO {
    def input(cs: Channels) = {
        a.input(cs)
        b.input(cs)
    }

    def output = a.output ++ b.output
}