// Computes the total size of a List of Strings using foldLeft 
def totalSize(lst : List[String]) = {
  lst.foldLeft(0) {(accum, str) => accum + str.length}
}

println( totalSize(List()) )                      // => 0
println( totalSize(List("one")) )                 // => 3
println( totalSize(List("one", "two", "three")) ) // => 11 
