// Creation of a for loop in Scala

def forLoop {
  println( "for loop using Java-style iteration" )
  for (i <- 0 until args.length) {
    println(args(i))
  }
}

forLoop
