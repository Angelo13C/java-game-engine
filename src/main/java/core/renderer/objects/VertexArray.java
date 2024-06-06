package core.renderer.objects;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public abstract class VertexArray
{
	protected ArrayList<VertexBuffer> vertexBuffers = new ArrayList<VertexBuffer>();
	protected IndexBuffer indexBuffer;
	
	public abstract void bind();
	public abstract void unbind();

	public abstract void addVertexBuffer(final VertexBuffer buffer);
	public abstract void setIndexBuffer(final IndexBuffer buffer);
	
	public final ArrayList<VertexBuffer> getVertexBuffers()
	{
		return this.vertexBuffers;
	}
	
	public final IndexBuffer getIndexBuffer()
	{
		return this.indexBuffer;
	}
	
	private static Class<?> selectedAPI;
	public static VertexArray create()
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ };
			return (VertexArray) selectedAPI.getConstructor(parametersType).newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	
	public static <T extends VertexArray> void selectAPI(Class<T> vertexArrayClass)
	{
		VertexArray.selectedAPI = vertexArrayClass;
	}
}
