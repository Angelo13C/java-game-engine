package core.ecs;

import core.destroyableReference.DestroyableReference;
import core.ecs.update.DisableAutoUpdate;
import core.ecs.update.Updatable;

public abstract class Behaviour implements DestroyableReference<Behaviour>, Updatable
{
	public GameObject gameObject;
	
	public boolean enabled = true;
	
	public Behaviour()
	{
		if(!this.getClass().isAnnotationPresent(DisableAutoUpdate.class))
		{
			SceneManager.addBehaviourToUpdate(this);
		}
	}

	public void start()
	{
		this.onStart();
	}
	
	@Override
	public void update()
	{
		if(this.enabled)
		{
			this.onUpdate();
		}
	}
	
	@Override
	public void destroy()
	{
		if(!this.getClass().isAnnotationPresent(DisableAutoUpdate.class))
		{
			SceneManager.removeBehaviourToUpdate(this);
		}
		this.onDestroy();
		this.gameObject = null;
	}
	
	@Override
	public Behaviour checkDestroyable()
	{
		if(this.gameObject == null)
			return null;
		return this;
	}

	protected void onStart() {}
	protected abstract void onUpdate();
	protected void onDestroy() {}
	
	protected final <T extends Behaviour> T addBehaviour(final Class<T> behaviourClass)
	{
		return this.gameObject.addBehaviour(behaviourClass);
	}	
	protected final <T extends Behaviour> T getBehaviour(final Class<T> behaviourClass)
	{
		return this.gameObject.getBehaviour(behaviourClass);
	}	
	protected final <T extends Behaviour> boolean hasBehaviour(final Class<T> behaviourClass)
	{
		return this.gameObject.hasBehaviour(behaviourClass);
	}
	
	public void setEnabled(final boolean value)
	{
		this.enabled = value;
	}	
	public boolean isEnabled()
	{
		return this.enabled;
	}
}
