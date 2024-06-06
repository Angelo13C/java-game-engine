package core.renderer.texture;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import static org.lwjgl.stb.STBImage.*;

public abstract class Texture2D extends Texture
{
	protected int pixelsPerUnit = 100;
	protected int width;
	protected int height;
	
	public Texture2D(final String filePath)
	{
		int[] width = new int[1];
		int[] height = new int[1];
		int[] channels = new int[1];
		//Flip the texture
		stbi_set_flip_vertically_on_load(true);
		ByteBuffer data = stbi_load(filePath, width, height, channels, 0);

		if(data == null)
		{
			System.out.println("Couldn't load the texture: " + filePath);
		}
		else 
		{
			initialize(width[0], height[0]);
			setData(data);
		}
		
		stbi_image_free(data);
	}
	
	public Texture2D(final int width, final int height) 
	{
		initialize(width, height);
	}

	//In pixels
	@Override
	public int getWidth() 
	{
		return this.width;
	}
	@Override
	public int getHeight()
	{
		return this.height;
	}

	public int getPixelsPerUnit()
	{
		return this.pixelsPerUnit;
	}
	public void setPixelsPerUnit(final int pixelsPerUnit)
	{
		this.pixelsPerUnit = pixelsPerUnit;
	}
	//In units
	public float getWidthInUnits()
	{
		return ((float) this.width) / this.pixelsPerUnit;
	}
	public float getHeightInUnits()
	{
		return ((float) this.height) / this.pixelsPerUnit;
	}
	
	protected void initialize(final int width, final int height)
	{
		this.width = width;
		this.height = height;
	}
	
	public abstract void setData(final ByteBuffer data);

	private static Class<?> selectedAPI;
	public static Texture2D create(final String filePath)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ String.class };
			return (Texture2D) selectedAPI.getConstructor(parametersType).newInstance(filePath);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}	
	public static Texture2D create(final int width, final int height)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ int.class, int.class };
			return (Texture2D) selectedAPI.getConstructor(parametersType).newInstance(width, height);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	public static <T extends Texture2D> void selectAPI(final Class<T> texture2DClass)
	{
		Texture2D.selectedAPI = texture2DClass;
	}
}
