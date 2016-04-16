package de.eternalwings.uni

import akka.actor.Actor

class Collector extends Actor {
    var items : Map[String, List[IPRange]] = Map.empty

    def receive = {
        case iprange: IPRange =>
            val curr = items.getOrElse(iprange.tz, List.empty)
            items = items.updated(iprange.tz, curr ++ List(iprange))
        case Print(zone) =>
            println("Print")
            items.keys.filter(_.matches(zone)).map(items.getOrElse(_, List())).foreach(x => x.foreach(println))
    }
}

case class Print(zone: String)
