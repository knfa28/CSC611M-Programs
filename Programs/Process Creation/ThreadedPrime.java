/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.math.BigDecimal;

/**
 *
 * @author CHOY
 */
public class ThreadedPrime extends Thread{
    private BigDecimal from;
    private BigDecimal to;
    private BigDecimal num;
    private boolean isPrime;
    private ThreadMain threadmain;
    public ThreadedPrime(BigDecimal from, BigDecimal to, BigDecimal num, ThreadMain threadmain)
    {
        this.from = from;
        this.to = to;
        this.num = num;
        isPrime = true;
        this.threadmain = threadmain;
    }
    
    public void run()
    {
        try{
        for(BigDecimal i = this.from; !this.to.equals(i); i = i.add(new BigDecimal("1")))
        {
            if(num.remainder(i) == new BigDecimal("0"))
            {
                isPrime = false;
            }
        }
        }finally{
            threadmain.threadHasFinished(this.getId(), isPrime);
        }
    }
    
    public boolean isPrime()
    {
        return isPrime;
    }
}
