package core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashSet;

public class Graph<T>
{
	private List<T> nodes = new ArrayList<T>();
	
	private Map<T, ArrayList<T>> dependencies = new HashMap<>();
	
	public void addNode(final T node)
	{
		if(nodes.contains(node))
			return;
		
		this.nodes.add(node);
		this.dependencies.put(node, new ArrayList<T>());
	}
	
	public void addDependency(final T node, final T dependsOn)
	{				
		//If this dependency already exists
		if(nodeDependsOn(node, dependsOn))
			return;
		
		this.dependencies.get(node).add(dependsOn);
	}
	
	public boolean nodeDependsOn(final T node, final T dependsOn)
	{
		return this.dependencies.get(node).contains(dependsOn);
	}
	
	public List<T> getNodes()
	{
		return nodes;
	}
	
	public void topologicalSort()
	{	
		List<T> sorted = new ArrayList<T>(this.nodes.size());
		HashSet<T> visited = new HashSet<T>();
	
	    for(T node : this.nodes)
	    {
			try {
				visit(node, visited, sorted);
			} catch (CycleLoopException e) {
				e.printStackTrace();
				System.exit(-1);
			}
	    }
	
	    for(int i = 0; i < sorted.size(); i++)
	    {
	    	this.nodes.set(i, sorted.get(i));
	    }
	}

	private void visit(T node, HashSet<T> visited, List<T> sorted) throws CycleLoopException
	{
	    if(!visited.contains(node))
	    {
	        visited.add(node);

	        for(T dependency : dependencies.get(node))
	        {
	        	visit(dependency, visited, sorted);
	        }

	        sorted.add(node);
	    }
	    else
	    {
	        if(!sorted.contains(node))
				throw new CycleLoopException("cycle loop found!");
	    }
	}
	
	public boolean contains(T node)
	{
		return this.nodes.contains(node);
	}
}