package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class PaymentController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val payments = mutable.Set[Payment]()
//  payments += Payment(1, "2", "Lalka", 12, 1)

  implicit val paymentsJson = Json.format[Payment]
  implicit val paymentReqJson = Json.format[PaymentRequest]

  def getAll(): Action[AnyContent] = Action {
    if (payments.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(payments))
    }
  }

  def addNewPayment(): Action[AnyContent] = Action { implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var payment: Option[PaymentRequest] =
      jsonObj.flatMap(
        Json.fromJson[PaymentRequest](_).asOpt
      )

    payment match {
      case Some(reqData) =>
        var newPayment = Payment(payments.size, reqData.cardNr, reqData.productName, reqData.price, reqData.amount)
        payments.update(newPayment, true)
        Created(Json.toJson(newPayment))
      case None => BadRequest
    }
  }
}
