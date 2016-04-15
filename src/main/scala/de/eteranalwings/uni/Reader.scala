package de.eteranalwings.uni

import java.io.File

import akka.actor.{Actor, Props}

import scala.io.Source

case class ReadCommand(file: File)
case class Entry(line: String)

class Reader extends Actor {

    val parser = context.actorSelection("../worker")

    def receive = {
        case ReadCommand(file) =>
            for(line <- Source.fromFile(file).getLines()) {
              parser ! Entry(line)
            }
        case _ =>
    }
}
