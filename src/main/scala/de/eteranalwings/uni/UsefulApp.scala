package de.eteranalwings.uni

import java.io.File

import akka.actor.{ActorSystem, Props}
import scala.concurrent.duration._

object UsefulApp extends App {
    import system.dispatcher

    val system = ActorSystem("UsefulReaderApp")
    val reader = system.actorOf(Props[Reader], "reader")
    val worker = system.actorOf(Props[Worker], "worker")
    val output = system.actorOf(Props[Collector], "collector")

    reader ! ReadCommand(new File("./src/main/resources/i2t.csv"))
    system.scheduler.schedule(1.second, 1.second) {
        output ! Print("Europe/.*")
    }
}
