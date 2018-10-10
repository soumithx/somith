import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
//Creation of Actor
class HelloActor extends Actor{
   def receive ={
     case "Start Engine" => println("Actor Engine Instantiated . . . . .  . ")
     case _ => println(" \n \n Invalid Action Recieved")
   }
}

object AkkaActors extends  App {
 val system = ActorSystem("HelloSystem") //Actor System Created
  val helloActor = system.actorOf(Props[HelloActor],name="helloactor") //Actor Reference Created
  helloActor ! "Start Engine" // Sending message to Actor
  helloActor ! "Go"
  system.terminate()
}
