package Round2;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class
 * @author Sluva
 *
 */
public class TestCacheFunction {

	CacheFunction cf;
	double[][] matrixWithOneFirst;
	double[][] matrixWithTwo;
	double[][] matrixWithOneSecond;
	double[][] matrixWithThree;

	@Before
	public void setUp() throws Exception {
		// three elements in the cache
		cf = new CacheFunction(3);
		int length = 1000;
		// initialization matrix
		matrixWithOneFirst = new double[length][length];
		matrixWithTwo = new double[length][length];
		matrixWithOneSecond = new double[length][length];
		matrixWithThree = new double[length][length];
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				matrixWithOneFirst[i][j] = matrixWithOneSecond[i][j] = 1;
				matrixWithTwo[i][j] = 2;
				matrixWithThree[i][j] = 3;
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		cf = null;
		matrixWithOneFirst = null;
		matrixWithTwo = null;
		matrixWithOneSecond = null;
		matrixWithThree = null;
	}
	
	/**
	 * Time test
	 * matrix multiplication - long operation
	 * run time > 3000 - calculation function
	 * run time < 100 - read in cache
	 */
	@Test
	public void test() {

		// write in the cache (first cell of memory)
		long time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithOneFirst, matrixWithOneSecond);
		time = System.currentTimeMillis() - time;
		assertTrue(time > 3000);

		// read in the cache
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithOneFirst, matrixWithOneSecond);
		time = System.currentTimeMillis() - time;
		assertTrue(time < 100);

		// read in the cache
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithOneSecond, matrixWithOneFirst);
		time = System.currentTimeMillis() - time;
		assertTrue(time < 100);

		// write in the cache (second cell of memory)
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithTwo, matrixWithOneSecond);
		time = System.currentTimeMillis() - time;
		assertTrue(time > 3000);

		// read in the cache
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithTwo, matrixWithOneFirst);
		time = System.currentTimeMillis() - time;
		assertTrue(time < 100);

		// write in the cache (third cell of memory)
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithThree, matrixWithTwo);
		time = System.currentTimeMillis() - time;
		assertTrue(time > 3000);

		// write in the cache (deletes first entry - first cell of memory)
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithTwo, matrixWithThree);
		time = System.currentTimeMillis() - time;
		assertTrue(time > 3000);
		
		// identical to the first call, but the result is not in the cache (deletes second entry - second cell of memory)
		time = System.currentTimeMillis();
		cf.theSumOfElementsOfTheProductMatrix(matrixWithOneFirst, matrixWithOneSecond);
		time = System.currentTimeMillis() - time;
		assertTrue(time > 3000);
	}
}
