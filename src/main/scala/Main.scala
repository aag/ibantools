import resource._

import scala.collection.mutable

object Main extends App {
  val ibanBicMap = new mutable.HashMap[String, String]

  for (source <- managed(scala.io.Source.fromFile("/tmp/ibans.txt", "ISO-8859-1"))) {
    for (line <- source.getLines) {
      val routingNumber = line.substring(0, 7)
      val bankName = line.substring(9, 66).trim()
      val bic = line.substring(139, 149).trim()

      if (bic.length > 0) {
        //println(s"name: $bankName, routing number: $routingNumber, bic: $bic")
        ibanBicMap += (routingNumber -> bic)
      }
    }
  }

  println(ibanBicMap)
}
