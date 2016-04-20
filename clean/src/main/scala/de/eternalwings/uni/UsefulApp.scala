package de.eternalwings.uni

import akka.actor.{ActorSystem, Props}

object UsefulApp extends App {
    val system = ActorSystem("UsefulReaderApp")
    val fileLoc = "./src/main/resources/i2t.csv"
}
