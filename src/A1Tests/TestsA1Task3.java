package A1Tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import VendingMachine.VendingMachine;

//Write jUnit tests for public double calculateChange(double price, String insertedCoins) to achieve
//100% code coverage of the method
//100% mutation score (all mutants of the method killed)

public class TestsA1Task3 {

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	VendingMachine vm;
	String output;

	@Before
	public void setUp() throws Exception { 
		vm = new VendingMachine();
	}

	@Test
	public void testCalculateChange() {
		output = systemOutRule.getLog();
		assertTrue(output.contains("Vending machine is up and running!"));
		assertEquals(5, vm.getColaCount());
		assertEquals(5, vm.getCoffeeCount());
		assertEquals(5, vm.getFantaCount());
		
		double change = vm.calculateChange(3.0, "ID");
		output = systemOutRule.getLog();
		assertTrue(output.contains("Wrong coin type!"));
		assertEquals(change, -3.0, 2); 
				
		change = vm.calculateChange(3.0, "OE"); 
		output = systemOutRule.getLog();
		assertTrue(output.contains("You have paid 1.0 Euro"));
		assertEquals(change, -2.0, 2); 

		change = vm.calculateChange(3.0, "TC");
		output = systemOutRule.getLog();
		assertTrue(output.contains("You have paid 1.2 Euro"));
		assertEquals(change, -1.8, 2); 
		
		change = vm.calculateChange(3.0, "FC");
		output = systemOutRule.getLog();
		assertTrue(output.contains("You have paid 1.7 Euro"));
		assertEquals(change, -1.3, 2); 

		change = vm.calculateChange(3.0, "TE");
		output = systemOutRule.getLog();
		assertTrue(output.contains("You have paid 3.7 Euro"));
		assertEquals(change, 0.7, 2); 
		
		change = vm.calculateChange(0.0, "TC FC OE XX");
		output = systemOutRule.getLog();
		assertTrue(output.contains("Wrong coin type!"));
		assertTrue(output.contains("You have paid 5.4 Euro"));
		assertEquals(change, 5.4, 2); 
		
	}
}
