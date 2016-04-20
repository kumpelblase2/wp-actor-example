package de.eternalwings.uni

import akka.actor.{Actor, ActorRef, PoisonPill, Props, Terminated}


class Worker extends Actor {
    def receive = {
      ???
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