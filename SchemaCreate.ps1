$path = "C:\Vishram\pashant\"
$controllerPath = "C:\Vishram\ERP\PlaySlick\app\controllers\"
$modalPath = "C:\Vishram\ERP\PlaySlick\app\models\"
$tables = import-csv ($path+"tableStruct.csv")

$requsetFields =""
$classLine =""
$classLine2 =""
$name =""
$fieldLine = ""
$allFieldId = ""
$pkId =""
$pkType =""
ForEach ($line in $tables){
    
    $name = $($line.Name)
    $id = $($line.FieldId)
    $type = $($line.Type)
    $pk = $($line.PK)
    $default = $($line.Default)

    if($pk -ne "")
    {
         $classLine = $id +":" +$type
         $fieldLine += "def "+$id +" = column[String](`"$id`", O.PrimaryKey, O.Default("+$default+"))`n"
         $allFieldId += $id
         $pkId = $id
         $pkType = $type
    }
    else
    {
         $fieldLine += "  def "+$id +" = column[String](`"$id`", O.Default("+$default+"))`n"
         $classLine   += " ," +$id +":" +$type
         $allFieldId += ", "+$id
    }


    $requsetFields += "  val "+$id+":"+$type+" = (request.body \ `""+$id+"`").as["+$type+"]`n"
   
    $classLine2 = "class "+$name+"Table(tag: Tag) extends Table["+$name+"](tag, `""+$name.ToLower()+"`") {"
   

    
}
$outPutData =""
 $outPutData += "package models`n"
$outPutData +=  "import java.util.Currency`n"

$outPutData +=  "import javax.inject.{Inject, Singleton}`n"
$outPutData +=  "import play.api.db.slick.DatabaseConfigProvider`n"
$outPutData +=  "import slick.jdbc.JdbcProfile`n"
$outPutData +=  "import play.api.libs.json._`n"
$outPutData +=  "import slick.jdbc.meta.MTable`n"

$outPutData +=  "import scala.concurrent.{ExecutionContext, Future}`n"

$outPutData +=  "/**`n"
$outPutData +=  " * A repository for "+$name+".`n"
$outPutData +=  " *`n"
$outPutData +=  " * @param dbConfigProvider The Play db config provider. Play will inject this for you.`n"
$outPutData +=  "*/`n"
$outPutData +=  "@Singleton`n"

$outPutData +=  "class "+$name+" @Inject() (dbConfigProvider: DatabaseConfigProvider`n"
$outPutData +=  "                         )(implicit ec: ExecutionContext) {`n"
$outPutData +=  "  val dbConfig = dbConfigProvider.get[JdbcProfile]`n"

$outPutData +=  "  import dbConfig._`n"
$outPutData +=  "  import profile.api._`n"

$outPutData += "  case class " + $name +"("+$classLine+")`n"
$outPutData += "  object "+$name+" {`n"
$outPutData += "    implicit val "+$name+"Format:OFormat["+$name+"] = Json.format["+$name+"]`n"
$outPutData += "  }`n`n"
$outPutData += "  "+$classLine2+"`n"
$outPutData += "    "+$fieldLine+"`n"
$outPutData += "    def * = ("+$allFieldId+") <> (("+$name+".apply _).tupled, "+$name+".unapply)`n"
$outPutData += "  }`n"
$tableQuery = $name.ToLower().Insert($name.Length,"s")
$outPutData += "  private  val "+$tableQuery+" = TableQuery["+$name+"Table]`n"
$outPutData += "  def "+$name.Insert(0,"get")+" = {"+$tableQuery+"}`n"
$outPutData += "  val existing = db.run(MTable.getTables)`n`n"
$outPutData += " // create table schema`n"
$outPutData += "  def createTable=`n"
$outPutData += "    existing.map( v => {`n"
$outPutData += "      val tables = v.map(mt => mt.name.name)`n"
$outPutData += ("      if(!tables.contains("+$tableQuery+".baseTableRow.tableName))`n")
$outPutData += ("        db.run(DBIO.seq("+$tableQuery+".schema.create))`n")
$outPutData += "  })`n`n"

$outPutData += "// Add new row`n"
$outPutData += (  "def create(req:JsValue): Future[Option[Int]]= db.run {`n")
$outPutData +=  ("    "+$tableQuery+" ++= req.as[Seq["+$name+"]]`n")
$outPutData += "  }`n"

$outPutData += "  // List all rows`n"
$outPutData += ("  def read(): Future[Seq["+$name+"]] = db.run {`n")
$outPutData += "    "+$tableQuery+".result`n"
$outPutData += "  }`n"

$outPutData += "  // Delete by pk`n"
$outPutData += ("  def delete("+$pkId+": "+$pkType+"): Future[Int]  = db.run {`n")
$outPutData += "     if("+$pkId+" !="+$default+")`n"
$outPutData += "       "+$tableQuery+".filter(_."+$pkId+" ==="+$pkId+").delete`n"
$outPutData += "     else`n"
$outPutData += "       "+$tableQuery+".delete`n"
$outPutData += "  }`n"

$updateField = $allFieldId.Replace(" "," ab.")


$outPutData += "  def update(req:JsValue): Future[Int]= db.run {`n"
$outPutData += "    "+$tableQuery+".insertOrUpdate(req.as["+$name+"])`n"
$outPutData += "  }`n"
$outPutData += "}`n"

#$outPutData
Set-Content -Path ($modalPath+$name+".scala") -Value $outPutData -Force

#controller 

#controller class code
$outController =""
$outController = "package controllers`n"

$outController += "import javax.inject.`_`n"
$outController += "import models.`_`n"
$outController += "import play.api.data.Form`n"
$outController += "import play.api.data.Forms.`_`n"
$outController += "import play.api.data.validation.Constraints.`_`n"
$outController += "import play.api.i18n.`_`n"
$outController += "import play.api.libs.json.{JsValue, Json}`n"
$outController += "import play.api.mvc.`_`n"

$outController += "import scala.concurrent.{ExecutionContext, Future}`n"
$outController += "class "+$name+"Controller @Inject()(repo: "+$name+",`n"
$outController += "                                    cc: MessagesControllerComponents,`n"
$outController += "                                    playBodyParsers: PlayBodyParsers`n"
$outController += "                                    )(implicit ec: ExecutionContext)  extends MessagesAbstractController(cc) {`n"

$outController += " def createTable() = Action { implicit  request =>`n"
$outController += "    repo.createTable`n"
$outController += "    Ok(`"Table Created`")`n"
$outController += " }`n`n"

$outController += " def create:Action[JsValue] = Action.async(playBodyParsers.json) {`n"
$outController += "  implicit request =>`n"
$outController += "  repo.create(request.body).map { data =>`n"
$outController += "   val count: Int = data.getOrElse(0)`n"
$outController += "   Ok(Json.toJson(`"result:`"+count + `" record deleted`"))`n"
$outController += "  }`n"
$outController += " }`n`n"

$outController += " def read:Action[AnyContent] = Action.async { implicit request =>`n"
$outController += "   repo.read().map { data =>`n"
$outController += "    Ok(Json.toJson(data))`n"
$outController += "   }`n"
$outController += " }`n`n"

$outController += " def update:Action[JsValue] = Action.async(playBodyParsers.json) {`n"
$outController += "  implicit request =>`n"
$outController += "  repo.update(request.body).map { data =>`n"
$outController += "   Ok(Json.toJson(`"result:`"+data + `" record updated`"))`n"
$outController += "  }`n"
$outController += " }`n`n"

$outController += " def delete("+$pkId+": "+$pkType+"):Action[AnyContent] = Action.async { implicit request =>`n"
$outController += "    repo.delete("+$pkId+").map { data =>`n"
$outController += "      Ok(Json.toJson(`"result:`"+data + `" record deleted`"))`n"
$outController += "    }`n"
$outController += " }`n"
$outController += "}`n"


Set-Content -Path ($controllerPath+$name+"Controller.scala") -Value $outController -Force






