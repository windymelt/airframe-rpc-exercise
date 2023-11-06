package io.github.windymelt.airframeexercise.client

import com.raquo.laminar.api.L.{*, given}
import io.github.windymelt.airframeexercise.api.v1.User
import io.github.windymelt.airframeexercise.api.v1._
import org.scalajs.dom
import org.w3c.dom.html.HTMLHtmlElement
import wvlet.airframe.http.Http

import scalajs.js.annotation.*

object ClientMain {
  val client =
    ServiceRPC.newRPCAsyncClient(
      Http.client.withMsgPackEncoding.newAsyncClient("localhost:5173")
    ) // Vite proxy
  @main def main(): Unit = {
    println("Hello, Scala.js!")
    println(client)
    val appContainer: dom.Element = dom.document.querySelector("#app")
    renderOnDomContentLoaded(appContainer, appElement)
  }

  def appElement =
    val userIdVar = Var[Long](0L)
    val userVar = Var[Option[User]](None)
    val onSubmitForm = (e: dom.Event) => {
      e.stopPropagation()
      e.preventDefault()
      println(s"submitted ${userIdVar.now()}")
      client
        .MyService
        .getUserById(userIdVar.now())
        .recover{
          case e: Throwable =>
            println("API failed")
            println(e.getMessage())
              None
      }
      .run(userVar.set)
      ()
    }
    div(
      cls := "container",
      p("This is Laminar application! API request is done via Airframe RPC in MessagePack."),
      formElement(userIdVar, onSubmitForm),
      child <-- userVar.signal.changes.map { u =>
        userCard(u)
      }
    )

  def formElement(userIdVar: Var[Long], onSubmitForm: dom.Event => Unit) =
    form(
      onSubmit --> onSubmitForm,
      div(
        cls := "md-3",
        label(
          labelAttr := "userId",
          cls := "form-label",
          "User ID:"
        ),
        input(
          typ := "text",
          cls := "form-control",
          idAttr := "userId",
          placeholder := "42",
          controlled(
            value <-- userIdVar.signal.map(_.toString),
            onInput.mapToValue.collect(((s: String) => s.toLongOption).unlift) --> userIdVar
          )
        )
      ),
      button(
        cls := "btn btn-primary",
        "Get User"
      )
    )

  def userCard(user: Option[User]) = div(
    cls := "card",
    styleAttr := "width: 18rem;",
    div(
      cls := "card-body",
      h5(
        cls := "card-title",
        if (user.isDefined) "User found" else "User not found"
      ),
      if (user.isDefined) {
        List(
          cardSection("Name", user.get.name),
          cardSection("Born at", user.get.born.toString),
          cardSection("ID", user.get.id.toString)
        )
      } else emptyNode
    )
  )

  def cardSection(title: String, text: String) = List(
    h6(
      cls := "card-subtitle mb-2 text-body-secondary",
      title
    ),
    p(
      cls := "card-text",
      text
    ),
  )
}
