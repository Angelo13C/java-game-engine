package sandbox;

import org.joml.Vector2f;

import core.Application;
import core.ecs.Behaviour;
import core.input.InputKeyboard.KeyCode;
import core.time.Time;
import core2D.Transform2D;

public class InputMover extends Behaviour
{
	private float speed;
	
	public void setSpeed(final float speed)
	{
		this.speed = speed;
	}

	@Override
	protected void onUpdate() 
	{
		Transform2D transform = this.gameObject.getBehaviour(Transform2D.class);
		if(transform != null)
		{
			Vector2f direction = new Vector2f(0f, 0f);
			direction.y += Application.getKeyboard().isKeyPressed(KeyCode.W) ? 1 : 0;
			direction.y -= Application.getKeyboard().isKeyPressed(KeyCode.S) ? 1 : 0;
			direction.x += Application.getKeyboard().isKeyPressed(KeyCode.D) ? 1 : 0;
			direction.x -= Application.getKeyboard().isKeyPressed(KeyCode.A) ? 1 : 0;
			
			if(!direction.equals(new Vector2f(0f, 0f)))
			{
				direction.normalize();
				Vector2f position = transform.getLocalPosition();
				transform.setLocalPosition(position.add(direction.mul(this.speed * (float) Time.getDeltaTime())));
			}
		}
	}
}
