package gui.behaviours.transform.constraints;

import gui.behaviours.transform.TransformGUI;

public class CenterConstraintGUI extends ConstraintGUI
{
	@Override
	public int getValue() 
	{
		TransformGUI parentTransform = this.transformGUI.gameObject.getParent().getBehaviour(TransformGUI.class);
		switch(this.constraintType)
		{
		case X: 	return parentTransform.getPosition().x;
		case Y: 	return parentTransform.getPosition().y;
		case WIDTH:
		case HEIGHT:
		}
		
		return 0;
	}
}
