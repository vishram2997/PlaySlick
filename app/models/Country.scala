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
class Country @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._
  case class Country(Id:String ,Name:String ,ISO:String ,Currency:String)
  object Country {
    implicit val CountryFormat:OFormat[Country] = Json.format[Country]
  }

  class CountryTable(tag: Tag) extends Table[Country](tag, "country") {
    def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
  def Name = column[String]("Name", O.Default(""))
  def ISO = column[String]("ISO", O.Default(""))
  def Currency = column[String]("Currency", O.Default(""))

    def * = (Id, Name, ISO, Currency) <> ((Country.apply _).tupled, Country.unapply)
  }
  private  val countrys = TableQuery[CountryTable]
  def getCountry = {countrys}
  val existing = db.run(MTable.getTables)

 // create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(countrys.baseTableRow.tableName))
        db.run(DBIO.seq(countrys.schema.create))
  })

// Add new row
def create(req:JsValue): Future[Option[Int]]= db.run {
    countrys ++= req.as[Seq[Country]]
  }
  // List all rows
  def read(): Future[Seq[Country]] = db.run {
    countrys.result
  }
  // Delete by pk
  def delete(Id: String): Future[Int]  = db.run {
     if(Id !="")
       countrys.filter(_.Id ===Id).delete
     else
       countrys.delete
  }
  def update(req:JsValue): Future[Int]= db.run {
    countrys.insertOrUpdate(req.as[Country])
  }
}

