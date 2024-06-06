package core2D;

import org.joml.Vector2f;

import core.ecs.Behaviour;
import core.ecs.GameObject;

public class Transform2D extends Behaviour
{
	private Vector2f position = new Vector2f(0f, 0f);
	private Vector2f scale = new Vector2f(1f, 1f);
	private float rotation = 0f;

	public Transform2D() {}
	
	public Transform2D(final Vector2f position)
	{
		this.position = position;
	}
	
	public Transform2D(final Vector2f position, final Vector2f scale)
	{
		this.position = position;
		this.scale = scale;
	}
	
	public Transform2D(final Vector2f position, final Vector2f scale, final float rotation)
	{
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}
	
	public Vector2f getLocalPosition()
	{
		return this.position;
	}
	public float getLocalRotation()
	{
		return this.rotation;
	}
	public Vector2f getLocalScale()
	{
		return this.scale;
	}
	public void setLocalPosition(final Vector2f position)
	{
		this.position = position;
	}
	public void setLocalRotation(final float rotation)
	{
		this.rotation = rotation;
	}
	public void setLocalScale(final Vector2f scale)
	{
		this.scale = scale;
	}
	
	public Vector2f getPosition()
	{
		GameObject parent = this.gameObject.getParent();
		if(parent == null)
			return this.position;
		else
			return parent.getBehaviour(Transform2D.class).getPosition().add(this.position);
	}	
	public float getRotation()
	{
		GameObject parent = this.gameObject.getParent();
		if(parent == null)
			return this.rotation;
		else
			return parent.getBehaviour(Transform2D.class).getRotation() + this.rotation;
	}	
	public Vector2f getScale()
	{
		GameObject parent = this.gameObject.getParent();
		if(parent == null)
			return this.scale;
		else
			return parent.getBehaviour(Transform2D.class).getScale().mul(this.scale);
	}
	
	//A utility function that gets the position, the rotation (returned) and the scale of a gameObject if it has a transform2D, otherwise it gives default values (position = 0, 0 - rotation = 0 - scale = 1, 1)
	public static float getValues(final GameObject gameObject, Vector2f position, Vector2f scale)
	{		
		Transform2D transform = gameObject.getBehaviour(Transform2D.class);
		if(transform != null)
		{
			Vector2f globalPosition = transform.getPosition();
			float globalRotation = transform.getRotation();
			Vector2f globalScale = transform.getScale();
			
			position.x = globalPosition.x;
			position.y = globalPosition.y;
			scale.x = globalScale.x;
			scale.y = globalScale.y;
			return globalRotation;
		}
		else
		{
			position.x = 0f;
			position.y = 0f;
			scale.x = 1f;
			scale.y = 1f;
			return 0f;
		}
	}
	
	//A utility function that gets the position of a gameObject if it has a transform2D, otherwise it gives default values (position = 0, 0)
	public static Vector2f getValues(final GameObject gameObject)
	{		
		Transform2D transform = gameObject.getBehaviour(Transform2D.class);
		if(transform != null)
		{
			return transform.getPosition();
		}
		else
		{
			return new Vector2f(0f, 0f);
		}
	}

	@Override
	protected void onUpdate() 
	{
		
	}
}
