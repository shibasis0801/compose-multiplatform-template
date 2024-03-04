package com.stark.network

import io.vertx.core.impl.logging.Logger
import io.vertx.core.impl.logging.LoggerFactory
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseVerticle: CoroutineVerticle() {
    val coroutineScope by lazy { CoroutineScope(coroutineContext) }
    val logger: Logger = LoggerFactory.getLogger(BaseVerticle::class.java)
    inline fun Route.json(
        crossinline handler: suspend (routingContext: RoutingContext) -> Response,
    ) = produces("application/json").handler {
        coroutineScope.launch(coroutineContext) {
            val result = handler(it)
            it.response()
                .putHeader("content-type", "application/json")
                .setStatusCode(result.statusCode.code)
                .end(result.body)
        }
    }
}
