package co7217.week17.entity.tests

import org.junit.jupiter.api.Test
import co7217.week17.entity.entityDSL.Domainmodel
import com.google.inject.Inject
import org.eclipse.xtext.testing.InjectWith
import org.eclipse.xtext.testing.extensions.InjectionExtension
import org.eclipse.xtext.testing.util.ParseHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InjectionExtension)
@InjectWith(EntityDSLInjectorProvider)
class CO7217_EntityDSLParsingTest {

	@Inject
	ParseHelper<Domainmodel> parseHelper
	
	@Test
	void testPackge() {
		def result = parseHelper.parse("""
package a { }
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}

	@Test
	void testDataType() {
		def result = parseHelper.parse("""
datatype String
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}

	
	@Test
	void testInheritance() {
		def result = parseHelper.parse("""
entity Customer {
  fullName: String
}
entity Vehicle {
  owner: Customer
}
entity Car extends Vehicle {
  drivingPlate: String
}
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}
	
	
	@Test
	void testEntities() {
		def result = parseHelper.parse("""
entity Customer {
  fullName: String
  many vehicles: Vehicle
}
entity Vehicle {
  owner: Customer
}
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}

	@Test
	void testModifierForFeatures() {
		def result = parseHelper.parse("""
entity Customer {
  public fullName: String
  private many vehicles: Vehicle
}
entity Vehicle {
  protected owner: Customer
}
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}

	@Test
	void testTutorialExample() {
		def result = parseHelper.parse("""
datatype String

entity Blog {
   title: String
   many posts: Post
}

entity HasAuthor {
   author: String
}

entity Post extends HasAuthor {
   title: String
   content: String
   many comments: Comment
}

entity Comment extends HasAuthor {
   content: String
}
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}
	
	
	/** 
	 * 
	 * The tests below require some modelling of your own
	 * 
	 */
	
	
	
	@Test
	void testLiteralEnumeration1() {
		def result = parseHelper.parse("""
enumeration Colour {
  red
  green
  blue
}
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}

	@Test
	void testLiteralEnumeration2() {
		def result = parseHelper.parse("""
enumeration Status {
  rented
  purchased
  sold
}
entity Car {
  status: Status
}
		""")
		Assertions.assertNotNull(result)
		def errors = result.eResource().errors
		Assertions.assertTrue(errors.isEmpty(), """Unexpected errors: ${errors.join(", ")}""")
	}

}




