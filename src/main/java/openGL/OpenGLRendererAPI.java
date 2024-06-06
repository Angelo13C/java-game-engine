package openGL;

import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector2i;
import org.lwjgl.opengl.GL12;

import core.Application;
import core.renderer.Color;
import core.renderer.RendererAPI;
import core.renderer.objects.IndexBuffer;
import core.renderer.objects.Shader;
import core.renderer.objects.VertexArray;
import core.renderer.objects.VertexBuffer;
import core.renderer.texture.Texture2D;
import openGL.objects.OpenGLIndexBuffer;
import openGL.objects.OpenGLShader;
import openGL.objects.OpenGLTexture2D;
import openGL.objects.OpenGLVertexArray;
import openGL.objects.OpenGLVertexBuffer;

public class OpenGLRendererAPI extends RendererAPI
{
	public OpenGLRendererAPI()
	{
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}
	
	@Override
	public void setClearColor(final Color color)
	{
		glClearColor(color.r, color.g, color.b, color.a);
	}

	@Override
	public void clear()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void update()
	{
		Vector2i size = Application.getWindow().getSize();
		GL12.glViewport(0, 0, size.x, size.y);
	}

	@Override
	protected void configureAPI() 
	{
		IndexBuffer.selectAPI(OpenGLIndexBuffer.class);
		VertexArray.selectAPI(OpenGLVertexArray.class);
		VertexBuffer.selectAPI(OpenGLVertexBuffer.class);
		Shader.selectAPI(OpenGLShader.class);
		Texture2D.selectAPI(OpenGLTexture2D.class);	
	}

	@Override
	public void drawIndexed(final VertexArray vertexArray)
	{
		vertexArray.bind();
		glDrawElements(GL_TRIANGLES, vertexArray.getIndexBuffer().getCount(), GL_UNSIGNED_INT, 0);
	}

	@Override
	public void drawIndexed(final VertexArray vertexArray, final int count)
	{
		vertexArray.bind();
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
	}
}
