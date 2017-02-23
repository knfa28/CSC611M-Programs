/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author CHOY
 */
public class ThreadMain {

    /**
     * @param args the command line arguments
     */
    private long startTime;
    private ArrayList<Long> threads;
    private double finishedThreads = 0; 
    private double numThreads;
    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());
    
    
    public ThreadMain(String number, String start, String end) {
        // TODO code application logic here
        threads = new ArrayList<Long>();
        //BigDecimal num = new BigDecimal("1000000000000037"); //16digit
        //BigDecimal num = new BigDecimal("999998727899999");  //15digit
      //  BigDecimal num = new BigDecimal("10000000002065383");  //17digit
        BigDecimal num = new BigDecimal(number);  //10digit
        //BigDecimal num = new BigDecimal("48112959837082048697"); //20 digit
        double numThreads = 1;
  
        //BigDecimal ceiling = bigSqrt(num).setScale(0, BigDecimal.ROUND_CEILING);
        BigDecimal from = new BigDecimal(start);
        //BigDecimal range = ceiling.divide(new BigDecimal(end));
        //range = range.setScale(0, BigDecimal.ROUND_CEILING);
        //System.out.println("ceiling: " + ceiling);
        //System.out.println("from: " + from);
        //System.out.println("range: " + range);
        BigDecimal to = new BigDecimal(end);
        startTime = System.nanoTime();
        for(int i = 1; i <= numThreads; i++)
        {
           ThreadedPrime threadedPrime = new ThreadedPrime(from, to, num,this);
            threads.add(threadedPrime.getId());
            threadedPrime.start();
            //System.out.println(from + " " + to);
            //from = to.add(new BigDecimal("1"));
            //to = range.multiply(new BigDecimal((i + 1) + ""));
            
            
            
        }
    }
    
    public void threadHasFinished(long id, boolean isPrime)
    {
       // System.out.println(id + " A Thread has finished running!");
        threads.remove((long) id);
        finishedThreads++;
        System.out.println("finished Threads: "+ finishedThreads);
        if(/*threads.isEmpty()&&(finishedThreads==numThreads)*/ finishedThreads> (1020))
        {
            System.out.println("All threads have finished");
            System.out.println("Time it took:" + ((System.nanoTime() - startTime)/1000000000f)+" in seconds");
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
