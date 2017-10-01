package systems.flowing.cake

package object io {
    type Channels = Map[String, Seq[Double]]
}

package object nodes {
    trait Name {
        val name: Option[String]
    }

    trait State {
        var state = Double.NaN
    }

    trait Input extends State
    trait Output extends State

    trait Order {
        val order: Int
    }
}