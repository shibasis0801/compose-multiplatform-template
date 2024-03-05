package com.example.starter

import com.stark.network.BaseVerticle
import com.stark.network.Response
import com.stark.network.SampleData
import com.stark.network.port
import io.vertx.core.http.HttpServerRequest
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class MainVerticle : BaseVerticle() {
  override suspend fun start() {
    val server = vertx.createHttpServer()
    val router = Router.router(vertx)
    router.apply {
      route().handler {
        val request: HttpServerRequest = it.request()
        val requestPath = request.path()
        val requestMethod = request.method().name()

        logger.info("Received request: $requestMethod $requestPath")
        it.next()
      }
      route().handler(BodyHandler.create())

      get("/")
        .json { routingContext ->
          Response.success(SampleData("Hello", "Server"))
        }

      get("/fail")
        .json {
          Response.error(mapOf("error" to "Failed to fetch data"))
        }

      get("/live-reload")
        .json {
          Response.success(mapOf("status" to "Live reload does not work. So, just hit run in Main.kt"))
        }
    }

    server.requestHandler(router).listen(port)
    logger.info("Listening to requests at $port")
  }
}
