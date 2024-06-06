package physics2D.shapes;

import org.joml.Vector2f;

public class Circle2D implements PhysicsShape2D
{
	public Vector2f position;
	public float radius;
	
	public Circle2D(final Vector2f position, final float radius)
	{
		this.position = position;
		this.radius = radius;
	}
}
