package com.stark.network

import io.vertx.core.impl.logging.Logger
import io.vertx.core.impl.logging.LoggerFactory
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.CoroutineVerticle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


typealias Handler = suspend (routingContext: RoutingContext, coroutineScope: CoroutineScope) -> Response

inline fun Route.handle(
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline handler: Handler,
) = produces("application/json").handler {
    coroutineScope.launch(dispatcher) {
        val result = handler(it, this)
        it.response()
            .putHeader("content-type", "application/json")
            .setStatusCode(result.statusCode.code)
            .end(result.jsonData)
    }
}



open class BaseVerticle: CoroutineVerticle() {
    val verticleScope by lazy { CoroutineScope(coroutineContext) }
    val logger: Logger = LoggerFactory.getLogger(BaseVerticle::class.java)
    inline fun Route.handle(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        crossinline handler: Handler,
    ) = handle(verticleScope, dispatcher, handler)
}
