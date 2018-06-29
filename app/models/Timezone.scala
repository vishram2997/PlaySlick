package models
import java.util.Currency
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable
import scala.concurrent.{ExecutionContext, Future}
/**
 * A repository for Timezone.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
*/
@Singleton
class Timezone @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
 import dbConfig._
 import profile.api._
case class Timezone(Id:String ,Name:String ,ENUName:String)
object Timezone {
  implicit val TimezoneFormat:OFormat[Timezone] = Json.format[Timezone]
}

class TimezoneTable(tag: Tag) extends Table[Timezone](tag, "timezone") {
  def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
  def Name = column[String]("Name", O.Default(""))
  def ENUName = column[String]("ENUName", O.Default(""))

  def * = (Id, Name, ENUName) <> ((Timezone.apply _).tupled, Timezone.unapply)
}
 private  val timezones = TableQuery[TimezoneTable]
 def getTimezone = {timezones}
 val existing = db.run(MTable.getTables)

// create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(timezones.baseTableRow.tableName))
        db.run(DBIO.seq(timezones.schema.create))
  })

// Add new row
def create(req:JsValue): Future[Option[Int]]= db.run {
   timezones ++= req.as[Seq[Timezone]]
 }
// List all rows
def read(): Future[Seq[Timezone]] = db.run {
    timezones.result
 }
// Delete by pk
def delete(Id: String): Future[Int]  = db.run {
    if(Id !="")
      timezones.filter(_.Id ===Id).delete
    else
      timezones.delete
  }
 def update(req:JsValue)= db.run {
   timezones.insertOrUpdate(req.as[Timezone])
 }
}

