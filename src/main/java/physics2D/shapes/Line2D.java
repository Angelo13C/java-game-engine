package physics2D.shapes;

import org.joml.Vector2f;

public class Line2D implements PhysicsShape2D
{
	public Vector2f min;
	public Vector2f max;
	
	public float length;	//Optimization, the squared length of the line is cached
	
	public Line2D(final Vector2f min, final Vector2f max)
	{
		this.min = min;
		this.max = max;
		
		length = min.distance(max);
	}
}
