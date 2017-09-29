package systems.flowing.cake.init

import systems.flowing.cake._
import store._

trait Init {
    this: Store with Nodes =>
    val init: Initializer
    init.init(this)
}

trait Initializer {
    def init(structure: Store with Nodes)
}