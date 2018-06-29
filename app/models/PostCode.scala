package models
import java.util.Currency
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable
import scala.concurrent.{ExecutionContext, Future}
/**
 * A repository for PostCode.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
*/
@Singleton
class PostCode @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._
  case class PostCode(Id:String ,Name:String ,City:String ,State:String ,Country:String)
  object PostCode {
    implicit val PostCodeFormat:OFormat[PostCode] = Json.format[PostCode]
  }

  class PostCodeTable(tag: Tag) extends Table[PostCode](tag, "postcode") {
    def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
  def Name = column[String]("Name", O.Default(""))
  def City = column[String]("City", O.Default(""))
  def State = column[String]("State", O.Default(""))
  def Country = column[String]("Country", O.Default(""))

    def * = (Id, Name, City, State, Country) <> ((PostCode.apply _).tupled, PostCode.unapply)
  }
  private  val postcodes = TableQuery[PostCodeTable]
  def getPostCode = {postcodes}
  val existing = db.run(MTable.getTables)

 // create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(postcodes.baseTableRow.tableName))
        db.run(DBIO.seq(postcodes.schema.create))
  })

// Add new row
def create(req:JsValue): Future[Option[Int]]= db.run {
    postcodes ++= req.as[Seq[PostCode]]
  }
  // List all rows
  def read(): Future[Seq[PostCode]] = db.run {
    postcodes.result
  }
  // Delete by pk
  def delete(Id: String): Future[Int]  = db.run {
     if(Id !="")
       postcodes.filter(_.Id ===Id).delete
     else
       postcodes.delete
  }
  def update(req:JsValue): Future[Int]= db.run {
    postcodes.insertOrUpdate(req.as[PostCode])
  }
}

