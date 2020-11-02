package Application;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class parallelSum 
{
	public static void main(String[] args) throws InterruptedException
	{
//--------------------------initialize array----------------------------------------------------------------------------
		System.out.println("Please Wait: \nCreating Array of 200,000,000 numbers....");
		//Random number generator
		Random generator = new Random(System.currentTimeMillis());
		//array stores 2 million numbers 
		byte arrayBytes[] = new byte[200000000];
		//Stores Sum Total
		long sum = 0; 
		long startTime;
		long endTime;
		//initialize array with random numbers < 10
		for(int x = 0; x < 200000000; x++)
		{
			arrayBytes[x] = (byte)generator.nextInt(10);
		}
//--------------------------Single Thread calculation---------------------------------------------------------------------
		System.out.println("\nSingle Thread SUM of array....");
		//Sum array using one process, time calculation
		startTime = System.nanoTime();
		for(int x = 0; x < 200000000; x++)
		{
			
			sum += (long)arrayBytes[x];
		}
		endTime = System.nanoTime();
		System.out.println("Sum Calculated: " + sum + "\nNanoseconds to Calculate: " + (endTime - startTime));
//--------------------------Multithread calculation---------------------------------------------------------------------
		System.out.println("\nMultithreaded SUM of array....");
		AtomicLong aSum = new AtomicLong();
		//start runnable creation for threads 1-4
		Runnable t1r = () ->
		{
			long y = 0;
			for(int x = 0; x < 50000000; x++)
			{	
				y += (long)arrayBytes[x];
			}
			aSum.getAndAdd(y);	
		};
		Runnable t2r = () ->
		{
			long y = 0;
			for(int x = 50000000; x < 100000000; x++)
			{	
				y += (long)arrayBytes[x];
			}
			aSum.getAndAdd(y);	
		};
		Runnable t3r = () ->
		{
			long y = 0;
			for(int x = 100000000; x < 150000000; x++)
			{	
				y += (long)arrayBytes[x];
			}
			aSum.getAndAdd(y);	
		};
		Runnable t4r = () ->
		{
			long y = 0;
			for(int x = 150000000; x < 200000000; x++)
			{	
				y += (long)arrayBytes[x];
			}
			aSum.getAndAdd(y);	
		};
		//create four threads using above runnables
		Thread t1= new Thread(t1r);
		Thread t2= new Thread(t2r);
		Thread t3= new Thread(t3r);
		Thread t4= new Thread(t4r);
		//get system start time to calculate speediness
		startTime=System.nanoTime();
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		//get system end time to calculate speediness
		endTime=System.nanoTime();
		//wait for all threads to be finished before continuing
		t1.join();
		t2.join();
		t3.join();
		t4.join();
		//display results
		System.out.println("Sum Calculated: " + aSum + "\nNanoseconds to Calculate: " + (endTime - startTime));
	}
	
}
