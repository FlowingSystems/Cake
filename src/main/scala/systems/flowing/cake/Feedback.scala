package systems.flowing.cake

import nodes._

trait Feedback {
    this: Hierarchy =>

    /**
     * Same as signal, but indices are in reverse order
     */
    def feedback(i: Int): Unit

    def backpropagate = nodes.sortBy(_.order).indices.reverse map feedback
}