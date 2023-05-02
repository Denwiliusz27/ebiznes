package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable

@Singleton
class CategoryController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val categories = mutable.Set[Category]()
  categories += Category(1, "Music")
  categories += Category(2, "Biography")
  categories += Category(3, "Kids")
  categories += Category(4, "History")
  categories += Category(5, "Horror")
  categories += Category(6, "Fiction")
  categories += Category(7, "Religion")
  categories += Category(8, "Sports")
  categories += Category(9, "Romance")
  categories += Category(10, "Travel")

  implicit val categoryJson = Json.format[Category]
  implicit val categoryReqJson = Json.format[CategoryRequest]

  // GET All
  def getAll(): Action[AnyContent] = Action {
    if (categories.isEmpty){
      NoContent
    } else {
      Ok(Json.toJson(categories))
    }
  }

  // GET with ID
  def getCategory(categoryId: Int) = Action {
    var category = categories.find(_.id == categoryId);

    category match {
      case None => NotFound
      case Some(newCategory) => Ok (Json.toJson(newCategory) )
    }
  }

  // POST new category
  def addNewCategory(): Action[AnyContent] = Action { implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var category: Option[CategoryRequest] =
      jsonObj.flatMap(
        Json.fromJson[CategoryRequest](_).asOpt
      )

    category match {
      case Some(reqData) =>
        var newId = categories.maxByOption(_.id).map(_.id)
        newId match {
          case Some(maxId) =>
            var newCategory = Category(maxId+1, reqData.name)
            categories.update(newCategory, true)
            Created(Json.toJson(newCategory))
          case None => BadRequest
        }
      case None => BadRequest
    }
  }

  def updateCategory(categoryId: Int): Action[AnyContent] = Action {implicit request =>
    var data = request.body
    var jsonObj = data.asJson
    var category: Option[CategoryRequest] =
      jsonObj.flatMap(
        Json.fromJson[CategoryRequest](_).asOpt
      )

    category match {
      case Some(reqData) =>
        var tmpCategory = categories.find(_.id == categoryId)
        tmpCategory match {
          case Some(tmpItem) =>
            categories.update(tmpItem, false)
            var newC = Category(categoryId, reqData.name)
            categories.update(newC, true)
            Created(Json.toJson(newC))

          case None => BadRequest
        }
      case None => BadRequest
    }
  }

  def deleteCategory(categoryId: Int): Action[AnyContent] = Action {implicit request =>
    var category = categories.find(_.id == categoryId)
    category match{
      case Some(item) =>
        categories.update(item, false)
        Created(Json.toJson(item))
      case None => BadRequest
    }
  }
}
