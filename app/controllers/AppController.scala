package controllers

import javax.inject._
import models._
import play.api.data.Form
import slick.jdbc.meta.MTable
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.db.slick.DatabaseConfigProvider
import play.api.i18n._
import slick.jdbc.JdbcProfile
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

class AppController @Inject()(currency: Currency,
                              country: Country,
                              dbConfigProvider: DatabaseConfigProvider,
                              state: State,
                              timezone: Timezone,
                              city:City,
                              addressType:AddressType,
                              postCode:PostCode,
                              cc: MessagesControllerComponents
                                )(implicit ec: ExecutionContext)
  extends MessagesAbstractController(cc) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  def createTables = Action { implicit  request =>


    timezone.createTable
    currency.createTable
    country.createTable
    state.createTable
    city.createTable
    postCode.createTable
    addressType.createTable
    Ok("Tables Created")
  }


}