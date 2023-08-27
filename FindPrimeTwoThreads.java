import java.math.BigInteger;
import java.io.*;

public class FindPrimeTwoThreads {
    
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
    
    static class ThreadingData {
        BigInteger start;
        BigInteger end;
        boolean result;
        
        ThreadingData(BigInteger start, BigInteger end) {
            this.start = start;
            this.end = end;
        }
    }
    
    static class FindPrimes implements Runnable {
        ThreadingData data;
        FindPrimes(ThreadingData data) {
            this.data = data;
        }
        
        @Override
        public void run() {
            data.result = isPrime(data.start, data.end);
        }
    }
    
    public static void main(String[] args) {
        String fileName = "RunTimes.txt";

        String input = "2135729";
        BigInteger two = BigInteger.TWO;
        BigInteger three = BigInteger.TWO.add(BigInteger.ONE);
        BigInteger n = new BigInteger(input);
        BigInteger midpoint = n.divide(two);



        boolean result1 = false;
        boolean result2 = false;
        

        try{

            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter buffer = new BufferedWriter(fw);
        
            for (int i = 0; i < 5; i++) {
                long startTime = System.nanoTime();
            
                ThreadingData data1 = new ThreadingData(two, midpoint);
                ThreadingData data2 = new ThreadingData(midpoint, n);
                Thread t1 = new Thread(new FindPrimes(data1));
                Thread t2 = new Thread(new FindPrimes(data2));
                t1.start();
                t2.start();
            
                try {
                    t1.join();
                    t2.join();
                    result1 = data1.result;
                    result2 = data2.result;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (result1 && result2) {
                    System.out.println(n + " is a prime number");
                } else {
                    System.out.println(n + " is not a prime number");
                }
            
                float timeTaken = ((System.nanoTime() - startTime));
                double timeInSeconds = timeTaken/1000000000;
                String timeString =  String.format("Time: %.2f seconds, Run: %d with 1 Processor and 2 Threads\n", timeInSeconds, i);
                System.out.printf("Time: %.2f seconds\n", timeInSeconds);
                buffer.write(timeString);
            }
            buffer.close();
        }catch (IOException e) {
            System.out.println("An error occurred while trying to append the content to the file: " + e.getMessage());
        }
    }
}

