package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val products = mutable.Set[Products]()
  products += Products(1, "Dziady", "Adam Mickiewicz", 39, 20, "Book for school")
  products += Products(2, "Lalka", "Boleslaw Prus", 35, 15, "Book about love")
  products += Products(3, "Sherlock Holmes", "Artur Doyle", 28, 10, "Criminal book")
  products += Products(4, "Balladyna", "Juliusz Słowacki", 37, 22, "Romantic drama")

  implicit val productsJson = Json.format[Products]
  implicit val productReqJson = Json.format[ProductRequest]

  // GET All
  def getAll(): Action[AnyContent] = Action {
    if (products.isEmpty){
      NoContent
    } else {
      Ok(Json.toJson(products))
    }
  }

  // GET with ID
  def getProduct(productId: Int) = Action {
    var product = products.find(_.id == productId);

    product match {
      case None => NotFound
      case Some(newProduct) => Ok (Json.toJson(newProduct) )
    }
  }

  // POST new product
  def addNewProduct(): Action[AnyContent] = Action { implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var product: Option[ProductRequest] =
      jsonObj.flatMap(
        Json.fromJson[ProductRequest](_).asOpt
      )

    product match {
      case Some(reqData) =>
        var newId = products.maxByOption(_.id).map(_.id)
        newId match {
          case Some(maxId) =>
            var newProduct = Products(maxId+1, reqData.name, reqData.author, reqData.price, reqData.amount, reqData.description)
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
            var newP = Products(productId, reqData.name, reqData.author, reqData.price, reqData.amount, reqData.description)
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
      case None => BadRequest
    }
  }
}
