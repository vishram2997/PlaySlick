// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/vsingh/Desktop/Vishram/Scala/PlaySlick/conf/routes
// @DATE:Tue Jun 19 16:58:01 EDT 2018

import play.api.mvc.Call


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers {

  // @LINE:6
  class ReversePersonController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:11
    def update(id:Long, name:String, age:Int): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "update(" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)) + "," + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("name", name)) + "," + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("age", age)) + ")")
    }
  
    // @LINE:9
    def addNew(name:String, age:Int): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "addPerson(" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[String]].unbind("name", name)) + "," + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Int]].unbind("age", age)) + ")")
    }
  
    // @LINE:10
    def deletePerson(id:Long): Call = {
      
      Call("POST", _prefix + { _defaultPrefix } + "remove(" + play.core.routing.dynamicString(implicitly[play.api.mvc.PathBindable[Long]].unbind("id", id)) + ")")
    }
  
    // @LINE:8
    def getPersons(): Call = {
      
      Call("GET", _prefix + { _defaultPrefix } + "persons")
    }
  
    // @LINE:6
    def index(): Call = {
      
      Call("GET", _prefix)
    }
  
  }

  // @LINE:14
  class ReverseAssets(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:14
    def versioned(file:Asset): Call = {
      implicit lazy val _rrc = new play.core.routing.ReverseRouteContext(Map(("path", "/public"))); _rrc
      Call("GET", _prefix + { _defaultPrefix } + "assets/" + implicitly[play.api.mvc.PathBindable[Asset]].unbind("file", file))
    }
  
  }


}
