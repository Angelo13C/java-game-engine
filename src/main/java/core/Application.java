package core;

import core.input.InputKeyboard;
import core.input.InputMouse;
import core.time.Time;

public abstract class Application
{	
	protected Window window;
	private boolean isRunning = false;

	private static Window staticWindow;

	public Application(final Window window)
	{
		this.window = window;
		Application.staticWindow = window;
	}
	
	private final void destroy()
	{
		onDestroy();
		this.window.onDestroy();
	}
	
	public final void start()
	{
		this.isRunning = true;
	    
		while(this.isRunning)
		{			
			if(this.isRunning)
				this.isRunning = !this.window.isCloseRequested();

			Time.update();
			
			onUpdate();
		}

		destroy();
	}
	
	public final void stop()
	{
		this.isRunning = false;
	}
	
	protected abstract void onUpdate();
	protected abstract void onDestroy();

	public static Window getWindow()
	{
		return Application.staticWindow;
	}
	public static InputKeyboard getKeyboard()
	{
		return Application.staticWindow.getInputKeyboard();
	}
	public static InputMouse getMouse()
	{
		return Application.staticWindow.getInputMouse();
	}
}
