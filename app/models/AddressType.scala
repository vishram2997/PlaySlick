package models
import java.util.Currency
import javax.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import play.api.libs.json._
import slick.jdbc.meta.MTable
import scala.concurrent.{ExecutionContext, Future}
/**
 * A repository for AddressType.
 *
 * @param dbConfigProvider The Play db config provider. Play will inject this for you.
*/
@Singleton
class AddressType @Inject() (dbConfigProvider: DatabaseConfigProvider
                         )(implicit ec: ExecutionContext) {
  val dbConfig = dbConfigProvider.get[JdbcProfile]
  import dbConfig._
  import profile.api._
  case class AddressType(Id:String ,Desc:String)
  object AddressType {
    implicit val AddressTypeFormat:OFormat[AddressType] = Json.format[AddressType]
  }

  class AddressTypeTable(tag: Tag) extends Table[AddressType](tag, "addresstype") {
    def Id = column[String]("Id", O.PrimaryKey, O.Default(""))
  def Desc = column[String]("Desc", O.Default(""))

    def * = (Id, Desc) <> ((AddressType.apply _).tupled, AddressType.unapply)
  }
  private  val addresstypes = TableQuery[AddressTypeTable]
  def getAddressType = {addresstypes}
  val existing = db.run(MTable.getTables)

 // create table schema
  def createTable=
    existing.map( v => {
      val tables = v.map(mt => mt.name.name)
      if(!tables.contains(addresstypes.baseTableRow.tableName))
        db.run(DBIO.seq(addresstypes.schema.create))
  })

// Add new row
def create(req:JsValue): Future[Option[Int]]= db.run {
    addresstypes ++= req.as[Seq[AddressType]]
  }
  // List all rows
  def read(): Future[Seq[AddressType]] = db.run {
    addresstypes.result
  }
  // Delete by pk
  def delete(Id: String): Future[Int]  = db.run {
     if(Id !="")
       addresstypes.filter(_.Id ===Id).delete
     else
       addresstypes.delete
  }
  def update(req:JsValue): Future[Int]= db.run {
    addresstypes.insertOrUpdate(req.as[AddressType])
  }
}

