// @GENERATOR:play-routes-compiler
// @SOURCE:C:/Users/vsingh/Desktop/Vishram/Scala/PlaySlick/conf/routes
// @DATE:Tue Jun 19 16:58:01 EDT 2018


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
