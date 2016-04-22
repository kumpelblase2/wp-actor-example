package de.eternalwings.uni

import akka.actor.Actor

class Collector extends Actor {
    var items : Map[String, Int] = Map.empty

    def receive = {
        case iprange: IPRange =>
            val first = iprange.start.split("\\.")(0)
            items = items.updated(first, items.getOrElse(first, 0) + 1)
        case Print(first) =>
            println("Amount:" + items.getOrElse(first, 0))
    }
}

case class Print(zone: String)
