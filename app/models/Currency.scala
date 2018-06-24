package models

import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable

import scala.concurrent.{ExecutionContext, Future}



/**
 * A repository for Currency.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class Currency @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // Currency Class End

  case class Currency(id: String, name: String, iso: String)

  object Currency {  
    implicit val CurrencyFormat:OFormat[Currency] = Json.format[Currency]
  }

//Currency Model End
  /**
   * Here we define the table. It will have a name of people
   */
   class CurrencyTable(tag: Tag) extends Table[Currency](tag, "currency") {
    def id = column[String]("id", O.PrimaryKey, O.Default(""))
    def name = column[String]("name", O.Default(""))
    def iso = column[String]("iso", O.Default(""))

    def * = (id, name, iso) <> ((Currency.apply _).tupled, Currency.unapply)
  }


  private val currency = TableQuery[CurrencyTable]

  def getCurrency = {
    TableQuery[CurrencyTable]
  }


  // create table schema
  def createTable():Future[Unit]= db.run {DBIO.seq(
    MTable.getTables map (tables => {
      if (!tables.exists(_.name.name == currency.baseTableRow.tableName))
        currency.schema.create
    })
  )
  }
  /**
   Add new record
   */
  def create(id: String, name: String, iso: String): Future[Int] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    currency += Currency(id, name, iso)
  }

  /**
   * List all the record in the database.
   */
  def read(): Future[Seq[Currency]] = db.run {
    currency.result
  }

  /*
  * Delete by id or '' for all
  */

  def delete(id: String): Future[Int]  = db.run {
    if(id != "")
      currency.filter(_.id === "").delete
    else
      currency.delete
  }

  /*
  Update record
  */
  def update(id: String, name: String, iso: String): Future[Int]  = db.run {
    currency.filter(_.id === id).map(ab => (ab.name , ab.iso)).update((name,iso))
  }
}