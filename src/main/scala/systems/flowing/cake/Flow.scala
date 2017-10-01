package systems.flowing.cake

import nodes._

trait Flow {
    this: Nodes with Directed =>

    def node(i: Int): Unit

    def process = indices(!_.isInstanceOf[Input]) foreach node
}