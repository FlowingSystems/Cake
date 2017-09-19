package systems.flowing.graph

trait Flow extends IO {
    this: Graph =>

    def node(i: Int): Unit

    override def output = {
        indices(!_.isInstanceOf[Input]) foreach node
        outputStates
    }
}