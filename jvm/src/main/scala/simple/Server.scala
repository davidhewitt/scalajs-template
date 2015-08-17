package simple

import akka.actor.ActorSystem
import spray.http.{HttpEntity, MediaTypes}
import spray.routing.SimpleRoutingApp
import scala.concurrent.ExecutionContext.Implicits.global
import autowire._

import scala.util.Properties

object Router extends autowire.Server[String, upickle.Reader, upickle.Writer]{
  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)
  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
}

object Server extends SimpleRoutingApp {
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    startServer("0.0.0.0", port = 8080){
      pathSingleSlash{
        get{
          complete{
            HttpEntity(
              MediaTypes.`text/html`,
              Page.skeleton.render
            )
          }
        }
      } ~
      pathPrefix("app") {
        getFromResourceDirectory("")
      } ~
      pathPrefix("assets") {
        getFromResourceDirectory("web/public/main/lib/")
      } ~
      path("ajax" / Segments){ s =>
        post{
          extract(_.request.entity.asString) { e =>
            complete {
              Router.route[Api](ApiImpl)(
                autowire.Core.Request(
                  s,
                  upickle.read[Map[String, String]](e)
                )
              )
            }
          }
        }
      }
    }
  }
}
