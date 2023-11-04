package io.github.windymelt.airframeexercise.api.v1

import wvlet.airframe.http._

case class User(id: Long, name: String, born: Int)

@RPC trait MyService:
  def getUserById(id: Long): Option[User]
