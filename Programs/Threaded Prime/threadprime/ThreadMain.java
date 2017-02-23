package threadprime;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class ThreadMain {

    /**
     * @param args the command line arguments
     */
    private long startTime;
    private ArrayList<Long> threads;
    
    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());
    
    
    public ThreadMain() {
        // TODO code application logic here
        threads = new ArrayList<Long>();
        BigDecimal num = new BigDecimal("18446744082299486207");
        //BigDecimal num = new BigDecimal("8677821294721");
        double numThreads = 1000;
        BigDecimal ceiling = bigSqrt(num).setScale(0, BigDecimal.ROUND_CEILING);
        BigDecimal from = new BigDecimal("2");
        BigDecimal range = ceiling.divide(new BigDecimal(numThreads + ""));
        range = range.setScale(0, BigDecimal.ROUND_CEILING);
        System.out.println("ceiling: " + ceiling);
        System.out.println("from: " + from);
        System.out.println("range: " + range);
        BigDecimal to = range;
        startTime = System.currentTimeMillis();
        for(int i = 1; i <= numThreads; i++)
        {
            ThreadedPrime threadedPrime = new ThreadedPrime(from, to, num,this);
            threads.add(threadedPrime.getId());
            threadedPrime.start();
            System.out.println(from + " " + to);
            from = to.add(new BigDecimal("1"));
            to = range.multiply(new BigDecimal((i + 1) + ""));
            
        }
    }
    
    public void threadHasFinished(long id, boolean isPrime)
    {
        System.out.println(id + " A Thread has finished running!");
        threads.remove((long) id);
        if(threads.isEmpty())
        {
            System.out.println("All threads have finished");
            System.out.println("Time it took:" + (System.currentTimeMillis() - startTime));
        }
        
        if(!isPrime)
        {
            System.out.println("Number is not prime");
        }
    }
    
    private static BigDecimal sqrtNewtonRaphson  (BigDecimal c, BigDecimal xn, BigDecimal precision){
    BigDecimal fx = xn.pow(2).add(c.negate());
    BigDecimal fpx = xn.multiply(new BigDecimal(2));
    BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(),RoundingMode.HALF_DOWN);
    xn1 = xn.add(xn1.negate());
    BigDecimal currentSquare = xn1.pow(2);
    BigDecimal currentPrecision = currentSquare.subtract(c);
    currentPrecision = currentPrecision.abs();
    if (currentPrecision.compareTo(precision) <= -1){
        return xn1;
    }
    return sqrtNewtonRaphson(c, xn1, precision);   
}
    
    public static BigDecimal bigSqrt(BigDecimal c){
    return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE));
}
    
}
