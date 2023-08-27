import java.math.BigInteger;
import java.io.*;

public class FindPrimeSingleThread {
    
    public static boolean isPrime(BigInteger start, BigInteger end) {
        for (BigInteger n = start; (n.compareTo(end)<= 0); n = n.add(BigInteger.ONE)) {
            BigInteger sqrt_n = n.sqrt();
            for (BigInteger i = BigInteger.TWO;  (sqrt_n.compareTo(i) >= 1); i = i.add(BigInteger.ONE)) {
                if (n.mod(i).equals(0)) {
                    return false;
                }            
            }
        }   
        return true;
    }
        
    public static void main(String[] args) {
        String fileName = "RunTimes.txt";

        String input = "2135729";
        BigInteger two = BigInteger.TWO;
        BigInteger n = new BigInteger(input);

        try{

            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter buffer = new BufferedWriter(fw);

            boolean result = false;
        
            for (int i = 0; i < 5; i++) {
                long startTime = System.nanoTime();
            
                result = isPrime(two, n);
            
                if (result) {
                    System.out.println(n + " is a prime number");
                } else {
                   System.out.println(n + " is not a prime number");
                }

                float timeTaken = ((System.nanoTime() - startTime));
                double timeInSeconds = timeTaken/1000000000;
                String timeString =  String.format("Time: %.2f seconds, Run: %d with 1 Processor and 1 Thread\n", timeInSeconds, i);
                System.out.printf("Time: %.2f seconds\n", timeInSeconds);
                buffer.write(timeString);
            }
            buffer.close();
        }catch (IOException e) {
            System.out.println("An error occurred while trying to append the content to the file: " + e.getMessage());
        }
        
    }
}

