package debug.profiler;

import core.time.Stopwatch;

public class StopwatchProfiler extends Stopwatch
{
	private String name;
	
	public StopwatchProfiler(final String name) 
	{
		this.name = name;
	}
	
	@Override
	public double update()
	{
		double deltaTime = super.update();
		System.out.println("Stopwatch \"" + this.name + "\": " + deltaTime + "s");
		return deltaTime;
	}
}
