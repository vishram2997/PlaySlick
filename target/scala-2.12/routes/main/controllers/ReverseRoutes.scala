// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/vsingh/Desktop/Vishram/Scala/play-scala-slick-example/conf/routes
// @DATE:Tue Jun 19 02:24:58 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers {

  // @LINE:6
  class ReversePersonController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getPersons(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "persons")
    }
  
    // @LINE:9
    def addNew(name:String, age:Int): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "addPerson(" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("name", name)) + "," + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("age", age)) + ")")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:12
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
