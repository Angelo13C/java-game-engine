package gui.behaviours.transform.constraints;

import gui.behaviours.transform.TransformGUI;

public class PixelConstraintGUI extends ConstraintGUI
{
	@Override
	public int getValue() 
	{
		TransformGUI parentTransform = this.transformGUI.gameObject.getParent().getBehaviour(TransformGUI.class);
		switch(this.constraintType)
		{
		case X: 	return (parentTransform.getPosition().x - parentTransform.getSize().x) + this.transformGUI.getWidthConstraint().getValue() + (int) this.relativeValue;
		case Y: 	return (parentTransform.getPosition().y - parentTransform.getSize().y) + this.transformGUI.getHeightConstraint().getValue() + (int) this.relativeValue;
		case WIDTH:	return (int) this.relativeValue;
		case HEIGHT:return (int) this.relativeValue;
		}
		
		return 0;
	}
}
