package gui.behaviours.transform;

import org.joml.Vector2i;

import core.ecs.Behaviour;
import gui.behaviours.transform.constraints.ConstraintGUI;
import gui.behaviours.transform.constraints.ConstraintGUI.ConstrainType;

public class TransformGUI extends Behaviour
{
	private Vector2i position = new Vector2i(0, 0);
	private Vector2i size = new Vector2i(0, 0);
	
	private ConstraintGUI x;
	private ConstraintGUI y;
	private ConstraintGUI width;
	private ConstraintGUI height;

	@Override
	protected void onUpdate() 
	{
		this.position.x = this.x.getValue();
		this.position.y = this.y.getValue();
		this.size.x = this.width.getValue();
		this.size.y = this.height.getValue();
	}

	public ConstraintGUI getXConstraint()
	{
		return this.x;
	}
	public ConstraintGUI getYConstraint()
	{
		return this.y;
	}
	public ConstraintGUI getWidthConstraint()
	{
		return this.width;
	}
	public ConstraintGUI getHeightConstraint()
	{
		return this.height;
	}
	public void setXConstraint(final ConstraintGUI x)
	{
		this.x = x;
		this.x.initialize(ConstrainType.X, this);
	}
	public void setYConstraint(final ConstraintGUI y)
	{
		this.y = y;
		this.y.initialize(ConstrainType.Y, this);
	}
	public void setWidthConstraint(final ConstraintGUI width)
	{
		this.width = width;
		this.width.initialize(ConstrainType.WIDTH, this);
	}
	public void setHeightConstraint(final ConstraintGUI height)
	{
		this.height = height;
		this.height.initialize(ConstrainType.HEIGHT, this);
	}
	
	public Vector2i getPosition()
	{		
		return this.position;
	}	
	public Vector2i getSize()
	{
		return this.size;
	}
}
