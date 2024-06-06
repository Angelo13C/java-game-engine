package physics2D;

import org.joml.Vector2f;

public class Manifold2D
{
	public PhysicsBody2D a;
	public PhysicsBody2D b;
	public Vector2f normal;
	public float penetration;

	public Manifold2D()	{}
	public Manifold2D(final PhysicsBody2D a, final PhysicsBody2D b, final Vector2f normal, final float penetration)
	{
		this.a = a;
		this.b = b;
		this.normal = normal;
		this.penetration = penetration;
	}
}
