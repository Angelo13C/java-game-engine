package core.ecs.update;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import core.ecs.Behaviour;
import core.util.Graph;

public class UpdateManager
{
	private UpdateManager() {}
	
	private static Graph<Class<? extends Updatable>> updateGroupsOrder = new Graph<Class<? extends Updatable>>();
	private static boolean isDirty = false;		//The graph will be sorted when dirty is true
	private static Map<Class<? extends Updatable>, UpdateGroup> updateGroups = new HashMap<Class<? extends Updatable>, UpdateGroup>();
	
	public static void update()
	{
		if(UpdateManager.isDirty)
		{
			UpdateManager.isDirty = false;
			UpdateManager.updateGroupsOrder.topologicalSort();
		}
		
		//Update all the groups in order
		for(Class<? extends Updatable> updatableGroupOrder : UpdateManager.updateGroupsOrder.getNodes())
		{
			UpdateGroup updateGroup = UpdateManager.updateGroups.get(updatableGroupOrder);

			//Due to dependencies, there might be a an updateGroup that hasn't been created but that is in the group order
			if(updateGroup != null)
				updateGroup.update();
		}
	}

	public static void addBehaviour(final Behaviour behaviour)
	{
		UpdateInGroup updateInGroup = behaviour.getClass().getAnnotation(UpdateInGroup.class);
		Class<? extends UpdateGroup> updateGroupClass = null;
		if(updateInGroup != null)
			updateGroupClass = updateInGroup.group();
		else
			updateGroupClass = GroupDefaultUpdate.class;

		UpdateGroup updateGroup = null;
		if(!UpdateManager.updateGroupsOrder.contains(updateGroupClass))
		{
			UpdateManager.isDirty = true;
			UpdateManager.updateGroupsOrder = updateDependencies(UpdateManager.updateGroupsOrder, updateGroupClass);

			//Create the new update group and add it to the list
			Class<?>[] parametersType = new Class[]{ };			
			try {
				updateGroup = updateGroupClass.getConstructor(parametersType).newInstance();
				updateGroups.put(updateGroupClass, updateGroup);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		else
			updateGroup = UpdateManager.updateGroups.get(updateGroupClass);

		//Due to dependencies, there might be a an updateGroup that hasn't been created but that is in the group order
		if(updateGroup != null)
			updateGroup.addBehaviour(behaviour);
	}
	
	public static Graph<Class<? extends Updatable>> updateDependencies(Graph<Class<? extends Updatable>> updatableGraph, Class<? extends Updatable> newUpdatable)
	{
		updatableGraph.addNode(newUpdatable);
		
		UpdateAfter updateAfter = newUpdatable.getAnnotation(UpdateAfter.class);
		if(updateAfter != null)
		{
			for(Class<? extends Updatable> updateAfterClass : updateAfter.than())
				updatableGraph.addDependency(newUpdatable, updateAfterClass);
		}
		UpdateBefore updateBefore = newUpdatable.getAnnotation(UpdateBefore.class);
		if(updateBefore != null)
		{
			for(Class<? extends Updatable> updateBeforeClass : updateBefore.than())
			{
				if(!updatableGraph.contains(updateBeforeClass))
					updatableGraph = updateDependencies(updatableGraph, updateBeforeClass);
				
				updatableGraph.addDependency(updateBeforeClass, newUpdatable);
			}
		}
		
		return updatableGraph;
	}
	
	/* THIS WAS THE OLD WAY TO ADD ALL THE DEPENDENCIES IN AN UPDATABLE GRAPH IN 1 CALL
	 * public static Graph<Class<? extends Updatable>> updateDependenciesOld(Graph<Class<? extends Updatable>> updatableGraph)
	{
		List<Class<? extends Updatable>> updatablesChecked = new ArrayList<Class<? extends Updatable>>();
		Stack<Class<? extends Updatable>> updatablesToCheck = new Stack<Class<? extends Updatable>>();
		
		for(Class<? extends Updatable> updatable : updatableGraph.getNodes())
		{
			updatablesToCheck.push(updatable);
			updatablesChecked.add(updatable);
		}
		
		//Check all his dependencies
		while(updatablesToCheck.size() > 0)
		{
			Class<? extends Updatable> updatableToCheck = updatablesToCheck.pop();
			
			UpdateAfter updateAfter = updatableToCheck.getAnnotation(UpdateAfter.class);
			if(updateAfter != null)
			{
				for(Class<? extends Updatable> updateAfterClass : updateAfter.than())
				{
					if(!updatableGraph.contains(updateAfterClass))
						updatableGraph.addNode(updateAfterClass);

					updatableGraph.addDependency(updatableToCheck, updateAfterClass);
					
					if(!updatablesChecked.contains(updateAfterClass))
					{
						updatablesChecked.add(updateAfterClass);
						updatablesToCheck.push(updateAfterClass);
					}
				}
			}

			UpdateBefore updateBefore = updatableToCheck.getAnnotation(UpdateBefore.class);
			if(updateBefore != null)
			{
				for(Class<? extends Updatable> updateBeforeClass : updateBefore.than())
				{
					if(!updatableGraph.contains(updateBeforeClass))
						updatableGraph.addNode(updateBeforeClass);

					updatableGraph.addDependency(updateBeforeClass, updatableToCheck);
					
					if(!updatablesChecked.contains(updateBeforeClass))
					{
						updatablesChecked.add(updateBeforeClass);
						updatablesToCheck.push(updateBeforeClass);
					}
				}
			}
		}
		
		return updatableGraph;
	}*/
}
