package physics2D;

import org.joml.Vector2f;

import physics2D.shapes.AABB2D;
import physics2D.shapes.Circle2D;
import physics2D.shapes.PhysicsShape2D;
import physics2D.shapes.Line2D;
import physics2D.shapes.Point2D;

public class CollisionDetection2D
{
	private CollisionDetection2D() {}
	
	public static boolean intersect(final PhysicsShape2D a, final PhysicsShape2D b, Manifold2D manifold)
	{
		if(a instanceof Circle2D && b instanceof Circle2D)
			return intersect((Circle2D) a, (Circle2D) b, manifold);
		
		else if(a instanceof Circle2D && b instanceof Point2D)
			return intersect((Circle2D) a, (Point2D) b, manifold);		
		else if(b instanceof Circle2D && a instanceof Point2D)
			return intersect((Circle2D) b, (Point2D) a, manifold);	
		
		else if(a instanceof AABB2D && b instanceof AABB2D)
			return intersect((AABB2D) a, (AABB2D) b, manifold);		
		
		else if(a instanceof AABB2D && b instanceof Point2D)
			return intersect((AABB2D) a, (Point2D) b, manifold);		
		else if(b instanceof AABB2D && a instanceof Point2D)
			return intersect((AABB2D) b, (Point2D) a, manifold);	
		
		else if(a instanceof AABB2D && b instanceof Circle2D)
			return intersect((AABB2D) a, (Circle2D) b, manifold);	
		else if(b instanceof AABB2D && a instanceof Circle2D)
			return intersect((AABB2D) b, (Circle2D) a, manifold);	
		
		else if(a instanceof Line2D && b instanceof Point2D)
			return intersect((Line2D) a, (Point2D) b, manifold);	
		else if(b instanceof Line2D && a instanceof Point2D)
			return intersect((Line2D) b, (Point2D) a, manifold);	
		
		else if(a instanceof Line2D && b instanceof Circle2D)
			return intersect((Line2D) a, (Circle2D) b, manifold);	
		else if(b instanceof Line2D && a instanceof Circle2D)
			return intersect((Line2D) b, (Circle2D) a, manifold);	
		
		return false;
	}
	
	public static boolean intersect(final Circle2D a, final Circle2D b, Manifold2D manifold)
	{
		Vector2f direction = new Vector2f(b.position).sub(a.position);		
		float radius = a.radius + b.radius;

		if(direction.lengthSquared() > radius * radius)
			return false;

		if(manifold != null)
		{			
			final float distance = direction.length();
			if(distance != 0f)
			{
				manifold.penetration = radius - distance;
				//Normalize the direction
				manifold.normal = direction.div(distance);
			}
			else
			{
				manifold.penetration = a.radius;
				manifold.normal = new Vector2f(0f, 1f);
			}
		}
		
		return true;
	}			
	public static boolean intersect(final Circle2D a, final Point2D b, Manifold2D manifold)
	{
		Vector2f direction = new Vector2f(b.position).sub(a.position);	

		if(direction.lengthSquared() > a.radius * a.radius)
			return false;

		if(manifold != null)
		{
			final float distance = direction.length();
			manifold.penetration = a.radius - distance;
			manifold.normal = direction.div(distance);
		}
		
		return true;
	}	
	
	//Set the manifold of an intersection between an aabb and another shape
	private static void rectangleOverlapManifold(final Vector2f direction, final Vector2f overlap, Manifold2D manifold)
	{
		if(overlap.x > overlap.y)
		{
			manifold.penetration = overlap.x;
			manifold.normal = new Vector2f(direction.x < 0f ? -1f : 1f, 0f);
		}
		else
		{
			manifold.penetration = overlap.y;
			manifold.normal = new Vector2f(0f, direction.y < 0f ? -1f : 1f);
		}
	}
	public static boolean intersect(final AABB2D a, final AABB2D b, Manifold2D manifold)
	{
		final Vector2f direction = new Vector2f(b.position).sub(a.position);
		
		final Vector2f overlap = new Vector2f(a.size).add(b.size).sub(new Vector2f(direction).absolute());

		//If it isn't overlapping in at least 1 axis
		if(overlap.x <= 0f || overlap.y <= 0f)
			return false;

		if(manifold != null)
		{
			rectangleOverlapManifold(direction, overlap, manifold);
		}
		
		return true;
	}	
	public static boolean intersect(final AABB2D a, final Point2D b, Manifold2D manifold)
	{
		final Vector2f direction = new Vector2f(b.position).sub(a.position);
		final Vector2f aHalfSize = new Vector2f(a.size).div(2f);

		final Vector2f overlap = new Vector2f(aHalfSize).sub(new Vector2f(direction).absolute());

		//If it isn't overlapping in at least 1 axis
		if(overlap.x <= 0f || overlap.y <= 0f)
			return false;

		if(manifold != null)
		{
			rectangleOverlapManifold(direction, overlap, manifold);
		}

		return true;
	}
	
	public static boolean intersect(final AABB2D a, final Circle2D b, Manifold2D manifold)
	{
		if(intersect(a, new Point2D(b.position), manifold))	//If the circle is inside the rectangle
		{
			if(manifold != null)
				manifold.penetration += b.radius;
			
			return true;
		}

		final Vector2f closestRectanglePoint = new Vector2f(clamp(b.position.x, a.min.x, a.max.x), clamp(b.position.y, a.min.y, a.max.y));
		return intersect(b, new Point2D(closestRectanglePoint),  manifold);
	}
	
	public static boolean intersect(final Line2D a, final Point2D b, Manifold2D manifold)
	{
		final float maxDelta = 0.003f;		//An higher value means lower accuracy, but if it's too low, it won't work properly due to precision lost

		if(a.length + maxDelta < b.position.distance(a.min) + b.position.distance(a.max))
			return false;
		
		if(manifold != null)
		{
			manifold.normal = new Vector2f(b.position);
			manifold.penetration = 0f;
		}
		
		return true;
	}
	public static boolean intersect(final Line2D a, final Circle2D b, Manifold2D manifold)
	{
		final float dot = (((b.position.x - a.min.x) * (a.max.x - a.min.x)) + ((b.position.y - a.min.y) * (a.max.y - a.min.y))) / (float) Math.pow(a.length, 2);
		final float closestX = a.min.x + (dot * (a.max.x - a.min.x));
		final float closestY = a.min.y + (dot * (a.max.y - a.min.y));
		final Point2D closestPoint = new Point2D(new Vector2f(closestX, closestY));

		if(!inRange(closestPoint.position.x, a.min.x, a.max.x) || !inRange(closestPoint.position.y, a.min.y, a.max.y))
		{
			return intersect(new Point2D(a.min), b, manifold) || intersect(new Point2D(a.max), b, manifold);	//If the circle touches the endpoints of the line
		}

		return intersect(b, closestPoint, manifold);
	}
	
	//HELPER FUNCTIONS
	private static boolean inRange(final float value, final float min, final float max)
	{
		return value > Math.min(min, max) && value < Math.max(min, max);
	}
	private static boolean rangeIntersect(final float a0, final float b0, final float a1, final float b1)
	{
		return Math.max(a0, b0) > Math.min(a1, b1) &&
			   Math.min(a0, b0) < Math.max(a1, b1);
	}
	private static float clamp(final float value, final float min, final float max) 
	{
	    return Math.max(min, Math.min(max, value));
	}

}
