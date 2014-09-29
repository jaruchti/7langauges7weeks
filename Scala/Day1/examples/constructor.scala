// Demonstration of an auxiliary constructor in Scala
class Person(first_name: String) {
  println("Outer constructor")
  def this(first_name : String, last_name : String) {
    this(first_name)
    println("Inner constructor")
  }
  def talk() = println("Hi")
}

var bob = new Person("Bob")
var bobTate = new Person("Bob", "Tate")
