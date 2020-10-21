package info.dhhovniy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.Instant;

public class Main {
    // author Orel Eraki
    // Fibonacci algorithm
    // O(log2 n)
    public static BigInteger fibMatrixMultiplication(int n) {

        int num = Math.abs(n);
        if (num == 0) {
            return BigInteger.ZERO;
        }
        else if (num <= 2) {
            return BigInteger.ONE;
        }

        BigInteger[][] number = { { BigInteger.ONE, BigInteger.ONE }, { BigInteger.ONE, BigInteger.ZERO } };
        BigInteger[][] result = { { BigInteger.ONE, BigInteger.ONE }, { BigInteger.ONE, BigInteger.ZERO } };

        while (num > 0) {
            if (num%2 == 1) result = MultiplyMatrix(result, number);
            number = MultiplyMatrix(number, number);
            num/= 2;
        }

        return result[1][1].multiply(BigInteger.valueOf(((n < 0) ? -1:1)));
    }

    private static BigInteger[][] MultiplyMatrix(BigInteger[][] mat1, BigInteger[][] mat2) {
        return new BigInteger[][] {
                {
                        mat1[0][0].multiply(mat2[0][0]).add(mat1[0][1].multiply(mat2[1][0])),
                        mat1[0][0].multiply(mat2[0][1]).add(mat1[0][1].multiply(mat2[1][1]))
                },
                {
                        mat1[1][0].multiply(mat2[0][0]).add(mat1[1][1].multiply(mat2[1][0])),
                        mat1[1][0].multiply(mat2[0][1]).add(mat1[1][1].multiply(mat2[1][1]))
                }
        };
    }

    public static BigInteger fibFormula(int n) {
        BigDecimal Phi  = BigDecimal.valueOf((1 + Math.sqrt(5)) / 2);
        BigDecimal mPhi = BigDecimal.valueOf((1 - Math.sqrt(5)) / 2);
        return Phi.pow(n).subtract(mPhi.pow(n)).divideToIntegralValue(BigDecimal.valueOf(Math.sqrt(5))).toBigInteger();
    }

    static BigInteger fibOldSchool(int n)
    {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        BigInteger c;
        if (n == 0)
            return a;
        for (int i = 2; i <= n; i++)
        {
            c = a.add(b);
            a = b;
            b = c;
        }
        return b;
    }

    public static void main(String[] args) {

        int n = 2000000;

        // works fine just for N < 70 and the worst by performance than any other because of BigDecimal.pow usage
        Instant start = Instant.now();
        String res = fibFormula(n).toString();
        Duration duration = Duration.between(start, Instant.now());
        System.out.println("Fibonacci Formula with O(1) time complexity                    of " + n + "th term is " + res
                + " with number of digits " + res.length() + " and time in nanos:" + duration.getNano());

        // faster then next with N > 500 approx - depends on environment
        start = Instant.now();
        res = fibMatrixMultiplication(n).toString();
        duration = Duration.between(start, Instant.now());
        System.out.println("Fibonacci Matrix Multiplication with O(log2 N) time complexity of " + n + "th term is " + res
                + " with number of digits " + res.length() + " and time in nanos:" + duration.getNano());

        // faster then previous with N < 500 approx - depends on environment
        start = Instant.now();
        res = fibOldSchool(n).toString();
        duration = Duration.between(start, Instant.now());
        System.out.println("Fibonacci Dynamic Optimized with O(N) time complexity          of " + n + "th term is " + res
                + " with number of digits " + res.length() + " and time in nanos:" + duration.getNano());
    }
}