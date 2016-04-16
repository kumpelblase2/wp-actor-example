package de.eternalwings.uni

import akka.actor.{Actor, ActorRef, Props, Terminated}

import scala.collection.mutable

case class IPRange(start: BigInt, end: BigInt, tz: String)

class Worker extends Actor {
    val output = context.actorSelection("/user/collector")
    def receive = {
      case Entry(line) =>
          val split = line.split(",")
          output ! IPRange(BigInt(split(0)), BigInt(split(1)), split(2))
      case _ =>
    }
}

class WorkerSupervisor extends Actor {
    val workers = new mutable.Queue[ActorRef]()

    override def preStart = {
        for(i <- 1 to 5) {
          val worker = context.actorOf(Props[Worker])
          context.watch(worker)
          workers.enqueue(worker)
        }
    }

    def receive = {
      case Terminated(actor) =>
        println("Oh noes, we lost one.")
      case message =>
        val free = workers.dequeue()
        free ! message
        workers += free
    }
}