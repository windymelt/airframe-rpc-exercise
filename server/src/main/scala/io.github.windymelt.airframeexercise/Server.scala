package io.github.windymelt.airframeexercise

import wvlet.airframe.http.netty.Netty
import wvlet.airframe.http._

object Server extends App {
  // Create a Router
  val router = RxRouter.of[api.v1.MyServiceImpl]

// Starting a new RPC server.
  Netty.server
    .withRouter(router)
    .withPort(8080)
    .start { server =>
      server.awaitTermination()
    }
}
