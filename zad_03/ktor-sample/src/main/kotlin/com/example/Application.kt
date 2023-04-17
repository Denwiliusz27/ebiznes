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


class Category(val id: Int, var name: String )
class Product(val id: Int, var name: String, var categoryId: Int, var price: Int )

val categories: List<Category> = listOf(
    Category(1, "Dramat"),
    Category(2, "Komedia"),
    Category(3, "Bajka"),
    Category(4, "Horror"),
)

val products: List<Product> = listOf(
    Product(1, "Nietykalni", 1, 30),
    Product(2, "Oskar", 2, 30),
    Product(3, "Ojciec chrzestny", 1, 35),
    Product(4, "Paddington", 3, 20),
    Product(5, "Obcy", 4, 25),
    Product(6, "Król lew", 3, 18),
    Product(7, "Kubuś puchatek", 3, 15),
    Product(8, "Jaś Fasola", 2, 22),
    Product(9, "Lśnienie", 4, 13),
)


suspend fun main() {
    val kord = Kord("MTA5NzU2NDIyMDA5MjA2NzkzMA.GVOjbh.GiLjqy5XS41-sggeBCzaAyxW-ym3OnsHRiHYOM")
    val channelId = Snowflake(1094220323987128363)

    kord.on<MessageCreateEvent> {
        if (message.author?.isBot != false) {
            return@on
        }

        println("Received message from discord: '${message.content}'")

        if (message.content == "!categories") {
            message.channel.createMessage("Kategorie:")
            categories.forEach {
                    i -> message.channel.createMessage(i.name)
            }
        } else if (message.content.startsWith("!products/")){
            val parts = message.content.split("/")
            if (parts.size == 2) {
                val cathegory = parts[1]
                val founded: Category? = categories.find { it.name == cathegory }

                if (founded != null) {
                    message.channel.createMessage("Produkty z kategorii '${cathegory}':")
                    val filteredProducts = products.filter { it.categoryId == founded.id }

                    for (filteredProduct in filteredProducts) {
                        message.channel.createMessage("${filteredProduct.name} - ${filteredProduct.price} zł")
                    }
                } else {
                    message.channel.createMessage("Nie znam takiej kategorii :(")
                }
            }
        } else {
            message.channel.createMessage("Nie znam takiego polecenia :(")
        }
    }

    embeddedServer(Netty, port = 8080) {
        sendMessagesModule(kord, channelId)
    }.start(wait = false)

    kord.login {
        @OptIn(PrivilegedIntent::class)
        intents += Intent.MessageContent
    }
}

fun Application.sendMessagesModule(kord: Kord, channelId: Snowflake){
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
