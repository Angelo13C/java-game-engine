package physics2D;

import java.util.List;

import org.joml.Vector2f;

import physics2D.shapes.PhysicsShape2D;

public class PhysicsBody2D
{
	private Vector2f position;
	private Vector2f oldPosition;
	private Vector2f linearVelocity;
	
	private List<PhysicsShape2D> shapes;
	private PhysicsShape2D boundingShape;
	
	private float inverseMass;
	
	public void integrate(final float deltaTime)
	{
		this.position.add(new Vector2f(this.linearVelocity).mul(deltaTime));
	}
	
	public Vector2f getPosition()
	{
		return this.position;
	}
	public Vector2f getLinearVelocity()
	{
		return this.linearVelocity;
	}

	public List<PhysicsShape2D> getShapes()
	{
		return this.shapes;
	}
	public PhysicsShape2D getBoundingShape()
	{
		return this.boundingShape;
	}
}
