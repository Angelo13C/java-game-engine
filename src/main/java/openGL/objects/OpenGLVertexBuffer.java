package openGL.objects;

import static org.lwjgl.opengl.GL20.*;

import core.renderer.objects.VertexBuffer;

public class OpenGLVertexBuffer extends VertexBuffer
{
	private int rendererID;
	
	public OpenGLVertexBuffer(final float[] vertices)
	{
		super(vertices);
		this.rendererID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.rendererID);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
	}
	
	public OpenGLVertexBuffer(final int size)
	{
		super(size);
		this.rendererID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, this.rendererID);
		glBufferData(GL_ARRAY_BUFFER, size, GL_DYNAMIC_DRAW);
	}

	@Override
	public void bind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, this.rendererID);
	}

	@Override
	public void unbind()
	{
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	@Override
	public void setData(final float[] vertices)
	{
		glBindBuffer(GL_ARRAY_BUFFER, this.rendererID);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
	}
}
