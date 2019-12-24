package org

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn


object Server extends App {

implicit val system: ActorSystem = ActorSystem("helloworld")
implicit val executor:  ExecutionContextExecutor = system.dispatcher
implicit val materializer: ActorMaterializer = ActorMaterializer()


def route = path("hello") { 
   get {  complete("Hello, World!")  }
   
   }

   val serverFuture = Http().bindAndHandle(route, "localhost", 8080)

  println("Server started ...")
  StdIn.readLine()
  serverFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())
}
