package core.time;

public class Time
{
	private static final double NANO_TO_SECONDS = 1E-9;
	
	private static long startTime = System.nanoTime();
	private static Stopwatch stopwatch = new Stopwatch(0);
	
	//Get the time of the system
	public static final double getSystemTime()
	{
		return System.nanoTime() * NANO_TO_SECONDS;
	}
	
	//Get the time passed since the application started
	public static final double getTime()
	{
		return (System.nanoTime() - startTime) * NANO_TO_SECONDS;
	}
	
	public static final double update()
	{
		return Time.stopwatch.update();
	}
	
	public static final double getDeltaTime()
	{
		return Time.stopwatch.getDeltaTime();
	}
}
