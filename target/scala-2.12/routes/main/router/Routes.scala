// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/vsingh/Desktop/Vishram/Scala/PlaySlick/conf/routes
// @DATE:Tue Jun 19 16:58:01 EDT 2018

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:6
  PersonController_1: controllers.PersonController,
  // @LINE:14
  Assets_0: controllers.Assets,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:6
    PersonController_1: controllers.PersonController,
    // @LINE:14
    Assets_0: controllers.Assets
  ) = this(errorHandler, PersonController_1, Assets_0, "/")

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, PersonController_1, Assets_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.PersonController.index"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """persons""", """controllers.PersonController.getPersons"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """addPerson(""" + "$" + """name<[^/]+>,""" + "$" + """age<[^/]+>)""", """controllers.PersonController.addNew(name:String, age:Int)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """remove(""" + "$" + """id<[^/]+>)""", """controllers.PersonController.deletePerson(id:Long)"""),
    ("""POST""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """update(""" + "$" + """id<[^/]+>,""" + "$" + """name<[^/]+>,""" + "$" + """age<[^/]+>)""", """controllers.PersonController.update(id:Long, name:String, age:Int)"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """assets/""" + "$" + """file<.+>""", """controllers.Assets.versioned(path:String = "/public", file:Asset)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:6
  private[this] lazy val controllers_PersonController_index0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_PersonController_index0_invoker = createInvoker(
    PersonController_1.index,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "index",
      Nil,
      "GET",
      this.prefix + """""",
      """ Home page""",
      Seq()
    )
  )

  // @LINE:8
  private[this] lazy val controllers_PersonController_getPersons1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("persons")))
  )
  private[this] lazy val controllers_PersonController_getPersons1_invoker = createInvoker(
    PersonController_1.getPersons,
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "getPersons",
      Nil,
      "GET",
      this.prefix + """persons""",
      """POST    /person                    controllers.PersonController.addPerson""",
      Seq()
    )
  )

  // @LINE:9
  private[this] lazy val controllers_PersonController_addNew2_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("addPerson("), DynamicPart("name", """[^/]+""",true), StaticPart(","), DynamicPart("age", """[^/]+""",true), StaticPart(")")))
  )
  private[this] lazy val controllers_PersonController_addNew2_invoker = createInvoker(
    PersonController_1.addNew(fakeValue[String], fakeValue[Int]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "addNew",
      Seq(classOf[String], classOf[Int]),
      "POST",
      this.prefix + """addPerson(""" + "$" + """name<[^/]+>,""" + "$" + """age<[^/]+>)""",
      """""",
      Seq()
    )
  )

  // @LINE:10
  private[this] lazy val controllers_PersonController_deletePerson3_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("remove("), DynamicPart("id", """[^/]+""",true), StaticPart(")")))
  )
  private[this] lazy val controllers_PersonController_deletePerson3_invoker = createInvoker(
    PersonController_1.deletePerson(fakeValue[Long]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "deletePerson",
      Seq(classOf[Long]),
      "POST",
      this.prefix + """remove(""" + "$" + """id<[^/]+>)""",
      """""",
      Seq()
    )
  )

  // @LINE:11
  private[this] lazy val controllers_PersonController_update4_route = Route("POST",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("update("), DynamicPart("id", """[^/]+""",true), StaticPart(","), DynamicPart("name", """[^/]+""",true), StaticPart(","), DynamicPart("age", """[^/]+""",true), StaticPart(")")))
  )
  private[this] lazy val controllers_PersonController_update4_invoker = createInvoker(
    PersonController_1.update(fakeValue[Long], fakeValue[String], fakeValue[Int]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.PersonController",
      "update",
      Seq(classOf[Long], classOf[String], classOf[Int]),
      "POST",
      this.prefix + """update(""" + "$" + """id<[^/]+>,""" + "$" + """name<[^/]+>,""" + "$" + """age<[^/]+>)""",
      """""",
      Seq()
    )
  )

  // @LINE:14
  private[this] lazy val controllers_Assets_versioned5_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), StaticPart("assets/"), DynamicPart("file", """.+""",false)))
  )
  private[this] lazy val controllers_Assets_versioned5_invoker = createInvoker(
    Assets_0.versioned(fakeValue[String], fakeValue[Asset]),
    play.api.routing.HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.Assets",
      "versioned",
      Seq(classOf[String], classOf[Asset]),
      "GET",
      this.prefix + """assets/""" + "$" + """file<.+>""",
      """ Map static resources from the /public folder to the /assets URL path""",
      Seq()
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:6
    case controllers_PersonController_index0_route(params@_) =>
      call { 
        controllers_PersonController_index0_invoker.call(PersonController_1.index)
      }
  
    // @LINE:8
    case controllers_PersonController_getPersons1_route(params@_) =>
      call { 
        controllers_PersonController_getPersons1_invoker.call(PersonController_1.getPersons)
      }
  
    // @LINE:9
    case controllers_PersonController_addNew2_route(params@_) =>
      call(params.fromPath[String]("name", None), params.fromPath[Int]("age", None)) { (name, age) =>
        controllers_PersonController_addNew2_invoker.call(PersonController_1.addNew(name, age))
      }
  
    // @LINE:10
    case controllers_PersonController_deletePerson3_route(params@_) =>
      call(params.fromPath[Long]("id", None)) { (id) =>
        controllers_PersonController_deletePerson3_invoker.call(PersonController_1.deletePerson(id))
      }
  
    // @LINE:11
    case controllers_PersonController_update4_route(params@_) =>
      call(params.fromPath[Long]("id", None), params.fromPath[String]("name", None), params.fromPath[Int]("age", None)) { (id, name, age) =>
        controllers_PersonController_update4_invoker.call(PersonController_1.update(id, name, age))
      }
  
    // @LINE:14
    case controllers_Assets_versioned5_route(params@_) =>
      call(Param[String]("path", Right("/public")), params.fromPath[Asset]("file", None)) { (path, file) =>
        controllers_Assets_versioned5_invoker.call(Assets_0.versioned(path, file))
      }
  }
}
