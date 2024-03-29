// Another example of using actors in Scala.  This is taken from the Akka documentation.
// Computes PI by calculating sections of an infinite sequence concurrently.

import akka.actor._
import akka.routing.RoundRobinPool

object Pi extends App {
  calculate(nrOfWorkers = 4, nrOfElements = 1000, nrOfMessages = 1000)

  sealed trait PiMessage
  case object Calculate extends PiMessage
  case class Work(start: Int, nrOfElements: Int) extends PiMessage
  case class Result(value: Double) extends PiMessage
  case class PiApproximation(pi: Double)

  class Worker extends Actor {

    def calculatePiFor(start: Int, nrOfElements: Int): Double = {
      var acc = 0.0

      for (i <- start until (start + nrOfElements))
        acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
      acc
    }

    def receive = {
      case Work(start, nrOfElements) =>
        sender ! Result(calculatePiFor(start, nrOfElements))
    }
  }

  class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef)
    extends Actor {

    var pi: Double = _
    var nrOfResults: Int = _

    val workerRouter = context.actorOf(
      Props[Worker].withRouter(RoundRobinPool(nrOfWorkers)), name = "workerRouter")

    def receive = {
      case Calculate =>
        for (i <- 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
      case Result(value) =>
        pi += value
        nrOfResults += 1
        if (nrOfResults == nrOfMessages) {
          // Send the result to the listener
          listener ! PiApproximation(pi)
        }
    }
  }

  class Listener extends Actor {
    def receive = {
      case PiApproximation(pi) =>
        println("\n\tPi approximation: \t\t%s\n".format(pi))
        context.system.shutdown()
    }
  }

  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int) {
    // Create an Akka system
    val system = ActorSystem("PiSystem")

    // Create the result listener, which will print the result and shutdown the system
    val listener = system.actorOf(Props[Listener], name = "listener")

    // create the master
    val master = system.actorOf(Props(new Master(
      nrOfWorkers, nrOfMessages, nrOfElements, listener)),
      name = "master")

    // start the calculation
    master ! Calculate
  }
}
