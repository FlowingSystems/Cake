package systems.flowing.cake

trait Flow {
    this: Graph =>

    def node(i: Int): Unit

    def process = indices(!_.isInstanceOf[Input]) foreach node
}