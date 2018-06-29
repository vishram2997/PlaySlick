package models
import java.util.Currency
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
class Currency @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
 import profile.api._
  case class Currency(Id:String ,Name:String ,ISO:String)
  object Currency {
    implicit val CurrencyFormat:OFormat[Currency] = Json.format[Currency]
  }

  class CurrencyTable(tag: Tag) extends Table[Currency](tag, "currency") {
    def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
    def Name = column[String]("Name", O.Default(""))
    def ISO = column[String]("ISO", O.Default(""))

    def * = (Id, Name, ISO) <> ((Currency.apply _).tupled, Currency.unapply)
  }
  private  val currencys = TableQuery[CurrencyTable]
  def getCurrency = {currencys}
  val existing = db.run(MTable.getTables)

// create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(currencys.baseTableRow.tableName))
        db.run(DBIO.seq(currencys.schema.create))
  })

  // Add new row
  def create(req:JsValue): Future[Option[Int]]= db.run {
     currencys ++= req.as[Seq[Currency]]
  }
  // List all rows
  def read(): Future[Seq[Currency]] = db.run {
      currencys.result
  }
  // Delete by pk
  def delete(Id: String): Future[Int]  = db.run {
      if(Id !="")
        currencys.filter(_.Id ===Id).delete
      else
        currencys.delete
  }

  def update(req:JsValue): Future[Int]= db.run {
     currencys.insertOrUpdate(req.as[Currency])
  }
}

