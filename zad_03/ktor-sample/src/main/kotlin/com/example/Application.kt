package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.kord.core.on
import com.example.plugins.*
import dev.kord.core.Kord
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.gateway.Intent
import dev.kord.gateway.PrivilegedIntent
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import dev.kord.core.entity.channel.TextChannel
import dev.kord.common.entity.Snowflake
import com.fasterxml.jackson.databind.*

data class MessageRequest(val content: String)

suspend fun main() {
    val kord = Kord("MTA5NzU2NDIyMDA5MjA2NzkzMA.GyPn1Y.ES7bK1IJ79y4-OkLIB5m0QPZaO564iFBCF_13c")
    val channelId = Snowflake(1094220323987128363)

    kord.on<MessageCreateEvent> {
        if (message.author?.isBot != false) {
            return@on
        }

        if (message.content != "!ping") {
            return@on
        }

        message.channel.createMessage("pong!")
    }

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        messageSenderModule(kord, channelId)
    }.start(wait = true)

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}

fun Application.messageSenderModule(kord: Kord, channelId: Snowflake){
    routing {
        post("/send_msg") {
            val message = call.receive<String>()
            val channel: TextChannel? = kord.getChannelOf(channelId)
            if (channel != null) {
                channel.createMessage(message)
                call.respond(HttpStatusCode.OK, "Message sucessfully sended to Discord Server")
            } else {
                call.respond(HttpStatusCode.Conflict, "Message cannot be send")
            }
        }
    }
}
