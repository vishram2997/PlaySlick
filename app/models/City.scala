package models
import java.util.Currency
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable
import scala.concurrent.{ExecutionContext, Future}
/**
 * A repository for City.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
*/
@Singleton
class City @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._
  case class City(Id:String ,Name:String ,Country:String ,State:String)
  object City {
    implicit val CityFormat:OFormat[City] = Json.format[City]
  }

  class CityTable(tag: Tag) extends Table[City](tag, "city") {
    def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
  def Name = column[String]("Name", O.Default(""))
  def Country = column[String]("Country", O.Default(""))
  def State = column[String]("State", O.Default(""))

    def * = (Id, Name, Country, State) <> ((City.apply _).tupled, City.unapply)
  }
  private  val citys = TableQuery[CityTable]
  def getCity = {citys}
  val existing = db.run(MTable.getTables)

 // create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(citys.baseTableRow.tableName))
        db.run(DBIO.seq(citys.schema.create))
  })

// Add new row
def create(req:JsValue): Future[Option[Int]]= db.run {
    citys ++= req.as[Seq[City]]
  }
  // List all rows
  def read(): Future[Seq[City]] = db.run {
    citys.result
  }
  // Delete by pk
  def delete(Id: String): Future[Int]  = db.run {
     if(Id !="")
       citys.filter(_.Id ===Id).delete
     else
       citys.delete
  }
  def update(req:JsValue): Future[Int]= db.run {
    citys.insertOrUpdate(req.as[City])
  }
}

