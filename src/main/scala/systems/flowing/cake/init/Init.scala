package systems.flowing.cake.init

import systems.flowing.cake._
import store._
import nodes._

trait Init {
    this: Directed with Nodes =>
    val init: Initializer
    init.init(this)
}

trait Initializer {
    def init(structure: Directed with Nodes)
}