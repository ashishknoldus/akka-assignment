import java.io.File

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.FromConfig
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import akka.pattern.ask
import scala.concurrent.duration.DurationDouble
import scala.io.Source

/**
  * Created by knoldus on 20/3/17.
  */

case class WordCount()

class MasterRouter extends Actor{
  override def receive: Receive = {

    case file: File => {
      val fileSource = Source.fromFile(file)
      val lines = fileSource.getLines

      for(line <- lines) {
        if(line.trim.length > 0) {
          self ! line
        }
      }
    }

    case line: String => {

      MasterRouter.totalWords += line.split("\\s+").length
    }

    case WordCount => {
      sender ! MasterRouter.totalWords
    }

  }
}

object MasterRouter extends App{
  var totalWords = 0

  val config = ConfigFactory.parseString(
    """
      |akka.actor.deployment {
      | /masterRouter {
      |   router = round-robin-pool
      |   nr-of-instances = 5
      | }
      |}
    """.stripMargin
  )

  val system = ActorSystem("RouterSystem", config)

  val router = system.actorOf(FromConfig.props(Props[MasterRouter]), "masterRouter")

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  val file: File = new File("/home/knoldus/Downloads/akka01")

  router ! file

  Thread.sleep(2000)

  val wordCount = router ? WordCount

  wordCount.map(println)
}