package controllers

case class PaymentRequest(cardNr: String, productName: String, price: Int, amount: Int)
