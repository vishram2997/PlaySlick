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

class StateController @Inject()(repo: State,
                                  cc: MessagesControllerComponents,
                                playBodyParsers: PlayBodyParsers
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  def createTable():Action[AnyContent] = Action { implicit request =>
    repo.createTable
    Ok("Table Created")
  }

  def create:Action[JsValue]= Action.async(playBodyParsers.json) {
      implicit request =>
        val id:String = (request.body \ "id").as[String]
        val name:String = (request.body \ "name").as[String]
        val country:String = (request.body \ "country").as[String]
        val timezone:String = (request.body \ "timezone").as[String]
        repo.create(id,name,country,timezone).map { data =>
          Ok(data + " record inserted")
        }
  }

  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def read:Action[AnyContent] = Action.async { implicit request =>
    repo.read().map { data =>
      Ok(Json.toJson(data))
    }
  }

  def update:Action[JsValue]= Action.async(playBodyParsers.json) { implicit request =>
    val id:String = (request.body \ "id").as[String]
    val name:String = (request.body \ "name").as[String]
    val country:String = (request.body \ "country").as[String]
    val timezone:String = (request.body \ "timezone").as[String]
    repo.update(id,name,country,timezone).map { people =>
      Ok(people +" record Updated")
    }
  }

  def delete(id: String):Action[AnyContent] = Action.async { implicit request =>
    repo.delete(id).map { data =>
      Ok(data +" record deleted")
    }
  }

}

case class CreateStateForm(name: String, age: Int)
