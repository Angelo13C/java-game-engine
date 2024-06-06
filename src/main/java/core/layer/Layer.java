package core.layer;

import java.util.ArrayList;
import java.util.List;

public class Layer<T>
{
	public List<T> objects = new ArrayList<T>();
	
	public void addObject(T object)
	{
		this.objects.add(object);
	}
	
	public List<T> getObjects()
	{
		return this.objects;
	}
}
