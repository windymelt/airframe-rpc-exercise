package io.github.windymelt.airframeexercise.client

import scalajs.js.annotation.*
import io.github.windymelt.airframeexercise.api.v1._
import wvlet.airframe.http.Http

object ClientMain {
  val client =
    ServiceRPC.newRPCAsyncClient(Http.client.newAsyncClient("localhost:5173")) // Vite proxy
  @main def main(): Unit = {
    println("Hello, Scala.js!")
    println(client)
    val result = client.MyService
      .getUserById(42L)
      .run(result => println(s"got $result"))
    println(result)
  }
}
