package core.input;

import org.joml.Vector2i;

import core.Window;

public abstract class InputMouse {
	protected Window window;

	protected final int BUTTONS_COUNT = 9;
	protected boolean[] downButtons = new boolean[BUTTONS_COUNT];
	protected boolean[] pressedButtons = new boolean[BUTTONS_COUNT];
	protected boolean[] upButtons = new boolean[BUTTONS_COUNT];
	
	protected Vector2i position;
	private Vector2i lastPosition;
	protected Vector2i scrollDelta;
	
	public InputMouse(final Window window)
	{
		this.window = window;
		position = new Vector2i(0, 0);
		lastPosition = new Vector2i(0, 0);
		scrollDelta = new Vector2i(0, 0);
	}
	
	public void update()
	{
		lastPosition = position;
		scrollDelta = new Vector2i(0, 0);
		
		for(int i = 0; i < BUTTONS_COUNT; i++)
		{
			this.downButtons[i] = false;
			this.upButtons[i] = false;
		}
		
		onUpdate();
	}
	
	protected abstract void onUpdate();
	
	public final boolean isButtonDown(final int button)
	{
		return this.downButtons[button];
	}
	
	public final boolean isButtonPressed(final int button)
	{
		return this.pressedButtons[button];
	}
	
	public final boolean isButtonUp(final int button)
	{
		return this.upButtons[button];
	}
	
	public final Vector2i getPosition()
	{
		return this.position;
	}
	
	public final Vector2i getDeltaPosition()
	{
		return new Vector2i(this.lastPosition.x - this.position.x, this.lastPosition.y - this.position.y);
	}
	
	public final Vector2i getScrollDelta()
	{
		return this.scrollDelta;
	}
}
