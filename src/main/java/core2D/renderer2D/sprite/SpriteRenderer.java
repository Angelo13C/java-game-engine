package core2D.renderer2D.sprite;

import org.joml.Vector2f;

import core.ecs.Behaviour;
import core.renderer.Color;
import core.renderer.texture.Sprite;
import core2D.Transform2D;
import core2D.renderer2D.Renderer2D;

public class SpriteRenderer extends Behaviour
{
	private Sprite sprite;
	private Color color;

	public SpriteRenderer()
	{

	}
	
	public SpriteRenderer(final Sprite sprite, final Color color)
	{
		setSprite(sprite);
		setColor(color);
	}

	@Override
	protected void onUpdate() 
	{
		Vector2f position = new Vector2f();
		Vector2f scale = new Vector2f();
		float rotation = Transform2D.getValues(this.gameObject, position, scale);
		
		Renderer2D.drawQuad(position, scale, (float) Math.toRadians(rotation), this.sprite, this.color);
	}
	
	public void setSprite(final Sprite sprite)
	{
		this.sprite = sprite;
	}	
	public void setColor(final Color color)
	{
		this.color = color;
	}
	public Sprite getSprite()
	{
		return this.sprite;
	}	
	public Color getColor()
	{
		return this.color;
	}
}
