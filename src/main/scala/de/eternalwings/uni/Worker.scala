package de.eternalwings.uni

import akka.actor.{Actor, ActorRef, PoisonPill, Props, Terminated}

import scala.collection.mutable

case class IPRange(start: BigInt, end: BigInt, tz: String)
case class WorkerConfig(number: Int)

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
        // Alternative:
        //1 to 5 map { _ => createWorker() } foreach { workers.enqueue(_) }
        
        for(i <- 1 to 5) {
          workers.enqueue(createWorker())
        }
    }

    def createWorker() = {
      println("Created new worker")
      val worker = context.actorOf(Props[Worker])
      context.watch(worker)
      worker
    }

    def receive = {
      case WorkerConfig(newNumber) =>
        println(s"set worker amount to $newNumber")
        while(newNumber > workers.size) {
          workers.enqueue(createWorker())
        }

        while(newNumber < workers.size) {
          val worker = workers.dequeue()
          worker ! PoisonPill
        }
      case Terminated(actor) =>
        println("Oh noes, we lost one.")
      case message =>
        val free = workers.dequeue()
        free ! message
        workers.enqueue(free)
    }
}
