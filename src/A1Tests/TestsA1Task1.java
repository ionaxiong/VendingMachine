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


//Write one jUnit test for each public method (except main()) 
//and report code coverage of the tests per method

public class TestsA1Task1 {
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
	public void testGetColaCount() {
		assertEquals(5, vm.getColaCount());
	}

	@Test
	public void testGetCoffeeCount() {
		assertEquals(5, vm.getCoffeeCount());
	}

	@Test
	public void testGetFantaCount() {
		assertEquals(5, vm.getFantaCount());
	}

	@Test
	public void testDisplayMenu() {
		vm.DisplayMenu();
						
		output = systemOutRule.getLog();
		
		assertTrue(output.contains("COLA		price: [2.5] euro	still have: [5]can"));
		assertTrue(output.contains("COFFEE		price: [3.0] euro	still have: [5]can"));
		assertTrue(output.contains("FANTA		price: [5.0] euro	still have: [5]can"));
		assertFalse(output.contains("FANTA		price: [5.0] euro	still have: [4]can"));
	}


	@Test
	public void testDisplayReturningCoins() {
		vm.displayReturningCoins(1.5);
		int[] coins = new int[] {0, 1, 1, 0};
		output = systemOutRule.getLog();
		assertTrue(output.contains("Your Change is " + NLC
				+ "	" + coins[0] + " x 2Euro" + NLC 
				+ "	" + coins[1] + " x 1Euro" + NLC
				+ "	" + coins[2] + " x 50Cent" + NLC
				+ "	" + coins[3] + " x 20Cent"));
	}

	@Test
	public void testCalculateChange() {
		vm.calculateChange(2.5, "FD");
		output = systemOutRule.getLog();
		assertTrue(output.contains("Wrong coin type!"));
	}

	@Test
	public void testCaptureMoney() {
		systemMock.provideLines("CANCEL");
		vm.captureMoney("COFFEE", 3.0);
		output = systemOutRule.getLog();
		assertTrue(output.contains("Order cancelled, return coins"));
	}

	@Test
	public void testCaptureInputAndRespond() {
		exit.expectSystemExit();
		exit.checkAssertionAfterwards(new Assertion() {
			
			 @Override
			 public void checkAssertion() throws Exception {
				 output = systemOutRule.getLog();
				 assertTrue(output.contains("COLA		price: [2.5] euro	still have: [4]can"));
				 assertFalse(output.contains("COFFEE		price: [3.0] euro	still have: [4]can"));
			 }
			
		});
		
		systemMock.provideLines("COLA", "FC", "TE", "QUIT");

		vm.captureInputAndRespond();
	}

	@Test
	public void failedTest_ProcessSelection() {
		systemMock.provideLines("FC", "TE");
		vm.processSelection("COLA");
		output = systemOutRule.getLog();
		assertTrue(output.contains("The price is 2.50 Euro"));
		assertTrue(output.contains("please insert a coin"));
		
		systemMock.provideLines("TE", "TE");
		vm.processSelection("FANTA");
		output = systemOutRule.getLog();
		assertTrue(output.contains("The price is 5.00 Euro"));
	}
	
	@Test
	public void failedTest_CalculateReturningCoins() {
		int[] coinList = new int[4];
		coinList = new int[] { 1, 0, 0, 0 };
		assertArrayEquals(coinList, vm.calculateReturningCoins(2.0));
		
		coinList = new int[] { 50, 0, 0, 0 };
		assertArrayEquals(coinList, vm.calculateReturningCoins(100.0));
		
		coinList = new int[] { 1, 1, 0, 0 };
		assertArrayEquals(coinList, vm.calculateReturningCoins(3.0));
		
		coinList = new int[] { 1, 1, 1, 1 };
		assertArrayEquals(coinList, vm.calculateReturningCoins(3.7));
		
		coinList = new int[] { 0, 0, 0, 0 };
		assertArrayEquals(coinList, vm.calculateReturningCoins(-50));
		
//		coinList = new int[] { 1, 1, 1, 2 };
//		assertArrayEquals(coinList, vm.calculateReturningCoins(3.9));
	}

}
