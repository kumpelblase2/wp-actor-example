import akka.actor.{Actor, ActorSystem, PoisonPill, Props}

class TestingActor extends Actor {
  def receive = {
    case "Test" =>
      println("Hello world!")
    case "Delay" =>
      Thread.sleep(1000)
      println("Hello after a delay!")
    case "Die" =>
      self ! PoisonPill
  }
}

object TestingActor {
  def setup() = {
    val system = ActorSystem("Testing")
    system.actorOf(Props[TestingActor], "test-actor")
  }
}