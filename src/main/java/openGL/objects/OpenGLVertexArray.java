package openGL.objects;

import static org.lwjgl.opengl.GL30.*;

import core.renderer.objects.BufferElement;
import core.renderer.objects.BufferLayout;
import core.renderer.objects.IndexBuffer;
import core.renderer.objects.VertexArray;
import core.renderer.objects.VertexBuffer;
import core.renderer.objects.Shader.ShaderDataType;

public class OpenGLVertexArray extends VertexArray
{
	private int rendererID;
	
	public OpenGLVertexArray() 
	{
		this.rendererID = glGenVertexArrays();
		glBindVertexArray(this.rendererID);
	}
	
	@Override
	public void bind()
	{
		glBindVertexArray(this.rendererID);
	}

	@Override
	public void unbind() 
	{
		glBindVertexArray(0);
	}

	@Override
	public void addVertexBuffer(final VertexBuffer buffer) 
	{
		glBindVertexArray(this.rendererID);
		buffer.bind();
		
		BufferLayout layout = buffer.getLayout();
		for(int i = 0; i < layout.getElements().length; i++)
		{
			BufferElement element = layout.getElements()[i];
			glVertexAttribPointer(
					i, 
					ShaderDataType.getComponentCount(element.type), 
					OpenGLShader.getTypeFromShaderDataType(element.type), 
					element.normalized,
					layout.getStride(),
					element.offset
			);
			glEnableVertexAttribArray(i);
		}
		
		this.vertexBuffers.add(buffer);
	}

	@Override
	public void setIndexBuffer(final IndexBuffer buffer) 
	{
		glBindVertexArray(this.rendererID);
		buffer.bind();
		
		this.indexBuffer = buffer;
	}
}
