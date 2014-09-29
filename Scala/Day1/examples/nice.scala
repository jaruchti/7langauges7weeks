// Creation of traits in Scala.  These are similar to modules in Ruby - essentially a partial-class implementation.
class Person(val name:String)

trait Nice {
  def greet() = println("Howdily doodily.")
}

class Character(override val name:String) extends Person(name) with Nice

val flanders = new Character("Ned")
flanders.greet
