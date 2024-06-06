package core.ecs;

import java.util.ArrayList;

public class Scene
{
	public String name;
	public boolean enabled = true;
	
	private ArrayList<GameObject> gameObjects = new ArrayList<GameObject>();
	
	public Scene(final String name)
	{
		this.name = name;
	}
	
	public void addGameObject(final GameObject gameObject)
	{
		this.gameObjects.add(gameObject);
	}	
	public void removeGameObject(final GameObject gameObject)
	{
		this.gameObjects.remove(gameObject);
	}

	public ArrayList<GameObject> getGameObjects()
	{
		return this.gameObjects;
	}
	
	public void updateDestroyableReferences()
	{
		for(int i = 0; i < gameObjects.size(); i++)
		{
			gameObjects.get(i).updateDestroyableReferences();
		}
	}
}
