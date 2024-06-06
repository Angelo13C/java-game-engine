package glfw.input;

import static org.lwjgl.glfw.GLFW.*;

import core.input.InputKeyboard;
import glfw.GLFWWindow;

public class GLFWInputKeyboard extends InputKeyboard
{
	public GLFWInputKeyboard(final GLFWWindow window)
	{
		super(window);
		glfwSetKeyCallback(window.getWindowID(), (long windowID, int key, int scanCode, int action, int mods) -> keyCallback(windowID, key, scanCode, action, mods));
	}

	public void keyCallback(final long windowID, final int key, final int scanCode, final int action, final int mods)
	{
		int keyCodeOrdinal = KeyCode.forValue(key).ordinal();

		if(action == GLFW_PRESS)
		{
			this.pressedKeys[keyCodeOrdinal] = true;
			this.downKeys[keyCodeOrdinal] = true;
		}
		else if(action == GLFW_RELEASE)
		{
			this.pressedKeys[keyCodeOrdinal] = false;
			this.upKeys[keyCodeOrdinal] = true;	
		}
	}

	@Override
	protected void onUpdate() {	}
}
