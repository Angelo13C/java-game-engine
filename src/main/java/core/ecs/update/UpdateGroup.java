package core.ecs.update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.ecs.Behaviour;
import core.util.Graph;

public abstract class UpdateGroup implements Updatable
{
	private Graph<Class<? extends Updatable>> behavioursOrder = new Graph<Class<? extends Updatable>>();
	private boolean isDirty = false;
	private Map<Class<? extends Updatable>, ArrayList<Behaviour>> behaviours = new HashMap<Class<? extends Updatable>, ArrayList<Behaviour>>();
	
	public UpdateGroup() {}
	
	@Override
	public void update()
	{
		if(this.isDirty)
		{
			this.isDirty = false;		
			this.behavioursOrder.topologicalSort();
		}
		
		//Update all the behaviours in order
		for(Class<? extends Updatable> behaviourOrder : this.behavioursOrder.getNodes())
		{
			ArrayList<Behaviour> behaviours = this.behaviours.get(behaviourOrder);
			if(behaviours != null)
			{
				for(Behaviour behaviour : behaviours)
				{
					if(behaviour.enabled)
						behaviour.update();
				}
			}
		}
	}
	
	public void addBehaviour(final Behaviour behaviour)
	{
		final Class<? extends Updatable> behaviourClass = behaviour.getClass();
		
		if(!this.behavioursOrder.contains(behaviour.getClass()))
		{
			this.isDirty = true;
			this.behavioursOrder = UpdateManager.updateDependencies(this.behavioursOrder, behaviourClass);
			
			this.behaviours.put(behaviourClass, new ArrayList<Behaviour>());
		}
		//If the behaviour was already added to the ordered graph because of dependencies but it hasn't been created yet
		else if(this.behaviours.get(behaviourClass) == null)
			this.behaviours.put(behaviourClass, new ArrayList<Behaviour>());
			
		this.behaviours.get(behaviourClass).add(behaviour);
	}
}
