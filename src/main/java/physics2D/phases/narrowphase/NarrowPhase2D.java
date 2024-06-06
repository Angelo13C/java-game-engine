package physics2D.phases.narrowphase;

import java.util.ArrayList;
import java.util.List;

import core.util.tuples.Pair;
import physics2D.Manifold2D;
import physics2D.CollisionDetection2D;
import physics2D.PhysicsBody2D;
import physics2D.shapes.PhysicsShape2D;

public class NarrowPhase2D
{
	private List<PhysicsBody2D> bodies = new ArrayList<PhysicsBody2D>();
	private List<Manifold2D> collisions = new ArrayList<Manifold2D>();
	
	public void addBody(final PhysicsBody2D body)
	{
		this.bodies.add(body);
	}
	
	public void update(List<Pair<PhysicsBody2D, PhysicsBody2D>> broadPhaseCollisions)
	{
		this.collisions.clear();
		
		for(Pair<PhysicsBody2D, PhysicsBody2D> broadPhaseCollision : broadPhaseCollisions)
		{
			for(PhysicsShape2D aShape : broadPhaseCollision.a.getShapes())
			{
				for(PhysicsShape2D bShape : broadPhaseCollision.b.getShapes())
				{/*
					if(CollisionDetection2D.intersect(aShape, bShape))
					{
						Manifold2D collision = new Manifold2D(broadPhaseCollision.a, broadPhaseCollision.b);
						this.collisions.add(collision);
					}*/
				}
			}
		}
	}
	
	public List<Manifold2D> getCollisions()
	{
		return this.collisions;
	}
}
