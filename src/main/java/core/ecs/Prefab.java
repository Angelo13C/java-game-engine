package core.ecs;

import java.util.ArrayList;

public final class Prefab
{
	private ArrayList<Behaviour> behaviours;
	
	public Prefab(ArrayList<Behaviour> behaviours)
	{
		this.behaviours = behaviours;
	}

	public GameObject instantiate(final Scene scene)
	{
		GameObject gameObject = new GameObject();
		scene.addGameObject(gameObject);
		
		/*for(Behaviour behaviour : this.behaviours)
			gameObject.addBehaviour(behaviour);*/
		
		return gameObject;
	}
}
