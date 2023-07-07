package example

import org.scalatest.funsuite.AnyFunSuite

class HelloSpec extends AnyFunSuite {



  test(
    """ "Hello"(6) should throw a "java.lang.StringIndexOutOfBoundsException" """
  ) {
    assertThrows[java.lang.StringIndexOutOfBoundsException]("Hello" (6))
  }

}
