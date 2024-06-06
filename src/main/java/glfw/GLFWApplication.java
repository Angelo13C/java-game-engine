package glfw;

import static org.lwjgl.glfw.GLFW.*;

import core.Application;
import core.Window;

public abstract class GLFWApplication extends Application
{
	public GLFWApplication(final Window window) {
		super(window);
	}

	@Override
	protected abstract void onUpdate();

	@Override
	protected void onDestroy()
	{
		//THIS MUST BE THE END OF A GLFW APPLICATION
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
