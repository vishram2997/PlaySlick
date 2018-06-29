package controllers
import javax.inject._
import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}
class CurrencyController @Inject()(repo: Currency,
                                    cc: MessagesControllerComponents,
                                    playBodyParsers: PlayBodyParsers
                                    )(implicit ec: ExecutionContext)  extends MessagesAbstractController(cc) {
 def createTable() = Action { implicit  request =>
    repo.createTable
    Ok("Table Created")
 }

 def create:Action[JsValue] = Action.async(playBodyParsers.json) {
  implicit request =>
  repo.create(request.body).map { data =>
   val count: Int = data.getOrElse(0)
   Ok(Json.toJson("result:"+count + " record deleted"))
  }
 }

 def read:Action[AnyContent] = Action.async { implicit request =>
   repo.read().map { data =>
    Ok(Json.toJson(data))
   }
 }

 def update:Action[JsValue] = Action.async(playBodyParsers.json) {
  implicit request =>
  repo.update(request.body).map { data =>
   Ok(Json.toJson("result:"+data + " record updated"))
  }
 }

 def delete(Id: String):Action[AnyContent] = Action.async { implicit request =>
    repo.delete(Id).map { data =>
      Ok(Json.toJson("result:"+data + " record deleted"))
    }
 }
}

