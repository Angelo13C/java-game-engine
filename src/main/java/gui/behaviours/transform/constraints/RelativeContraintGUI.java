package gui.behaviours.transform.constraints;

import gui.behaviours.transform.TransformGUI;

public class RelativeContraintGUI extends ConstraintGUI
{
	@Override
	public int getValue() 
	{
		TransformGUI parentTransform = this.transformGUI.gameObject.getParent().getBehaviour(TransformGUI.class);
		switch(this.constraintType)
		{
		case X: 	return (int) (parentTransform.getPosition().x * this.relativeValue);
		case Y: 	return (int) (parentTransform.getPosition().y * this.relativeValue);
		case WIDTH:	return (int) (parentTransform.getSize().x * this.relativeValue);
		case HEIGHT:return (int) (parentTransform.getSize().y * this.relativeValue);
		}
		
		return 0;
	}
}
