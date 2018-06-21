package controllers

import javax.inject._

import models._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class PersonController @Inject()(repo: PersonRepository,
                                  cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {



  /**
   * The add person action.
   *
   * This is asynchronous, since we're invoking the asynchronous methods on PersonRepository.
   */
  def addNew(name:String,age:Int) :Action[AnyContent] = Action.async {
    implicit request =>
    repo.create(name, age).map {people =>
      Ok(Json.toJson(people))
    }
  }


  /**
   * A REST endpoint that gets all the people as JSON.
   */
  def getPersons:Action[AnyContent] = Action.async { implicit request =>
    repo.list().map { people =>
      Ok(Json.toJson(people))
    }
  }

  def deletePerson(id: Long):Action[AnyContent] = Action.async { implicit request =>
    repo.deletePerson(id).map { people =>
      Ok(people +" record deleted")
    }
  }

  def update(id: Long, name: String, age: Int):Action[AnyContent] = Action.async { implicit request =>
    repo.update(id,name,age).map { people =>
      Ok(people +" record Updated")
    }
  }

}

/**
 * The create person form.
 *
 * Generally for forms, you should define separate objects to your models, since forms very often need to present data
 * in a different way to your models.  In this case, it doesn't make sense to have an id parameter in the form, since
 * that is generated once it's created.
 */
case class CreatePersonForm(name: String, age: Int)
