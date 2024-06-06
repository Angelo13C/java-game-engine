package openGL.objects;

import java.nio.ByteBuffer;

import core.renderer.texture.Texture2D;

import static org.lwjgl.opengl.GL42.*;

public class OpenGLTexture2D extends Texture2D
{
	private int rendererID;
	
	public OpenGLTexture2D(final String filePath) 
	{
		super(filePath);
	}
	
	public OpenGLTexture2D(final int width, final int height) 
	{
		super(width, height);
		
		glTexStorage2D(this.rendererID, 1, GL_RGBA8, width, height);
		
		//Set texture parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}
	
	public int getRendererID()
	{
		return this.rendererID;
	}

	@Override
	public void bind(final int slot) 
	{
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, this.rendererID);
	}

	@Override
	protected void initialize(final int width, final int height)
	{
		super.initialize(width, height);
		this.rendererID = glGenTextures();
	}

	@Override
	public void setData(final ByteBuffer data)
	{		
		glBindTexture(GL_TEXTURE_2D, this.rendererID);
		
		//Set texture parameters
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		//Data MUST BE ALLOCATED AS DIRECT BYTE BUFFER
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);	//THIS ONLY WORKS WITH IMAGES WITH 4 CHANNELS
	}
}
