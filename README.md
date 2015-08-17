Scalajs-Template
================

Basic sbt template for a cross-compile scalajs project.

Based on https://github.com/scala-js/scalajs-cross-compile-example

Integrated sbt-web and sbt-revolver.

To try it out, open sbt and run:

    sbt> re-start

To close the web process, instead run:

    sbt> re-stop

More documentation coming soon!

## Using as a base

Create a new empty git repository for your new project. Clone that repository into some folder on your development machine. Then cd into this folder and run:

    git archive master | tar -x -C /path/to/your/new/project
