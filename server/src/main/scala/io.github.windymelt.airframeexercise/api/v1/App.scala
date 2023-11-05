package io.github.windymelt.airframeexercise.api.v1

import wvlet.airframe.http._

class MyServiceImpl extends MyService:
  override def getUserById(id: Long): Option[User] = id match
    case 42L =>
      println("got 42")
      Some(User(42L, "Windymelt", 1993))
    case _   => None
