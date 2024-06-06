package openGL.objects;

import static org.lwjgl.opengl.GL30.*;

import core.renderer.objects.IndexBuffer;

public class OpenGLIndexBuffer extends IndexBuffer
{
	private int rendererID;
	private int count;
	
	public OpenGLIndexBuffer(final int[] indices)
	{
		super(indices);
		this.rendererID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.rendererID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);
		count = indices.length;
	}

	@Override
	public void bind()
	{
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, this.rendererID);
	}

	@Override
	public void unbind()
	{
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	@Override
	public int getCount()
	{
		return count;
	}
}
