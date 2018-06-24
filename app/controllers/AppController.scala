package controllers

import javax.inject._

import models._
import play.api.data.Form
import slick.jdbc.meta.MTable
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.i18n._
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AppController @Inject()(currency: Currency,
                              country: Country,
                              state: State,
                              timezone: Timezone,
                              personRepository: PersonRepository,
                              cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {


  def createTables = Action { implicit  request =>


    timezone.createTable()
    currency.createTable()
    country.createTable
    state.createTable
    Ok("Tables Created")
  }

}