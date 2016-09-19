package com.github.aag.iban

class Validator {
  def validate(countryCode: String, checkSum: String, bban: String): Boolean = {
    if (countryCode.toUpperCase() != "DE") {
      throw new IllegalArgumentException("Only Germany is currently supported")
    }

    val bbanClean = bban.replaceAll(" ", "")

    // German BBANs must be 18 digits long
    if (bbanClean.length != 18) {
      return false
    }

    // The checksum must always be 2 digits long
    if (checkSum.length != 2) {
      return false
    }

    // Hard code the country code for Germany
    // First we take the letters DE, convert them to their position in the
    // alphabet (45), then add 9 to each digit (1314). We then left shift
    // by two digits so we can just add the checksum to it.
    val countryInt = 131400

    val bbanInt = BigInt(bbanClean) * 1000000
    val checkNum = bbanInt + countryInt + checkSum.toInt

    (checkNum % 97)  == 1
  }
}
