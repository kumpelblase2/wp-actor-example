package de.eteranalwings.uni

import akka.actor.{Actor, Props}

case class IPRange(start: BigInt, end: BigInt, tz: String)

class Worker extends Actor {
    val output = context.actorSelection("../collector")
    def receive = {
      case Entry(line) =>
          val split = line.split(",")
          output ! IPRange(BigInt(split(0)), BigInt(split(1)), split(2))
      case _ =>
    }
}
