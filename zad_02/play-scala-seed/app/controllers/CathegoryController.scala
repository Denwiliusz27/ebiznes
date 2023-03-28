package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class CathegoryController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val cathegories = mutable.Set[Cathegory]()
  cathegories += Cathegory(1, "Music")
  cathegories += Cathegory(2, "Biography")
  cathegories += Cathegory(3, "Kids")
  cathegories += Cathegory(4, "History")
  cathegories += Cathegory(5, "Horror")

  implicit val cathegoryJson = Json.format[Cathegory]
  implicit val cathegoryReqJson = Json.format[CathegoryRequest]

  // GET All
  def getAll(): Action[AnyContent] = Action {
    if (cathegories.isEmpty){
      NoContent
    } else {
      Ok(Json.toJson(cathegories))
    }
  }

  // GET with ID
  def getCathegory(cathegoryId: Int) = Action {
    var cathegory = cathegories.find(_.id == cathegoryId);

    cathegory match {
      case None => NotFound
      case Some(newCathegory) => Ok (Json.toJson(newCathegory) )
    }
  }

  // POST new cathegory
  def addNewCathegory(): Action[AnyContent] = Action { implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var cathegory: Option[CathegoryRequest] =
      jsonObj.flatMap(
        Json.fromJson[CathegoryRequest](_).asOpt
      )

    product match {
      case Some(reqData) =>
        var newId = products.maxByOption(_.id).map(_.id)
        newId match {
          case Some(maxId) =>
            var newProduct = Product(maxId+1, reqData.name, reqData.author, reqData.price, reqData.amount, reqData.description)
            products.update(newProduct, true)
            Created(Json.toJson(newProduct))
          case None => BadRequest
        }
      case None => BadRequest
    }
  }

  def updateProduct(productId: Int): Action[AnyContent] = Action {implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var product: Option[ProductRequest] =
      jsonObj.flatMap(
        Json.fromJson[ProductRequest](_).asOpt
      )

    product match {
      case Some(reqData) =>
        var tmpProduct = products.find(_.id == productId)
        tmpProduct match {
          case Some(tmpItem) =>
            products.update(tmpItem, false)
            var newP = Product(productId, reqData.name, reqData.author, reqData.price, reqData.amount, reqData.description)
            products.update(newP, true)
            Created(Json.toJson(newP))

          case None => BadRequest
        }
      case None => BadRequest
    }
  }

  def deleteProduct(productId: Int): Action[AnyContent] = Action {implicit request =>
    var product = products.find(_.id == productId)
    product match{
      case Some(item) =>
        products.update(item, false)
        Created(Json.toJson(item))
    }
  }
}
