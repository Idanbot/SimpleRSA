//
// Writen by Idan Botbol
//   

package rsa;

import java.math.BigInteger;
import java.security.SecureRandom;

//	In case you want to create a list of possible keys
//import java.util.ArrayList;
//import java.util.List;

public class RSA {

	public static void main(String[] args) {

		// Generating primes with given bit_length (primeSize)
		int primeSize = 6;
		SecureRandom r1 = new SecureRandom();
		BigInteger bigP = BigInteger.probablePrime(primeSize, r1);

		// Making sure the prime numbers selected are different from each other
		BigInteger bigQ = BigInteger.probablePrime(primeSize, r1);
		while (bigQ.compareTo(bigP) == 0)
			bigQ = BigInteger.probablePrime(primeSize, r1);

		// Printing generated P and Q
		System.out.println("Prime P = " + bigP);
		System.out.println("Prime Q = " + bigQ);

		// Calculating products of primes
		BigInteger productN = bigP.multiply(bigQ);
		BigInteger phi = bigP.subtract(BigInteger.ONE).multiply(bigQ.subtract(BigInteger.ONE));

		// Generating an encryption key
		BigInteger eKey;
		do
			eKey = new BigInteger(phi.bitLength(), r1);
		while (eKey.compareTo(BigInteger.ONE) <= 0 || eKey.compareTo(phi) >= 0 || !eKey.gcd(phi).equals(BigInteger.ONE));
		System.out.println("Encryption key = " + eKey);

		// Generating a decryption key
		BigInteger dKey = eKey.modInverse(phi);
		System.out.println("Decryption key = " + dKey);

		// Message encryption
		BigInteger message = BigInteger.valueOf(1337);
		BigInteger msgEn = message.modPow(eKey, productN);
		System.out.println("Message: " + message);
		System.out.println("Encrypted Message => " + msgEn);

		// Message decryption
		BigInteger msgDe = msgEn.modPow(dKey, productN);
		System.out.println("Decryped Messaged => " + msgDe);

	}

}
