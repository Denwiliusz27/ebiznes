package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val products = new mutable.ListBuffer[Product]()
  products += Product(1, "Dziady", "Adam Mickiewicz", 39, 20, "Book for school")
  products += Product(2, "Lalka", "Boleslaw Prus", 35, 15, "Book about love")
  products += Product(3, "Sherlock Holmes", "Artur Doyle", 28, 10, "Criminal book")
  products += Product(4, "Balladyna", "Juliusz Słowacki", 37, 22, "Romantic drama")

  implicit val productsJson = Json.format[Product]
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
      case Some(item) => Ok (Json.toJson (item) )
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
      case Some(item) =>
        var newId = products.map(_.id).max + 1
        var newProduct = Product(newId, item.name, item.author, item.price, item.amount, item.description)
        products += newProduct
        Created(Json.toJson(newProduct))
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
      case Some(item) =>
        var tmpProduct = products.find(_.id == productId)
        tmpProduct match {
          case Some(tmpItem) =>
            products -= tmpItem
            var newP = Product(productId, item.name, item.author, item.price, item.amount, item.description)
            products += newP
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
        products -= item
        Created(Json.toJson(item))
    }
  }

}
