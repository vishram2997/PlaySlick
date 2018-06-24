package models

import java.util.Currency

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable

import scala.concurrent.{ExecutionContext, Future}

/**
 * A repository for Country.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class Country @Inject() (dbConfigProvider: DatabaseConfigProvider,
                         currency: Currency)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  val currencyTable:Currency = currency
  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._



  // Country Class End

  case class Country(id: String, name: String, iso: String, currency:String)

  object Country {  
    implicit val CountryFormat:OFormat[Country] = Json.format[Country]
  }

//Country Model End
  /**
   * Here we define the table. It will have a name of people
   */



  class CountryTable(tag: Tag) extends Table[Country](tag, "country") {
    def id = column[String]("id", O.PrimaryKey, O.Default(""))
    def name = column[String]("name", O.Default(""))
    def iso = column[String]("iso", O.Default(""))
    def currency = column[String]("currency", O.Default(""))
    def curr_fk =    foreignKey("curr_fk", currency,currencyTable.getCurrency )(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, name, iso,currency) <> ((Country.apply _).tupled, Country.unapply)
  }


  private  val countries = TableQuery[CountryTable]

  def getCountry = {
    countries
  }

  // create table schema
  def createTable():Future[Unit]= db.run {DBIO.seq(
    MTable.getTables map (tables => {
      if (!tables.exists(_.name.name == countries.baseTableRow.tableName))
        countries.schema.create
    })
  )
  }
  /**
   Add new record
   */
  def create(id: String, name: String, iso: String, currency:String): Future[Int] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    countries += Country(id, name, iso,currency)
  }

  /**
   * List all the record in the database.
   */
  def read(): Future[Seq[Country]] = db.run {
    countries.result
  }

  /*
  * Delete by id or '' for all
  */

  def delete(id: String): Future[Int]  = db.run {
    if(id != "")
      countries.filter(_.id === "").delete
    else
      countries.delete
  }

  /*
  Update record
  */
  def update(id: String, name: String, iso: String, currency:String): Future[Int]  = db.run {
    countries.filter(_.id === id).map(ab => (ab.name , ab.iso,ab.currency)).update((name,iso,currency))
  }
}