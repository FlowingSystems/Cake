package systems.flowing.cake

trait Initializer {
    def initialize(structure: Directed)
}

object Empty extends Initializer {
    def initialize(structure: Directed) = structure reset  
}