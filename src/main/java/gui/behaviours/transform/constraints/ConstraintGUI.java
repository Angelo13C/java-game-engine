package gui.behaviours.transform.constraints;

import gui.behaviours.transform.TransformGUI;

public abstract class ConstraintGUI 
{
	protected float relativeValue = 0f;
	
	public enum ConstrainType
	{
		X, Y, WIDTH, HEIGHT
	}
	protected ConstrainType constraintType;
	protected TransformGUI transformGUI;
	
	public void initialize(final ConstrainType contraintType, final TransformGUI transformGUI)
	{
		this.constraintType = contraintType;
		this.transformGUI = transformGUI;
	}
	
	public ConstraintGUI setRelativeValue(final float relativeValue)
	{
		this.relativeValue = relativeValue;
		return this;
	}
	
	public abstract int getValue();
}
