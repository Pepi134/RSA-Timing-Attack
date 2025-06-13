import java.math.BigInteger;
import java.security.SecureRandom;

public class TimingAttackRSA {
    private static final int NUM_SAMPLES = 1000; // Бројот на примероци за земање време

    public static void main(String[] args) {
        // Генерирање на RSA клучеви
        BigInteger n = generateLargePrime();
        BigInteger e = new BigInteger("65537");
        BigInteger d = e.modInverse(n);

        // Временско мерење на криптоанализата
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_SAMPLES; i++) {
            // Генерирање на порака и криптирање
            BigInteger message = generateRandomMessage(n);
            BigInteger ciphertext = encrypt(message, e, n);

            // Криптоанализа со мерење на времето
            timingAttack(ciphertext, d, n);
        }
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        System.out.println("Средно време за криптоанализа: " + totalTime / NUM_SAMPLES + " наносекунди");
    }

    // Генерирање на големо просто бројче
    private static BigInteger generateLargePrime() {
        return BigInteger.probablePrime(1024, new SecureRandom());
    }

    // Генерирање на случајна порака помала од n
    private static BigInteger generateRandomMessage(BigInteger n) {
        SecureRandom random = new SecureRandom();
        BigInteger message;
        do {
            message = new BigInteger(n.bitLength(), random);
        } while (message.compareTo(n) >= 0);
        return message;
    }

    // Криптирање на пораката со RSA
    private static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    // Криптоанализа со мерење на времето
    private static void timingAttack(BigInteger ciphertext, BigInteger d, BigInteger n) {
        long startTime = System.nanoTime();
        ciphertext.modPow(d, n);
        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;


    }
}
