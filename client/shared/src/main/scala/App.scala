package io.github.windymelt.airframeexercise.client

import scalajs.js.annotation.*
import wvlet.airframe.http.Http
import io.github.windymelt.airframeexercise.api.v1._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import wvlet.airframe.rx.Rx

object ClientMain {
  val client =
    ServiceRPC.newRPCAsyncClient(Http.client.newAsyncClient("localhost:5173")) // Vite proxy
  @main def main(): Unit = {
    println("Hello, Scala.js!")
    println(client)
    val result = client.MyService
      .getUserById(42L)
      // .recoverWith { case e: Throwable =>
      //   println(e.getMessage())
      //   Rx.future(Future(None))
      // }
      .run(result => println(s"got $result"))
    println(result)
  }
}
