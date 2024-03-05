package com.example.starter

import io.vertx.core.Launcher

fun main(args: Array<String>) {
    Launcher.executeCommand("run", MainVerticle::class.java.name)
}
