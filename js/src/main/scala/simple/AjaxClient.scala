package simple

import autowire._
import org.scalajs.dom
import scalajs.concurrent.JSExecutionContext.Implicits.runNow

object AjaxClient extends autowire.Client[String, upickle.Reader, upickle.Writer]{
  override def doCall(req: Request) = {
    dom.ext.Ajax.post(
      url = "/ajax/" + req.path.mkString("/"),
      data = upickle.write(req.args)
    ).map(_.responseText)
  }

  def read[Result: upickle.Reader](p: String) = upickle.read[Result](p)
  def write[Result: upickle.Writer](r: Result) = upickle.write(r)
}
