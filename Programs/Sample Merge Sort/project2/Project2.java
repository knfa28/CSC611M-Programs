/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package project2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 *
 * @author Stanley
 */
public class Project2 {

    /**
     * @param args the command line arguments
     */
    private static final int numOfItems = 1000000;
    public static void main(String[] args) {
        
       int[] numArray = generateSequence();
        for(int i = 0; i< numOfItems; i++)
       {
           System.out.println(numArray[i]);
       }
                
        //exportToTxt(generateSequence());
    }
    
    public static int[] generateSequence()
    {
        Random rand = new Random();


       int[] numArray = new int[numOfItems];
       
       for(int i = 0; i< numOfItems; i++)
       {
           numArray[i] = rand.nextInt(1000000);
       }
       return numArray;
    }
    
    public static void exportToTxt(int[] numArray)
    {
        BufferedWriter writer = null;
try
{
    writer = new BufferedWriter( new FileWriter( "numberList.txt"));
    for(int i = 0; i< numOfItems; i++)
       {
           writer.write(numArray[i]+",");
       }

}
catch ( IOException e)
{
}
finally
{
    try
    {
        if ( writer != null)
        writer.close( );
    }
    catch ( IOException e)
    {
    }
}
    }
}
