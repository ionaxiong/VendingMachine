package A1Tests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.rules.Timeout;

import VendingMachine.VendingMachine;

//write a jUnit test case that tests the following scenario:  
//	“the user tries to buy 6 COLA (only 5 available) and a COFFEE. 
//	For every drink a different combination of coins is used to pay”

public class TestsA1Task2 {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Rule
	public final TextFromStandardInputStream systemMock = TextFromStandardInputStream.emptyStandardInputStream();

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@ClassRule
	public static final Timeout timeout = Timeout.millis(1000);

	VendingMachine vm;

	String output;

	private final String NLC = System.getProperty("line.separator");

	@Before
	public void setUp() throws Exception {
		vm = new VendingMachine();
	}

	@Test
	public void testProcessSelection() {
		assertEquals(5, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());

		// Order first COLA, COLA quantity == 4
		systemMock.provideLines("TE", "OE");
		vm.processSelection("COLA");
		output = systemOutRule.getLog();

		assertEquals(4, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());

		assertTrue(output.contains("Your change is: 0.5 EURO"));
		assertTrue(output.contains("Your Change is " + NLC
				+ "	0 x 2Euro" + NLC
				+ "	0 x 1Euro" + NLC
				+ "	1 x 50Cent" + NLC
				+ "	0 x 20Cent"));
		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));

		// Order second COLA, COLA quantity == 3
		systemMock.provideLines("TE", "TE");
		vm.processSelection("COLA");
		output = systemOutRule.getLog();

		assertEquals(3, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());

		assertTrue(output.contains("Your change is: 1.5 EURO"));
		assertTrue(output.contains("Your Change is " + NLC
				+ "	0 x 2Euro" + NLC
				+ "	1 x 1Euro" + NLC
				+ "	1 x 50Cent" + NLC
				+ "	0 x 20Cent"));
		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));
		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));

		// Order second COLA, COLA quantity == 2
		systemMock.provideLines("FC", "FC", "FC", "FC", "FC");
		vm.processSelection("COLA");
		output = systemOutRule.getLog();

		assertEquals(2, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());

		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));

		// Order second COLA, COLA quantity == 1
//		systemMock.provideLines("TC", "TC", "TC", "TC", "TC", "TC", "TC", "TE");
//		vm.processSelection("COLA");
//		output = systemOutRule.getLog();
//
//		assertEquals(1, vm.getColaCount());
//		assertEquals(5, vm.getCoffeeCount());
//		assertEquals(5, vm.getFantaCount());
//
//		assertTrue(output.contains("Your change is: 0.9 EURO"));
//		assertTrue(output.contains("Your Change is " + NLC
//				+ "	0 x 2Euro" + NLC
//				+ "	0 x 1Euro" + NLC
//				+ "	1 x 50Cent" + NLC
//				+ "	2 x 20Cent"));
//		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));

		// Order second COLA, COLA quantity == 1
		systemMock.provideLines("OE", "OE", "OE");
		vm.processSelection("COLA");
		output = systemOutRule.getLog();

		assertEquals(1, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());

		assertTrue(output.contains("Your change is: 0.5 EURO"));
		assertTrue(output.contains("Your Change is " + NLC
				+ "	0 x 2Euro" + NLC
				+ "	0 x 1Euro" + NLC
				+ "	1 x 50Cent" + NLC
				+ "	0 x 20Cent"));
		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));
		
		// Order second COLA, COLA quantity == 0
		systemMock.provideLines("FC", "FC", "OE", "FC");
		vm.processSelection("COLA");
		output = systemOutRule.getLog();

		assertEquals(0, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());

		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));
		
		// COLA stock ran out
		vm.processSelection("COLA");
		output = systemOutRule.getLog();

		assertEquals(0, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());
		
		assertTrue(output.contains("We ran out of COLA. Please order a different drink"));
		
//		//Order COFFEE
//		systemMock.provideLines("FC", "TE", "OE");
//		vm.processSelection("COFFEE");
//		output = systemOutRule.getLog();
//		
//		assertEquals(0, vm.getColaCount());
//		assertEquals(4, vm.getCoffeeCount());
//		assertEquals(5, vm.getFantaCount());
//		
//		assertTrue(output.contains("Your change is: 0.5 EURO"));
//		assertTrue(output.contains("Your Change is " + NLC
//				+ "	0 x 2Euro" + NLC
//				+ "	0 x 1Euro" + NLC
//				+ "	1 x 50Cent" + NLC
//				+ "	0 x 20Cent"));
//		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));
		
		//Order COFFEE
		
		systemMock.provideLines("FC", "TE", "OE");
		vm.processSelection("COFFEE");
		output = systemOutRule.getLog();

		assertEquals(0, vm.getColaCount());
		assertEquals(4, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());
		
		assertTrue(output.contains("Your change is: 0.5 EURO"));
		assertTrue(output.contains("Your Change is " + NLC
				+ "	0 x 2Euro" + NLC
				+ "	0 x 1Euro" + NLC
				+ "	1 x 50Cent" + NLC
				+ "	0 x 20Cent"));
		assertTrue(output.contains("DRINK DELIVERED, Thank you for your business, see you again!"));
		
	}

}
