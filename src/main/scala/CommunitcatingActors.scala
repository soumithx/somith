import akka.actor._

case object PingMessage
case object PongMessage
case object StartMessage
case object StopMessage

class Ping(pong:ActorRef) extends Actor {
  var count = 0
  var IncAndPnt = {count += 1;println("Ping")}
  def receive= {
    case StartMessage => IncAndPnt
                         pong ! PingMessage
    case PongMessage => IncAndPnt
                        if(count > 99){
                          sender ! StopMessage
                          println("Ping Stop")
                          context.stop(self)
                        }else{
                          sender ! PingMessage
                        }
    case _ => println("Ping got something unexpected")
  }
}
class Pong extends  Actor{
  def receive ={
    case PingMessage => println("pong")
                     sender ! PongMessage
    case StopMessage => println("pong stopped")
      context.stop(self)
    case _ => println("pong got something unexpected")
  }
}
object CommunitcatingActors extends App {
 val system = ActorSystem("PingPong")
 val pong = system.actorOf(Props[Pong],name="Pong")
  val ping = system.actorOf(Props(new Ping(pong)),name="Ping")
  ping ! StartMessage
  system.terminate()
}
