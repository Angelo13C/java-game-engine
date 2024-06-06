package core.util;

import core.time.Time;

public class Random 
{
	private Random() {}
	
	private static java.util.Random random = new java.util.Random((long) Time.getSystemTime() * 100);
	
	public static float getInRange(float start, float end)
	{
		return ((end - start) * random.nextFloat()) + start;
	}
}
