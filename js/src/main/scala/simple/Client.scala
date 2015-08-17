package simple
import scalatags.JsDom.all._
import org.scalajs.dom
import dom.html
import scalajs.js.annotation.{JSExport, JSName}
import scalajs.concurrent.JSExecutionContext.Implicits.runNow
import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.Dynamic.global
import autowire._
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Success, Failure}
import scala.collection.mutable.LinkedList

@JSExport
object Client extends{
  @JSExport
  def main(container: html.Div) = {
    AjaxClient[Api].title().call().onSuccess {
      case title => {
        container.appendChild(
          div(
            `class`:="container",
            h1(title)
          ).render
        )
      }
    }
  }
}
