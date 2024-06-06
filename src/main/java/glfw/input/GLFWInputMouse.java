package glfw.input;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2i;

import core.input.InputMouse;
import glfw.GLFWWindow;

public class GLFWInputMouse extends InputMouse
{
	public GLFWInputMouse(final GLFWWindow window)
	{
		super(window);
		glfwSetCursorPosCallback(window.getWindowID(), (long windowID, double positionX, double positionY) -> mousePositionCallback(windowID, positionX, positionY));
		glfwSetMouseButtonCallback(window.getWindowID(), (long windowID, int button, int action, int mods) -> mouseButtonCallback(windowID, button, action, mods));
		glfwSetScrollCallback(window.getWindowID(), (long windowID, double scrollX, double scrollY) -> mouseScrollCallback(windowID, scrollX, scrollY));
	}

	public void mousePositionCallback(final long windowID, final double positionX, final double positionY)
	{
		this.position = new Vector2i((int) positionX, (int) positionY);
	}
	
	public void mouseButtonCallback(final long windowID, final int button, final int action, final int mods)
	{
		if(action == GLFW_PRESS)
		{
			this.pressedButtons[button] = true;
			this.downButtons[button] = true;
		}
		else if(action == GLFW_RELEASE)
		{
			this.pressedButtons[button] = false;
			this.upButtons[button] = true;	
		}
	}

	public void mouseScrollCallback(final long windowID, final double xDelta, final double yDelta)
	{
		this.scrollDelta = new Vector2i((int) xDelta, (int) yDelta);
	}

	@Override
	protected void onUpdate() {	}
}
