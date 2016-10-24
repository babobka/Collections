package ru.kpfu.util;

public interface MathUtil {

	public static boolean isPrime(int n) {
		if (n != 2 && n % 2 == 0) {
			return false;
		}
		int sqrt = (int) Math.sqrt(n);
		for (int i = 3; i <= sqrt; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	public static int getNextPrime(int n) {
		int localN = n;
		if (localN % 2 == 0) {
			localN++;
		} else {
			localN += 2;
		}

		while (!isPrime(localN)) {
			localN += 2;
		}
		return localN;
	}

	public static void main(String[] args) {
		for (int i = 2; i < 100; i++) {
			System.out.println(i + " " + isPrime(i));
		}
	}

}
