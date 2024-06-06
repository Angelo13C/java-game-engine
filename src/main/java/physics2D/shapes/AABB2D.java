package physics2D.shapes;

import org.joml.Vector2f;

public class AABB2D implements PhysicsShape2D
{
	public Vector2f min;
	public Vector2f max;
	
	public Vector2f position;
	public Vector2f size;
	
	public AABB2D(final Vector2f min, final Vector2f max)
	{
		this.min = min;
		this.max = max;
		
		this.size = new Vector2f(max).sub(min).div(2f);
		this.position = new Vector2f(size).add(min);
	}
}
