package core.renderer.objects;

import java.lang.reflect.InvocationTargetException;

public abstract class IndexBuffer
{
	public IndexBuffer(final int[] indices) {}
	
	public abstract void bind();
	public abstract void unbind();
	
	public abstract int getCount();
	
	private static Class<?> selectedAPI;
	public static IndexBuffer create(final int[] indices)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ int[].class };
			return (IndexBuffer) selectedAPI.getConstructor(parametersType).newInstance(indices);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	
	public static <T extends IndexBuffer> void selectAPI(Class<T> indexBufferClass)
	{
		IndexBuffer.selectedAPI = indexBufferClass;
	}
}
