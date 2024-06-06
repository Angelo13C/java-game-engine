package core;

import org.joml.Vector2i;

import core.input.InputKeyboard;
import core.input.InputMouse;

public abstract class Window
{
	protected Vector2i size;
	protected String title;
	
	protected InputKeyboard inputKeyboard;
	protected InputMouse inputMouse;
	
	public Window(final Vector2i size, final String title)
	{
		this.size = size;
		this.title = title;
		
		initialize();
	}
	
	protected abstract void initialize();
	
	public final void startFrame()
	{
		this.inputKeyboard.update();
		this.inputMouse.update();
		onFrameStart();
	}
	
	protected abstract void onFrameStart();
	
	public final void endFrame()
	{
		onFrameEnd();
	}
	
	protected abstract void onFrameEnd();
	
	public final void destroy()
	{
		onDestroy();
	}
	
	protected abstract void onDestroy();

	public abstract boolean isCloseRequested();	
	public abstract void setVSync(final boolean value);
	
	public InputKeyboard getInputKeyboard()
	{
		return this.inputKeyboard;
	}
	
	public InputMouse getInputMouse()
	{
		return this.inputMouse;
	}
	
	public Vector2i getSize()
	{
		return this.size;
	}
	
	public String getTitle()
	{
		return this.title;
	}

	public void setSize(final Vector2i size)
	{
		this.size = size;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}
}
