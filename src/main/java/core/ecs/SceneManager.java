package core.ecs;

import java.util.ArrayList;

import core.ecs.update.UpdateManager;

public final class SceneManager
{
	private SceneManager() {}
	
	private static ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	private static ArrayList<Scene> scenes = new ArrayList<Scene>();
	
	public static final void update()
	{
		//Update all the groups/behaviours
		UpdateManager.update();
		
		/*for(int i = 0; i < SceneManager.behaviours.size(); i++)
		{
			if(behaviours.get(i).enabled)
			{
				behaviours.get(i).update();
			}
		}*/
		for(int i = 0; i < SceneManager.scenes.size(); i++)
		{
			if(scenes.get(i).enabled)
			{
				scenes.get(i).updateDestroyableReferences();
			}
		}
	}
	
	public static final void addScene(final Scene scene)
	{
		SceneManager.scenes.add(scene);
	}
	
	public static final void addBehaviourToUpdate(final Behaviour behaviour)
	{
		UpdateManager.addBehaviour(behaviour);
			
		int indexPriority = 0;
		//Get the correct position based on the priority
		//while(indexPriority < SceneManager.behaviours.size() && SceneManager.behaviours.get(indexPriority).priority > behaviour.priority)
			//indexPriority++;
		
		SceneManager.behaviours.add(indexPriority, behaviour);
	}
	
	public static final void removeBehaviourToUpdate(final Behaviour behaviour)
	{
		SceneManager.behaviours.remove(behaviour);
	}
}
