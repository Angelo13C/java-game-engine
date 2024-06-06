package gui.behaviours.transform.constraints;

import org.joml.Vector2i;

import core.Application;

public class WindowConstraintGUI extends ConstraintGUI
{
	@Override
	public int getValue() 
	{
		Vector2i windowSize = Application.getWindow().getSize();
		switch(this.constraintType)
		{
		case X: 	return windowSize.x / 2;
		case Y: 	return windowSize.y / 2;
		case WIDTH:	return windowSize.x / 2;
		case HEIGHT:return windowSize.y / 2;
		}
		
		return 0;
	}
}
