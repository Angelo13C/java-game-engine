package physics2D.phases.broadphase;

import core.util.tuples.Pair;
import physics2D.CollisionDetection2D;
import physics2D.PhysicsBody2D;

public class NSquaredBroadPhase2D extends BroadPhase2D
{
	@Override
	public void update()
	{
		this.collisions.clear();
		
		for(int i = 0; i < this.bodies.size(); i++)
		{
			for(int j = i + 1; j < this.bodies.size(); j++)
			{
				final PhysicsBody2D a = this.bodies.get(i);
				final PhysicsBody2D b = this.bodies.get(j);
				/*if(CollisionDetection2D.intersect(a.getBoundingShape(), b.getBoundingShape()))
				{
					this.collisions.add(new Pair<PhysicsBody2D, PhysicsBody2D>(a, b));
				}*/
			}
		}
	}
}
