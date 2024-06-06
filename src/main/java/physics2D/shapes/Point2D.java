package physics2D.shapes;

import org.joml.Vector2f;

public class Point2D implements PhysicsShape2D
{
	public Vector2f position;
	
	public Point2D(final Vector2f position)
	{
		this.position = position;
	}
}
