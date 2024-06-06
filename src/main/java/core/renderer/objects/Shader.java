package core.renderer.objects;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

public abstract class Shader
{
	private static final String SHADER_TYPE_PREFIX = "#type";
	private static final String REGEX = "(" + SHADER_TYPE_PREFIX + ")( )+([a-zA-Z]+)";
	protected String filePath = "";
	
	public enum ShaderDataType
	{
		NONE, FLOAT, FLOAT2, FLOAT3, FLOAT4, INT, INT2, INT3, INT4, BOOL, MAT3, MAT4;
		
		private static final Map<ShaderDataType, Integer> sizeByValue = new HashMap<ShaderDataType, Integer>();

	    static {
	    	sizeByValue.put(ShaderDataType.NONE, -1);
	    	sizeByValue.put(ShaderDataType.FLOAT, 4);
	    	sizeByValue.put(ShaderDataType.FLOAT2, 4 * 2);
	    	sizeByValue.put(ShaderDataType.FLOAT3, 4 * 3);
	    	sizeByValue.put(ShaderDataType.FLOAT4, 4 * 4);
	    	sizeByValue.put(ShaderDataType.INT, 4);
	    	sizeByValue.put(ShaderDataType.INT2, 4 * 2);
	    	sizeByValue.put(ShaderDataType.INT3, 4 * 3);
	    	sizeByValue.put(ShaderDataType.INT4, 4 * 4);
	    	sizeByValue.put(ShaderDataType.BOOL, 1);
	    	sizeByValue.put(ShaderDataType.MAT3, 4 * 3 * 3);
	    	sizeByValue.put(ShaderDataType.MAT4, 4 * 4 * 4);
	    }
	    
	    public static int getSize(ShaderDataType type)
	    {
	    	return sizeByValue.get(type);
	    }
	    
		private static final Map<ShaderDataType, Integer> componentCountByValue = new HashMap<ShaderDataType, Integer>();

	    static {
	    	componentCountByValue.put(ShaderDataType.NONE, -1);
	    	componentCountByValue.put(ShaderDataType.FLOAT, 1);
	    	componentCountByValue.put(ShaderDataType.FLOAT2, 2);
	    	componentCountByValue.put(ShaderDataType.FLOAT3, 3);
	    	componentCountByValue.put(ShaderDataType.FLOAT4, 4);
	    	componentCountByValue.put(ShaderDataType.INT, 1);
	    	componentCountByValue.put(ShaderDataType.INT2, 2);
	    	componentCountByValue.put(ShaderDataType.INT3, 3);
	    	componentCountByValue.put(ShaderDataType.INT4, 4);
	    	componentCountByValue.put(ShaderDataType.BOOL, 1);
	    	componentCountByValue.put(ShaderDataType.MAT3, 3 * 3);
	    	componentCountByValue.put(ShaderDataType.MAT4, 4 * 4);
	    }
	    
	    public static int getComponentCount(ShaderDataType type)
	    {
	    	return componentCountByValue.get(type);
	    }
	}
	
	public Shader(final String vertexSource, final String fragmentSource)
	{
		compileShaders(vertexSource, fragmentSource);
	}
	
	public Shader(final String filePath)
	{
		this.filePath = filePath;
		
		try
		{
			//Retrieve the vertex and fragment shaders from the source file
			String source = new String(Files.readAllBytes(Paths.get(filePath)));
			String[] splitSource = source.split(REGEX);
			
			String vertexSource = "";
			String fragmentSource = "";
			
			int endOfLineIndex = 0;
			
			//2 are the needed sources
			for(int i = 0; i < 2; i++)
			{
				//Get the index of the letter after #type
				int startIndex = source.indexOf(SHADER_TYPE_PREFIX, endOfLineIndex) + SHADER_TYPE_PREFIX.length() + 1;
				endOfLineIndex = source.indexOf("\r\n", startIndex);
				String typeString = source.substring(startIndex, endOfLineIndex).trim();
				
				if(typeString.equalsIgnoreCase("vertex"))
					vertexSource = splitSource[i + 1];
				else if(typeString.equalsIgnoreCase("fragment"))
					fragmentSource = splitSource[i + 1];
				else
					throw new IOException("Unexpected #type \"" + typeString + "\" for Shader: " + filePath);
			}
			
			compileShaders(vertexSource, fragmentSource);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	protected abstract void compileShaders(final String vertexSource, final String fragmentSource);
	
	public abstract void bind();
	public abstract void unbind();
	
	public abstract void setIntArray(final String name, final int[] values);
	public abstract void setMat4(final String name, final Matrix4f value);	
	
	private static Class<?> selectedAPI;
	public static Shader create(final String vertexSource, final String fragmentSource)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ String.class, String.class };
			return (Shader) selectedAPI.getConstructor(parametersType).newInstance(vertexSource, fragmentSource);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	
	public static Shader create(final String filePath)
	{
		try {
			//ParametersType must match the constructor types
			Class<?>[] parametersType = new Class[]{ String.class };
			return (Shader) selectedAPI.getConstructor(parametersType).newInstance(filePath);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Error
		return null;
	}
	
	public static <T extends Shader> void selectAPI(Class<T> shaderClass)
	{
		Shader.selectedAPI = shaderClass;
	}
}
