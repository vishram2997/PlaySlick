package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import scala.concurrent.{ Future, ExecutionContext }



/**
 * A repository for Country.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class Country @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // Country Class End

  case class Country(id: String, name: String, iso: String, currency:String)

  object Country {  
    implicit val CountryFormat = Json.format[Country]
  }

//Country Model End
  /**
   * Here we define the table. It will have a name of people
   */
  private class CountryTable(tag: Tag) extends Table[Country](tag, "country") {
    def id = column[String]("id", O.PrimaryKey, O.Default(""))
    def name = column[String]("name", O.Default(""))
    def iso = column[String]("iso", O.Default(""))
    def currency = column[String]("currency", O.Default(""))
    def currencyFK =    foreignKey("curr_fk", id, currency)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

    def * = (id, name, iso,currency) <> ((Country.apply _).tupled, Country.unapply)
  }

 
  private val country = TableQuery[CountryTable]

  /**
   Add new record
   */
  def create(id: String, name: String, iso: String, currency:String): Future[Country] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    country += (id, name, iso,currency)
  }

  /**
   * List all the record in the database.
   */
  def read(): Future[Seq[Country]] = db.run {
    country.result
  }

  /*
  * Delete by id or '' for all
  */

  def delete(id: String): Future[Int]  = db.run {
    if(id != "")
      country.filter(_.id === "").delete
    else
      country.delete
  }

  /*
  Update record
  */
  def update(id: String, name: String, iso: String, currency:String): Future[Int]  = db.run {
    country.filter(_.id === id).map(ab => (ab.name , ab.iso,ab.currency)).update((name,iso,currency))
  }
}