
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Stanley
 */
public class Pancakes {
    
        private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());
    
    public static void main(String[] args) {
        try {
            // THENUMBER    STARTING      ENDING TIMESTOSPAWN
            //Runtime.getRuntime().exec("c");
            int numThreads =4;
            BigDecimal originalNumber = new BigDecimal("1000000000000037");
            String startingValue = "2";
            
            BigDecimal ceiling = bigSqrt(originalNumber).setScale(0, BigDecimal.ROUND_CEILING);
            BigDecimal range = ceiling.divide(new BigDecimal(Integer.toString(numThreads)));
            range = range.setScale(0, BigDecimal.ROUND_CEILING);
              BigDecimal from = new BigDecimal(startingValue);
              BigDecimal to = range;
            ArrayList<Process> processes = new ArrayList<Process>();
            
            for(int i = 1; i <= numThreads; i++)
        {
            processes.add(Runtime.getRuntime().exec("java Driver " + originalNumber + " " + startingValue + " " + to.toString()));
            from = to.add(new BigDecimal("1"));
            to = range.multiply(new BigDecimal((i + 1) + ""));
        }
            Process p = Runtime.getRuntime().exec("java Driver 100011111 2 2000");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while((line = br.readLine()) != null)
                System.out.println("From slave: " + line);
        } catch (IOException ex) {
            Logger.getLogger(Pancakes.class.getName()).log(Level.SEVERE, null, ex);
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
