package glfw;

import org.joml.Vector2i;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.HashMap;
import java.util.Map;

import core.Window;
import glfw.input.GLFWInputKeyboard;
import glfw.input.GLFWInputMouse;

public class GLFWWindow extends Window
{
	private long windowID;

	private static Map<Long, GLFWWindow> windowsFromID = new HashMap<Long, GLFWWindow>();
	
	public GLFWWindow(final Vector2i size, final String title)
	{
		super(size, title);
		GLFWWindow.windowsFromID.put(this.windowID, this);
		this.inputKeyboard = new GLFWInputKeyboard(this);
		this.inputMouse = new GLFWInputMouse(this);
		
		glfwSetWindowSizeCallback(this.windowID, (long windowID, int width, int height) -> resizeCallback(windowID, width, height));
	}
	
	public static GLFWWindow getWindowFromID(final long windowID)
	{
		return GLFWWindow.windowsFromID.get(windowID);
	}
	
	@Override
	protected final void initialize()
	{
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
		
		this.windowID = glfwCreateWindow(this.size.x, this.size.y, this.title, NULL, NULL);
		if(this.windowID == NULL)
			throw new IllegalStateException("Failed to create GLFW window");
			
		glfwMakeContextCurrent(this.windowID);
		
		setVSync(false);
		
		glfwShowWindow(this.windowID);
		
		GL.createCapabilities();
	}

	private void resizeCallback(long windowID, int width, int height)
	{
		this.size.x = width;
		this.size.y = height;
	}
	
	@Override
	protected void onFrameStart()
	{
        glfwMakeContextCurrent(this.windowID);		
		
		glfwPollEvents();
	}

	@Override
	protected void onFrameEnd()
	{
		glfwSwapBuffers(this.windowID);
		glfwSetWindowSize(this.windowID, this.size.x, this.size.y);
	}

	@Override
	protected void onDestroy()
	{
		//Free the window callbacks and destroy the window
		glfwFreeCallbacks(this.windowID);
		glfwDestroyWindow(this.windowID);
	}

	@Override
	public boolean isCloseRequested()
	{
		return glfwWindowShouldClose(this.windowID);
	}

	@Override
	public void setVSync(final boolean value)
	{
		glfwSwapInterval(value ? 1 : 0);
	}
	
	public long getWindowID()
	{
		return this.windowID;
	}
}
