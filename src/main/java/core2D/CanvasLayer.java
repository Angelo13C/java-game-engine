package core2D;

import org.joml.Vector2f;

import core.ecs.Behaviour;
import core.layer.Layer;

public class CanvasLayer extends Layer<Behaviour>
{
	private Vector2f parallax;

	public Vector2f getParallax() 
	{
		return this.parallax;
	}
	public void setParallax(final Vector2f parallax) 
	{
		this.parallax = parallax;
	}	
}
