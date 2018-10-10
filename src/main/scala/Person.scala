class Person (var firstName:String,var SecondName:String){
    private  val HOME = System.getProperty("user.home")
  var age = 0

  override def toString = s"$firstName $SecondName is $age years old"
  def printHome {println(s"Home  = $HOME")}
  def printFullName{println(this)}
  printHome
  printFullName

}


