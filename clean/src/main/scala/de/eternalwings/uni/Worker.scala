package de.eternalwings.uni

import akka.actor.{Actor, ActorRef, PoisonPill, Props, Terminated}


class Worker extends Actor {
    def receive = {
      ???
    }

    def convert(value: BigInt) = {
        0.to(3).map { i =>
            (value >> i * 8) % 256
        }.reverse.mkString(".")
    }
}








class WorkerSupervisor extends Actor {
    override def preStart = {
        ???
    }

    def receive = {
      ???
    }

    def createWorker() = {
      ???
    }






    /* if not enough time */
    def _createWorker() = {
        println("Created new worker")
        val worker = context.actorOf(Props[Worker])
        context.watch(worker)
        worker
    }
}