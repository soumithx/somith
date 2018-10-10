import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
//Creation of Actor
class motorEngine extends Actor{ //No Arguments are passed
   def receive ={
     case "Start Engine" =>
                         println("Actor Engine Instantiated . . . . .  . ")

     case _ => println(" \n \n Invalid Action Recieved")
   }
}
class veichle(name:String) extends Actor { //Arguments are passes
  def receive ={
    case "Mercedes" => println("Ben Class A \n Benz Class B \n Benz  Class C")
    case "Audi" => println("Audi A1 \n A2 \n A3 \n A4 \n A5 \n A6")
    case "Hyundai" => println("Accent \n Elentra \n Sonata")
    case "Ford" => println("Ford Focus \n Ford Figo")
    case "Volvo" => println("Volvo s6 \n s7 \n s8")
    case _  => println("other products are currently unavailable please contact your dealer \n Thank You...")

  }
}


object AkkaActors extends  App {
 val system = ActorSystem("HelloSystem") //Actor System Created
  val engineActor = system.actorOf(Props[motorEngine],name="engineactor")//Actor Reference Created
  val carActor = system.actorOf(Props(new veichle("Motorviechle")),name="caractor")
  engineActor ! "Start Engine" // Sending message to Actor
  engineActor ! "Go"

  carActor ! "Audi"
  carActor ! "Hyundai"
  system.terminate()
}
