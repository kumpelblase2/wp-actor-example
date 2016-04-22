package de.eternalwings.uni

import akka.actor.{ActorContext, ActorSystem, Props}

object UsefulApp extends App {
    val system = ActorSystem("UsefulReaderApp")
    val fileLoc = "./src/main/resources/i2t.csv"

    def getActor(actorContext: ActorContext, name: String) = {
        context.actorSelection("/user/" + name)
    }
}
