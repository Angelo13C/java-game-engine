package core.renderer.objects;

import java.lang.reflect.InvocationTargetException;

public abstract class VertexBuffer
{
	protected BufferLayout bufferLayout;
	
	public VertexBuffer(final float[] vertices) {}
	public VertexBuffer(final int size) {}
	
	public abstract void bind();
	public abstract void unbind();	
	
	public abstract void setData(final float[] vertices);
	
	public final BufferLayout getLayout()
	{
		return this.bufferLayout;
	}

	public final void setLayout(BufferLayout layout) 
	{
		this.bufferLayout = layout;
	}
	
	
	private static Class<?> selectedAPI;
	public static VertexBuffer create(final float[] vertices)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ float[].class };
			return (VertexBuffer) selectedAPI.getConstructor(parametersType).newInstance(vertices);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	
	public static VertexBuffer create(final int size)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ int.class };
			return (VertexBuffer) selectedAPI.getConstructor(parametersType).newInstance(size);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	
	public static <T extends VertexBuffer> void selectAPI(Class<T> vertexBufferClass)
	{
		VertexBuffer.selectedAPI = vertexBufferClass;
	}
}
