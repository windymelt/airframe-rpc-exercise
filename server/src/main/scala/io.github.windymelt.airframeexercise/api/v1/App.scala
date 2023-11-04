package io.github.windymelt.airframeexercise.api.v1

import wvlet.airframe.http._
import wvlet.airframe.http.netty.Netty

class MyServiceImpl extends MyService:
  override def getUserById(id: Long): Option[User] = id match
    case 42L => Some(User(42L, "Windymelt", 1993))
    case _   => None

object Main extends App:
  // Create a Router
  val router = RxRouter.of[MyServiceImpl]

// Starting a new RPC server.
  Netty.server
    .withRouter(router)
    .withPort(8080)
    .start { server =>
      server.awaitTermination()
    }
