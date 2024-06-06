package core.renderer.texture;

import org.joml.Vector2f;

import core2D.renderer2D.Renderer2D;

public class Sprite 
{
	private Texture2D texture;
	private Vector2f[] textureCoords;
	
	public Sprite(final Texture2D texture)
	{
		this.texture = texture;
		this.textureCoords = Renderer2D.QUAD_TEXTURE_COORDS;
	}
	public Sprite(final Texture2D texture, final Vector2f[] textureCoords)
	{
		this.texture = texture;
		this.textureCoords  = textureCoords;
	}
	
	public Texture2D getTexture()
	{
		return this.texture;
	}
	public Vector2f[] getTextureCoords()
	{
		return this.textureCoords;
	}
	public void setTexture(final Texture2D texture)
	{
		this.texture = texture;
	}
	public void setTextureCoords(final Vector2f[] textureCoords)
	{
		this.textureCoords = textureCoords;
	}
}
