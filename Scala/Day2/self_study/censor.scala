import scala.collection.mutable.HashMap

// Definition of a censor trait.  
// Classes utilizing this trait may load 
// a censorship map from a file defining
// words to censor and their replacement.
// By calling the censor(...) method on a 
// string of text, the action of replacing
// censored words is performed. 
trait Censor{
  val curses = new HashMap[String, String]

  // Populates the mapping of censored words to their suitable
  // replacements from the file given as a parameter.
  // The file should define the mapping in the following format:
  // word1 replacement1
  // word2 replacement2
  def load( file : String) = {
    io.Source.fromFile( file ).getLines().foreach { (line) =>
      if ( line.length > 0 ){
        val censorPair = line.split("\\s+");

        // Java allows us to match patterns case insensitively via the (?i) clause
        // in a regular expression.  However, we cannot easily replace with the correct
        // case. Therefore, we populate the curses map with lowercase, capitalized, and
        // uppercase key->value pairs for matching and replacing censored words. 
        val key = censorPair(0)
        val lowercaseKey   = key.toLowerCase
        val capitalizedKey = key(0).toUpper + key.substring(1).toLowerCase
        val uppercaseKey   = key.toUpperCase

        val replacement = censorPair(1)
        val lowercaseReplacement = replacement.toLowerCase
        val capitalizedReplacement = replacement(0).toUpper + replacement.substring(1).toLowerCase
        val uppercaseReplacement = replacement.toUpperCase
        
        curses += lowercaseKey   -> lowercaseReplacement
        curses += capitalizedKey -> capitalizedReplacement 
        curses += uppercaseKey   -> uppercaseReplacement 
      }
    }
  }

  // Find all instances of the censored words in the text and make the suitable
  // replacement.
  def censor( text : String) = {
    curses.foldLeft(text) {(censoredText, cursesPair) =>
      // Iterate through each pair in the curses map and 
      // gradually build the censoredText via replaceAll.
      censoredText.replaceAll(
        ("\\b" + cursesPair._1 + "\\b"), 
        cursesPair._2)
    }
  }
}

// Some manual testing. Automatted testing is beyond the scope of this exercise.
class CensorTest(val text : String) extends Censor {
  load("censor_words.txt")

  def getOriginalText() = text
  def getCensoredText() = censor( text )
}

val testText = new CensorTest( "Gosh DARN it, I stubbed by darn toe again!" )

println("Original: " + testText.getOriginalText); 
println("Censored: " + testText.getCensoredText);
