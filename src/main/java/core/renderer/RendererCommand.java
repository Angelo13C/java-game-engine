package core.renderer;

import core.renderer.objects.VertexArray;

public final class RendererCommand
{
	private RendererCommand() {}
	
	public static final void setClearColor(Color color)
	{
		RendererAPI.getSelectedAPI().setClearColor(color);
	}
	
	public static final void clear()
	{
		RendererAPI.getSelectedAPI().clear();
	}
	
	public static final void update()
	{
		RendererAPI.getSelectedAPI().update();
	}
	
	public static final void drawIndexed(final VertexArray vertexArray)
	{
		RendererAPI.getSelectedAPI().drawIndexed(vertexArray);
	}
	
	public static final void drawIndexed(final VertexArray vertexArray, final int count)
	{
		RendererAPI.getSelectedAPI().drawIndexed(vertexArray, count);
	}
}
