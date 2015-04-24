package Round2;

import java.util.Arrays;

/**
 * Class with Memoize function
 * 
 * @author Sluva
 * 
 */
public class CacheFunction {

	// repository of keys (arguments to function)
	// key[x][0] - first argument, key[x][1] - second argument
	// x = capacity
	private Object[][] key;

	// repository of values (results of function)
	private double[] cache;

	// amount of stored values
	private final int capacity;

	// oldest element
	private volatile int oldestElement;

	/**
	 * Constructor
	 * 
	 * @param capacityOfCache
	 *            amount of stored values
	 */
	public CacheFunction(int capacityOfCache) {
		capacity = capacityOfCache;
		key = new Object[capacityOfCache][2];
		cache = new double[capacityOfCache];
		oldestElement = 0;
	}

	/**
	 * multiplies the matrix and sums all elements of the result
	 * 
	 * @param matrix1
	 *            first matrix for multiplies
	 * @param matrix2
	 *            second matrix for multiplies
	 * @return sums all elements of the result
	 */
	public double theSumOfElementsOfTheProductMatrix(double[][] matrix1,
			double[][] matrix2) {
		// search in the cache
		Double result = getCache(matrix1, matrix2);
		if (result != null)
			return result;
		// calculations of function
		result = 0.;
		for (int i = 0; i < matrix1.length; i++) {
			for (int j = 0; j < matrix2[0].length; j++) {
				for (int k = 0; k < matrix2.length; k++) {
					result += matrix1[i][j] * matrix2[j][k];
				}
			}
		}
		// add in cache
		addToCache(matrix1, matrix2, result);
		return result;
	}

	/**
	 * search in the cache
	 * 
	 * @param arg1
	 *            first arg of function
	 * @param arg2
	 *            second arg of function
	 * @return result or null (if the lookup fails)
	 */
	private synchronized Double getCache(double[][] arg1, double[][] arg2) {
		first: for (int i = 0; i < capacity; i++) {
			// if the null element -> the elements in the cache ended
			if (key[i][0] == null)
				return null;
			// checking for equality links
			if (arg1 == key[i][0] && arg1 == key[i][1])
				return cache[i];
			// checking for equality elements is matrix
			for (int j = 0; j < arg1.length; j++) {
				if (!Arrays.equals(arg1[j], ((double[][]) key[i][0])[j])
						|| !Arrays.equals(arg2[j], ((double[][]) key[i][1])[j]))
					continue first;
			}
			return cache[i];
		}
		return null;
	}

	/**
	 * add to cache
	 * @param arg1 first arg of the funtion
	 * @param arg2 second arg of the function
	 * @param res result of the function
	 */
	private synchronized void addToCache(double[][] arg1, double[][] arg2,
			double res) {
		// index of the oldest element only incremented
		// but oldestElement % capacity - changes it cyclically
		key[oldestElement % capacity][0] = arg1.clone();
		key[oldestElement % capacity][1] = arg2.clone();
		cache[oldestElement % capacity] = res;
		oldestElement++;
	}
}
