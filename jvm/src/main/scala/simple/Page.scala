package simple

import scalatags.Text.all._

object Page{
  val boot =
    "simple.Client().main(document.getElementById('contents'))"
  val skeleton =
    html(
      head(
        script(src:="/app/app-fastopt.js"),
        script(src:="/app/app-jsdeps.js"),
        link(
          rel:="stylesheet",
          href:="/assets/bootstrap/css/bootstrap.css"
        )
      ),
      body(
        onload:=boot,
        div(id:="contents")
      )
    )
}
