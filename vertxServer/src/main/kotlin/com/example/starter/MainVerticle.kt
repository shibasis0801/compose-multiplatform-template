package com.example.starter

import com.stark.network.BaseVerticle
import com.stark.network.JsonResponse
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
        .handle { ctx, _ ->
          JsonResponse(mapOf("status" to "UP"))
        }
    }

    server.requestHandler(router).listen(port)
    logger.info("Listening to requests at $port")
  }
}
