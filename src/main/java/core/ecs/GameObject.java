package core.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import core.destroyableReference.DestroyableReference;
import core.destroyableReference.DestroyableReferenceUtils;

public final class GameObject implements DestroyableReference<GameObject>
{
	public Scene scene;
	public ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	
	private GameObject parent = null;
	private List<GameObject> children = new ArrayList<GameObject>();
	
	public GameObject() {}
	public GameObject(final Scene scene)
	{
		moveToScene(scene);
	}
	
	public final <T extends Behaviour> T addBehaviour(Class<T> behaviourClass)
	{
		RequireBehaviour requireBehaviour = behaviourClass.getAnnotation(RequireBehaviour.class);
		if(requireBehaviour != null)
		{
			for(Class<? extends Behaviour> requiredBehaviourClass : requireBehaviour.behaviours())
			{
				if(!hasBehaviour(requiredBehaviourClass))
					addBehaviour(requiredBehaviourClass);
			}
		}
		
		Class<?>[] parameterTypes = new Class[]{ };		
		T behaviour = null;
		try
		{
			behaviour = behaviourClass.getConstructor(parameterTypes).newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		
		behaviour.gameObject = this;
		this.behaviours.add(behaviour);
		
		behaviour.start();
		return behaviour;
	}
	
	//Check if the gameobject has a behaviour of the type T
	public final <T extends Behaviour> boolean hasBehaviour(final Class<T> behaviourClass)
	{
		for(Behaviour behaviour : this.behaviours)
		{
			if(behaviourClass.isAssignableFrom(behaviour.getClass()))
			{
				return true;
			}
		}
		
		return false;
	}

	//Get only the first behaviour matching the type (if it doesn't exist returns null)
	public final <T extends Behaviour> T getBehaviour(final Class<T> behaviourClass)
	{
		for(Behaviour behaviour : this.behaviours)
		{
			if(behaviourClass.isAssignableFrom(behaviour.getClass()))
			{
				return behaviourClass.cast(behaviour);
			}
		}
		
		return null;
	}
	
	//Get all the behaviours matching the type
	public final <T extends Behaviour> ArrayList<T> getBehaviours(final Class<T> behaviourClass)
	{
		ArrayList<T> matchingBehaviours = new ArrayList<T>();
		
		for(Behaviour behaviour : this.behaviours)
		{
			if(behaviourClass.isAssignableFrom(behaviour.getClass()))
			{
				matchingBehaviours.add(behaviourClass.cast(behaviour));
			}
		}
		
		return matchingBehaviours;
	}

	public final void moveToScene(Scene scene)
	{
		if(this.scene != null)
			this.scene.removeGameObject(this);
		
		this.scene = scene;
		
		this.scene.addGameObject(this);
	}

	public final GameObject getParent()
	{
		return this.parent;
	}
	public final void setParent(final GameObject gameObject)
	{
		this.parent = gameObject;
	}
	public final void addChild(final GameObject gameObject)
	{
		this.children.add(gameObject);
	}
	public final void addChild(final GameObject gameObject, final int index)
	{
		this.children.add(index, gameObject);
	}
	public final List<GameObject> getChildren()
	{
		return this.children;
	}
	
	public final void updateDestroyableReferences()
	{
		for(int i = 0; i < this.behaviours.size(); i++)
		{
			if(DestroyableReferenceUtils.checkDestroyable(behaviours.get(i)) == null)
				behaviours.remove(i);
		}
	}
	
	@Override
	public final void destroy()
	{
		for(Behaviour behaviour : this.behaviours)
		{
			behaviour.destroy();
		}
		
		this.scene.removeGameObject(this);
		this.scene = null;
	}

	@Override
	public final GameObject checkDestroyable()
	{
		if(this.scene == null)
			return null;
		return this;
	}
}
