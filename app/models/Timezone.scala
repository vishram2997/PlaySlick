package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import scala.concurrent.{ Future, ExecutionContext }



/**
 * A repository for Timezone.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class Timezone @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
   val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // Timezone Class End

  case class Timezone(id: String, name: String, enuName: String)

  object Timezone {  
    implicit val TimezoneFormat:OFormat[Timezone] = Json.format[Timezone]
  }

//Timezone Model End
  /**
   * Here we define the table. It will have a name of people
   */
   class TimezoneTable(tag: Tag) extends Table[Timezone](tag, "timezone") {
    def id = column[String]("id", O.PrimaryKey, O.Default(""))
    def name = column[String]("name", O.Default(""))
    def enuName = column[String]("enuName", O.Default(""))

    def * = (id, name, enuName) <> ((Timezone.apply _).tupled, Timezone.unapply)
  }

 
  private val timezone = TableQuery[TimezoneTable]
  def getTimezone = {
    timezone
  }

  // create table schema
  def createTable():Future[Unit]= db.run {
    timezone.schema.create
  }
  /**
   Add new record
   */
  def create(id: String, name: String, enuName: String): Future[Int] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    timezone += Timezone(id, name, enuName)
  }

  /**
   * List all the record in the database.
   */
  def read(): Future[Seq[Timezone]] = db.run {
    timezone.result
  }

  /*
  * Delete by id or '' for all
  */
  def delete(id: String): Future[Int]  = db.run {
    if(id != "")
      timezone.filter(_.id === "").delete
    else
      timezone.delete

  }

  /*
  Update record
  */
  def update(id: String, name: String, enuName: String): Future[Int]  = db.run {
    timezone.filter(_.id === id).map(ab => (ab.name , ab.enuName)).update((name,enuName))
  }
}