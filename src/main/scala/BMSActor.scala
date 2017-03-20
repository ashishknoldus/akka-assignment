import akka.actor.{Actor, ActorSystem, Props}
import akka.actor.Actor.Receive
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.concurrent.duration.DurationLong

/**
  * Created by knoldus on 20/3/17.
  */

class BMSActor extends Actor{
  override def receive: Receive = {

    case seat: Int =>
      if(!BMSActor.bookingLock) {

        BMSActor.bookingLock = true

        if(BMSActor.listOfSeats(seat) == 1) {
          BMSActor.listOfSeats(seat) = 0
          println(s"Seat $seat has been booked")
        } else {

        }

        BMSActor.bookingLock = false

      } else {
        Thread.sleep(20)
        self ! seat
      }
  }
}

object BMSActor extends App{

  var bookingLock: Boolean = false
  val listOfSeats : ArrayBuffer[Int] = ArrayBuffer.fill(50)(1) //1 represents unbooked seat

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /bmsRouter {
      |   router = round-robin-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)

  val router = system.actorOf(FromConfig.props(Props[BMSActor]), "bmsRouter")

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45
  router ! 45

}
