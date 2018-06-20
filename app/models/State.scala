package models

import javax.inject.{ Inject, Singleton }
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import scala.concurrent.{ Future, ExecutionContext }



/**
 * A repository for State.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
 */
@Singleton
class State @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  // We want the JdbcProfile for this provider
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  // These imports are important, the first one brings db into scope, which will let you do the actual db operations.
  // The second one brings the Slick DSL into scope, which lets you define the table and other queries.
  import dbConfig._
  import profile.api._
  // State Class End

  case class State(id: String, name: String, country: String, timeZone:String)

  object State {  
    implicit val StateFormat = Json.format[State]
  }

//State Model End
  /**
   * Here we define the table. It will have a name of people
   */
  private class StateTable(tag: Tag) extends Table[State](tag, "state") {
    def id = column[String]("id", O.PrimaryKey, O.Default(""))
    def name = column[String]("name", O.Default(""))
    def country = column[String]("country", O.Default(""))
    def timeZone = column[String]("timeZone", O.Default(""))

    def countryFK =    foreignKey("country_fk", id, country)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)
    def timezoneFK =    foreignKey("timezone_fk", id, timezone)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)

    def * = (id, name, country,timeZone) <> ((State.apply _).tupled, State.unapply)
  }

 
  private val state = TableQuery[StateTable]

  /**
   Add new record
   */
  def create(id: String, name: String, country: String, timeZone:String): Future[State] = db.run {
    // We create a projection of just the name and age columns, since we're not inserting a value for the id column
    state += (id, name, country,timeZone)
  }

  /**
   * List all the record in the database.
   */
  def read(): Future[Seq[State]] = db.run {
    state.result
  }

  /*
  * Delete by id or '' for all
  */

  def delete(id: String): Future[Int]  = db.run {
    if(id != "")
      state.filter(_.id === "").delete
    else
      state.delete
  }

  /*
  Update record
  */
  def update(id: String, name: String, country: String, timeZone:String): Future[Int]  = db.run {
    state.filter(_.id === id).map(ab => (ab.name , ab.country,ab.timeZone)).update((name,country,timeZone))
  }
}
