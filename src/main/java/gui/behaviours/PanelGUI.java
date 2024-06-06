package gui.behaviours;

import org.joml.Vector2f;

import core.ecs.Behaviour;
import core.ecs.RequireBehaviour;
import core.ecs.update.DisableAutoUpdate;
import core.renderer.Color;
import gui.RendererGUI;
import gui.behaviours.transform.TransformGUI;

@DisableAutoUpdate
@RequireBehaviour(behaviours = TransformGUI.class)
public class PanelGUI extends Behaviour
{
	private Color color = Color.WHITE;
	
	public Color getColor() 
	{
		return color;
	}
	public void setColor(final Color color) 
	{
		this.color = color;
	}
	
	@Override
	protected void onUpdate() 
	{
/*		final TransformGUI transformGUI = this.gameObject.getBehaviour(TransformGUI.class);
		final Vector2f position = camera2D.screenToWorldPosition(transformGUI.getPosition());
		final float rotation = transformGUI.getRotation();
		final Vector2f size = transformGUI.getSize();
		
		RendererGUI.drawQuad(position, size, rotation, color);*/
	}
}
