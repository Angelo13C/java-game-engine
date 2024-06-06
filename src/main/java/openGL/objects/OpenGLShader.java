package openGL.objects;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;

import org.joml.Matrix4f;

import core.renderer.objects.Shader;

public class OpenGLShader extends Shader
{
	private int programID;
	
	@SuppressWarnings("serial")
	private static final HashMap<ShaderDataType, Integer> TYPE_FROM_SHADER_DATA_TYPE = new HashMap<ShaderDataType, Integer>()
	{
		{
			put(ShaderDataType.NONE, -1);
	    	put(ShaderDataType.FLOAT, GL_FLOAT);
	    	put(ShaderDataType.FLOAT2, GL_FLOAT);
	    	put(ShaderDataType.FLOAT3, GL_FLOAT);
	    	put(ShaderDataType.FLOAT4, GL_FLOAT);
	    	put(ShaderDataType.INT, GL_INT);
	    	put(ShaderDataType.INT2, GL_INT);
	    	put(ShaderDataType.INT3, GL_INT);
	    	put(ShaderDataType.INT4, GL_INT);
	    	put(ShaderDataType.BOOL, GL_BOOL);
	    	put(ShaderDataType.MAT3, GL_FLOAT);
	    	put(ShaderDataType.MAT4, GL_FLOAT);
		}
	};
    
	public OpenGLShader(final String vertexSource, final String fragmentSource) {
		super(vertexSource, fragmentSource);
	}

	public OpenGLShader(final String filePath) {
		super(filePath);
	}
	
	public static int getTypeFromShaderDataType(final ShaderDataType type)
	{
		return OpenGLShader.TYPE_FROM_SHADER_DATA_TYPE.get(type);
	}

	@Override
	public void bind()
	{		
		glUseProgram(this.programID);
	}

	@Override
	public void unbind()
	{		
		glUseProgram(0);
	}

	@Override
	protected void compileShaders(final String vertexSource, final String fragmentSource)
	{
		int vertexID = compileShader(vertexSource, GL_VERTEX_SHADER, "Vertex");
		if(vertexID == -1)
			return;
		
		int fragmentID = compileShader(fragmentSource, GL_FRAGMENT_SHADER, "Fragment");
		if(fragmentID == -1)
			return;

		//Link shaders
		this.programID = glCreateProgram();
		glAttachShader(this.programID, vertexID);
		glAttachShader(this.programID, fragmentID);
		glLinkProgram(this.programID);
		
		//If it failed linking the program
		int success = glGetProgrami(this.programID, GL_LINK_STATUS);
		if(success == GL_FALSE)
		{
			int logLength = glGetProgrami(this.programID, GL_INFO_LOG_LENGTH);
			System.out.println("Shaders linking failed! \"" + this.filePath + "\"");
			System.out.println(glGetProgramInfoLog(this.programID, logLength)); 
			return;
		}
	}

	//Returns true if successful
	private int compileShader(final String shaderSource, final int type, final String typeName)
	{
		//Create the shader on the GPU and compile it
		int shaderID = glCreateShader(type);
		
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		
		int success = glGetShaderi(shaderID, GL_COMPILE_STATUS);
		//If it failed compiling the shader
		if(success == GL_FALSE)
		{
			int logLength = glGetShaderi(shaderID, GL_INFO_LOG_LENGTH);
			System.out.println(typeName + " shader compilation failed! \"" + this.filePath + "\"");
			System.out.println(glGetShaderInfoLog(shaderID, logLength));
			return -1;
		}
		
		return shaderID;
	}

	@Override
	public void setIntArray(final String name, final int[] values)
	{
		int location = glGetUniformLocation(this.programID, name);
		glUniform1iv(location, values);
	}

	@Override
	public void setMat4(final String name, final Matrix4f value)
	{
		int location = glGetUniformLocation(this.programID, name);
		glUniformMatrix4fv(location, false, value.get(new float[4 * 4]));
	}
}
