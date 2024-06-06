package physics2D.phases.broadphase;

import java.util.ArrayList;
import java.util.List;

import core.util.tuples.Pair;
import physics2D.PhysicsBody2D;

public abstract class BroadPhase2D
{
	protected List<PhysicsBody2D> bodies = new ArrayList<PhysicsBody2D>();
	protected List<Pair<PhysicsBody2D, PhysicsBody2D>> collisions = new ArrayList<Pair<PhysicsBody2D, PhysicsBody2D>>();
	
	public void addBody(final PhysicsBody2D body)
	{
		this.bodies.add(body);
	}
	
	public abstract void update();
	
	public List<Pair<PhysicsBody2D, PhysicsBody2D>> getCollisions()
	{
		return this.collisions;
	}
}
