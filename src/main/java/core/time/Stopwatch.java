package core.time;

public class Stopwatch
{
	private double startTime;
	private double lastUpdateTime;
	private double deltaTime;
	
	public Stopwatch()
	{
		this.startTime = Time.getTime();
		this.lastUpdateTime = 0f;
		this.deltaTime = 0f;
	}
	
	public Stopwatch(final double startTime)
	{
		this.startTime = startTime;
		this.lastUpdateTime = 0;
		this.deltaTime = 0f;
	}
	
	//Get the time passed since this stopwatch has been created
	public double getTime()
	{
		return Time.getTime() - startTime;
	}
	
	//Get the time since the last update
	public double getDeltaTime()
	{
		return this.deltaTime;
	}
	
	public double update()
	{
		this.deltaTime = getTime() - this.lastUpdateTime;
		this.lastUpdateTime = getTime();
		return this.deltaTime;
	}
}
