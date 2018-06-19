// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/vsingh/Desktop/Vishram/Scala/play-scala-slick-example/conf/routes
// @DATE:Tue Jun 19 02:24:58 EDT 2018

import play.api.routing.JavaScriptReverseRoute


import _root_.controllers.Assets.Asset

// @LINE:6
package controllers.javascript {

  // @LINE:6
  class ReversePersonController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:8
    def getPersons: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.getPersons",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "persons"})
        }
      """
    )
  
    // @LINE:9
    def addNew: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.addNew",
      """
        function(name0,age1) {
          return _wA({method:"POST", url:"""" + _prefix + { _defaultPrefix } + """" + "addPerson(" + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[String]].javascriptUnbind + """)("name", name0)) + "," + encodeURIComponent((""" + implicitly[play.api.mvc.PathBindable[Int]].javascriptUnbind + """)("age", age1)) + ")"})
        }
      """
    )
  
    // @LINE:6
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.PersonController.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }

  // @LINE:12
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:12
    def versioned: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.versioned",
      """
        function(file1) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[play.api.mvc.PathBindable[Asset]].javascriptUnbind + """)("file", file1)})
        }
      """
    )
  
  }


}
