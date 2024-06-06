package gui.behaviours;

import core.destroyableReference.DestroyableReferenceUtils;
import core.ecs.Behaviour;
import core.ecs.RequireBehaviour;
import gui.behaviours.transform.TransformGUI;

@RequireBehaviour(behaviours = TransformGUI.class)
public class CanvasGUI extends Behaviour
{
	private TransformGUI transformGUI = null;
	
	@Override
	protected void onUpdate() 
	{
		this.transformGUI = (TransformGUI) DestroyableReferenceUtils.checkDestroyable(this.transformGUI);
	}
	
	public TransformGUI getTransformGUI()
	{
		if(this.transformGUI == null)
			this.transformGUI = this.gameObject.getBehaviour(TransformGUI.class);
		
		return this.transformGUI;
	}
}
