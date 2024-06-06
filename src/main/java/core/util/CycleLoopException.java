package core.util;

@SuppressWarnings("serial")
public class CycleLoopException extends Exception
{
	public CycleLoopException(String message)
	{
		super(message);
	}
}