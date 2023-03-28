package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class ShoppingBasketController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val shoppingBasket = mutable.Set[ShoppingProduct]()
  shoppingBasket += ShoppingProduct(1, "apple", 2, 4.5)
  shoppingBasket += ShoppingProduct(2, "bread", 1, 5.0)
  shoppingBasket += ShoppingProduct(3, "potatoe", 10, 4.0)
  shoppingBasket += ShoppingProduct(4, "spaghetti", 2, 7.2)

  implicit val shoppingProductElementJson = Json.format[ShoppingProduct]
  implicit val shoppingProductElementReqJson = Json.format[ShoppingProductRequest]

  // GET All
  def getAll(): Action[AnyContent] = Action {
    if (shoppingBasket.isEmpty){
      NoContent
    } else {
      Ok(Json.toJson(shoppingBasket))
    }
  }

  // GET with ID
  def getShoppingProduct(productId: Int) = Action {
    var shoppingProduct = shoppingBasket.find(_.id == productId);

    shoppingProduct match {
      case None => NotFound
      case Some(newShoppingProduct) => Ok (Json.toJson(newShoppingProduct) )
    }
  }

  // POST new category
  def addNewShoppingProduct(): Action[AnyContent] = Action { implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var shoppingProduct: Option[ShoppingProductRequest] =
      jsonObj.flatMap(
        Json.fromJson[ShoppingProductRequest](_).asOpt
      )

    shoppingProduct match {
      case Some(reqData) =>
        var newId = shoppingBasket.maxByOption(_.id).map(_.id)
        newId match {
          case Some(maxId) =>
            var newProduct = ShoppingProduct(maxId+1, reqData.productName, reqData.amount, reqData.price)
            shoppingBasket.update(newProduct, true)
            Created(Json.toJson(newProduct))
          case None => BadRequest
        }
      case None => BadRequest
    }
  }

  def updateShoppingProduct(productId: Int): Action[AnyContent] = Action {implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var shoppingProduct: Option[ShoppingProductRequest] =
      jsonObj.flatMap(
        Json.fromJson[ShoppingProductRequest](_).asOpt
      )

    shoppingProduct match {
      case Some(reqData) =>
        var tmpShoppingProduct = shoppingBasket.find(_.id == productId)
        tmpShoppingProduct match {
          case Some(tmpItem) =>
            shoppingBasket.update(tmpItem, false)
            var newP = ShoppingProduct(productId, reqData.productName, reqData.amount, reqData.price)
            shoppingBasket.update(newP, true)
            Created(Json.toJson(newP))
          case None => BadRequest
        }
      case None => BadRequest
    }
  }

  def deleteShoppingProduct(productId: Int): Action[AnyContent] = Action {implicit request =>
    var shoppingProduct = shoppingBasket.find(_.id == productId)
    shoppingProduct match{
      case Some(item) =>
        shoppingBasket.update(item, false)
        Created(Json.toJson(item))
      case None => BadRequest
    }
  }
}
