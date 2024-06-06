package core.renderer;

import core.renderer.objects.VertexArray;

public abstract class RendererAPI
{
	public abstract void setClearColor(final Color color);
	public abstract void clear();
	public abstract void update();
	public abstract void drawIndexed(final VertexArray vertexArray);
	public abstract void drawIndexed(final VertexArray vertexArray, final int count);
	
	public RendererAPI()
	{
		this.configureAPI();
	}
	
	protected abstract void configureAPI();
	
	private static RendererAPI selectedAPI = null;
	public static RendererAPI getSelectedAPI()
	{
		return RendererAPI.selectedAPI;
	}
	public static void selectAPI(final RendererAPI rendererAPI)
	{
		RendererAPI.selectedAPI = rendererAPI;
	}
}
