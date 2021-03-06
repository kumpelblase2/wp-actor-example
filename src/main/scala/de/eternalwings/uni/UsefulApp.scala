package de.eternalwings.uni

import java.io.File

import akka.actor.{ActorSystem, Props}

import scala.concurrent.duration._

object UsefulApp extends App {
    import system.dispatcher

    val system = ActorSystem("UsefulReaderApp")
    val output = system.actorOf(Props[Collector], "collector")
    val reader = system.actorOf(Props[Reader], "reader")
    val worker = system.actorOf(Props[WorkerSupervisor], "worker")
    // worker could also be defined as this:
    /*
    val resizer = DefaultResizer(lowerBound = 2, upperBound = 15)
    val worker = system.actorOf(Props[Worker].withRouter(RoundRobinRouter(resizer = Some(resizer))))
     */

    reader ! ReadCommand(new File("./src/main/resources/i2t.csv"))
    system.scheduler.schedule(1.second, 1.second) {
        //worker ! WorkerConfig(Random.nextInt(4) + 1)
        output ! Print("84")
    }
}
