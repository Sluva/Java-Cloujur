
/**
 * 
 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 - it is Fibonacci sequence
 * O, O, E, O, O, E,  O,  O,  E,  O,  O - it is Fibonacci sequence, where even numbers - E,
 * and odd numbers - O. You can see that every third number - even number, 
 * for this reason, parity check of all numbers is unnecessary
 * 
 * @author Sluva
 *
 */ 
public class Fibonacci {
	public static void main (String[] args) {
		// the first three values
		int first = 1; int second = 1; int third = 2;
		// the limit calculations
		int limit = 4_000_000;
		// the initial value of the sum
		int sum = 0;
		// summation until the value is less than limit
		while (third <= limit) {
			// addition of even value to the result
			sum += third;
			// calculation of the first odd value
			first = second + third;
			// calculation of the second odd value
			second = third + first;
			// calculation of the even value
			third = first + second;
		}
		// output sum
		System.out.println(sum);
	}
}
