import com.github.aag.iban.Validator
import org.scalatest._

class VerifierSpec extends FlatSpec with Matchers {

  "A Validator" should "throw an exception for a non-German country" in {
    val verifier = new Validator
    a [java.lang.IllegalArgumentException] should be thrownBy {
      verifier.validate("FR", "00", "123456789012345678")
    }
  }

  it should "recognize a too-short IBAN as invalid" in {
    val verifier = new Validator
    verifier.validate("DE", "74", "21050171001234567") should be (false)
  }

  it should "recognize a too-long IBAN as invalid" in {
    val verifier = new Validator
    verifier.validate("DE", "38", "2105017000123456781") should be (false)
  }

  it should "recognize a too-short checksum as invalid" in {
    val verifier = new Validator
    verifier.validate("DE", "6", "210501703012345625") should be (false)
  }

  it should "recognize a too-long checksum as invalid" in {
    val verifier = new Validator
    verifier.validate("DE", "006", "210501703012345625") should be (false)
  }

  it should "recognize a valid IBAN as valid" in {
    val verifier = new Validator
    verifier.validate("DE", "68", "210501700012345678") should be (true)
  }

  it should "recognize a valid IBAN as valid when formatted with spaces" in {
    val verifier = new Validator
    verifier.validate("DE", "68", "2105 0170 0012 3456 78") should be (true)
  }

  it should "recognize a valid IBAN with a bad checksum as invalid" in {
    val verifier = new Validator
    verifier.validate("DE", "69", "210501700012345678") should be (false)
  }

}