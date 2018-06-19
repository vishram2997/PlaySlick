// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/vsingh/Desktop/Vishram/Scala/play-scala-slick-example/conf/routes
// @DATE:Tue Jun 19 02:24:58 EDT 2018


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
