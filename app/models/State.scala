package models
import java.util.Currency
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable
import scala.concurrent.{ExecutionContext, Future}
/**
 * A repository for State.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
*/
@Singleton
class State @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._
  case class State(Id:String ,Name:String ,Country:String ,Currency:String ,Timezone:String)
  object State {
    implicit val StateFormat:OFormat[State] = Json.format[State]
  }

  class StateTable(tag: Tag) extends Table[State](tag, "state") {
    def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
  def Name = column[String]("Name", O.Default(""))
  def Country = column[String]("Country", O.Default(""))
  def Currency = column[String]("Currency", O.Default(""))
  def Timezone = column[String]("Timezone", O.Default(""))

    def * = (Id, Name, Country, Currency, Timezone) <> ((State.apply _).tupled, State.unapply)
  }
  private  val states = TableQuery[StateTable]
  def getState = {states}
  val existing = db.run(MTable.getTables)

 // create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(states.baseTableRow.tableName))
        db.run(DBIO.seq(states.schema.create))
  })

// Add new row
def create(req:JsValue): Future[Option[Int]]= db.run {
    states ++= req.as[Seq[State]]
  }
  // List all rows
  def read(): Future[Seq[State]] = db.run {
    states.result
  }
  // Delete by pk
  def delete(Id: String): Future[Int]  = db.run {
     if(Id !="")
       states.filter(_.Id ===Id).delete
     else
       states.delete
  }
  def update(req:JsValue): Future[Int]= db.run {
    states.insertOrUpdate(req.as[State])
  }
}

