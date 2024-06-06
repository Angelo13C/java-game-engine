package physics2D;

import java.util.ArrayList;
import java.util.List;

import core.util.tuples.Pair;
import physics2D.phases.broadphase.BroadPhase2D;
import physics2D.phases.broadphase.NSquaredBroadPhase2D;
import physics2D.phases.narrowphase.NarrowPhase2D;

public class PhysicsWorld2D
{
	private List<PhysicsBody2D> bodies = new ArrayList<PhysicsBody2D>();
	private List<Manifold2D> collisions = new ArrayList<Manifold2D>();
	
	private BroadPhase2D broadPhase = new NSquaredBroadPhase2D();
	private NarrowPhase2D narrowPhase = new NarrowPhase2D();
	
	public void addBody(final PhysicsBody2D body)
	{
		this.bodies.add(body);
		this.broadPhase.addBody(body);
		this.narrowPhase.addBody(body);
	}
	
	public void update(final float deltaTime)
	{
		simulate(deltaTime);
		
		detectCollisions();
	}
	
	private void simulate(final float deltaTime)
	{
		for(PhysicsBody2D body : this.bodies)
			body.integrate(deltaTime);
	}
	
	private void detectCollisions()
	{		
		//Broad phase
		this.broadPhase.update();
		List<Pair<PhysicsBody2D, PhysicsBody2D>> broadPhaseCollisions = this.broadPhase.getCollisions();
		
		//Narrow phase
		this.narrowPhase.update(broadPhaseCollisions);
		this.collisions = this.narrowPhase.getCollisions();
	}
	
	public List<Manifold2D> getCollisions()
	{
		return this.collisions;
	}
}
