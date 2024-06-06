package gui;

import java.nio.ByteBuffer;

import org.joml.Vector2f;

import core.renderer.Color;
import core.renderer.RendererCommand;
import core.renderer.objects.BufferElement;
import core.renderer.objects.BufferLayout;
import core.renderer.objects.IndexBuffer;
import core.renderer.objects.Shader;
import core.renderer.objects.Shader.ShaderDataType;
import core.renderer.objects.VertexArray;
import core.renderer.objects.VertexBuffer;
import core.renderer.texture.Sprite;
import core.renderer.texture.Texture2D;
import openGL.objects.OpenGLTexture2D;

public final class RendererGUI
{
	private RendererGUI() {}

	private static final int MAX_QUAD_PER_DRAW = 1500;
	private static final int MAX_VERTICES_PER_DRAW = MAX_QUAD_PER_DRAW * 4;
	private static final int MAX_INDICES_PER_DRAW = MAX_QUAD_PER_DRAW * 6;
	private static final int MAX_TEXTURE_SLOTS = 8;
	
	public static Texture2D whiteTexture;
	private static Shader shader;
	
	private static VertexArray quadVertexArray;
	
	private static float[] quadVertices;
	private static int quadVertexIndex = 0;
	private static int vertexFloatCount = 0;	
	private static Texture2D[] textureSlots;
	private static int textureSlotIndex = 1; 	//The slot 0 is the white texture
	
	public static final Vector2f[] QUAD_TEXTURE_COORDS = new Vector2f[]
			  { new Vector2f(0f, 0f),
				new Vector2f(1f, 0f),
				new Vector2f(1f, 1f),
				new Vector2f(0f, 1f) };
	
	public static void initialize()
	{				
		RendererGUI.quadVertexArray = VertexArray.create();
		
		BufferLayout layout = new BufferLayout(new BufferElement[] {
				new BufferElement(ShaderDataType.FLOAT2, "a_Position"),
				new BufferElement(ShaderDataType.FLOAT4, "a_Color"),
				new BufferElement(ShaderDataType.FLOAT2, "a_TexCoord"),
				new BufferElement(ShaderDataType.FLOAT, "a_TexIndex"),
				new BufferElement(ShaderDataType.FLOAT, "a_CornersRadius")
		});
		
		final int vertexSize = layout.getStride();	
		RendererGUI.vertexFloatCount = 0;
		for(BufferElement element : layout.getElements())
		{
			vertexFloatCount += ShaderDataType.getComponentCount(element.type);
		}

		//All the quad vertices to draw
		quadVertices = new float[RendererGUI.MAX_VERTICES_PER_DRAW * vertexFloatCount];
		
		VertexBuffer quadVertexBuffer = VertexBuffer.create(RendererGUI.MAX_VERTICES_PER_DRAW * vertexSize);		
		quadVertexBuffer.setLayout(layout);
		
		RendererGUI.quadVertexArray.addVertexBuffer(quadVertexBuffer);

		int[] indices = new int[RendererGUI.MAX_INDICES_PER_DRAW];
		//Calculate the indices of the quads
		int offset = 0;
		for(int i = 0; i < indices.length; i += 6)
		{
			indices[i + 0] = offset + 0;
			indices[i + 1] = offset + 1;
			indices[i + 2] = offset + 2;
			
			indices[i + 3] = offset + 2;
			indices[i + 4] = offset + 3;
			indices[i + 5] = offset + 0;
			
			offset += 4;
		}
		IndexBuffer indexBuffer = IndexBuffer.create(indices);
		quadVertexArray.setIndexBuffer(indexBuffer);
		
		RendererGUI.whiteTexture = Texture2D.create(1, 1);
		ByteBuffer whiteTextureData = ByteBuffer.allocateDirect(4).putInt(0xffffffff).position(0);
		RendererGUI.whiteTexture.setData(whiteTextureData);
		
		int []samplers = new int[RendererGUI.MAX_TEXTURE_SLOTS];
		for(int i = 0; i < RendererGUI.MAX_TEXTURE_SLOTS; i++)
			samplers[i] = i;
		
		RendererGUI.shader = Shader.create("assets/shaders/GUIDefault.glsl");
		RendererGUI.shader.bind();
		RendererGUI.shader.setIntArray("u_Textures", samplers);
		
		RendererGUI.textureSlots = new Texture2D[RendererGUI.MAX_TEXTURE_SLOTS];
		RendererGUI.textureSlots[0] = RendererGUI.whiteTexture;
	}
	
	public static void startScene()
	{
		RendererGUI.quadVertexIndex = 0;
		RendererGUI.textureSlotIndex = 1;
	}	

	public static void endScene()
	{		
		flush();
	}
	
	private static void flush()
	{		
		RendererGUI.quadVertexArray.getVertexBuffers().get(0).setData(RendererGUI.quadVertices);
		
		RendererGUI.shader.bind();
		
		for(int i = 0; i < RendererGUI.textureSlotIndex; i++)
		{
			RendererGUI.textureSlots[i].bind(i);
		}
			
		int indicesCount = (int) (RendererGUI.quadVertexIndex * 1.5f);
		RendererCommand.drawIndexed(RendererGUI.quadVertexArray, indicesCount);
		
		RendererGUI.quadVertexIndex = 0;
		RendererGUI.textureSlotIndex = 1;
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final Color color)
	{
		drawQuad(position, size, RendererGUI.whiteTexture, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float cornersRadius, final Color color)
	{
		drawQuad(position, size, cornersRadius, RendererGUI.whiteTexture, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final Texture2D texture, final Color color)
	{
		drawQuad(position, size, 0f, texture, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float cornersRadius, final Texture2D texture, final Color color)
	{
		drawQuad(position, size, cornersRadius, texture, RendererGUI.QUAD_TEXTURE_COORDS, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float cornersRadius, final Sprite sprite, final Color color)
	{
		drawQuad(position, size, cornersRadius, sprite.getTexture(), sprite.getTextureCoords(), color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float cornersRadius, final Texture2D texture, final Vector2f[] textureCoords, final Color color)
	{
		if(RendererGUI.quadVertexIndex >= RendererGUI.MAX_VERTICES_PER_DRAW)
			flush();

		Vector2f[] positions = new Vector2f[4];
		final float halfWidth = size.x / 2f;
		final float halfHeight = size.y / 2f;
		positions[0] = new Vector2f(position.x - halfWidth, position.y - halfHeight);
		positions[1] = new Vector2f(position.x + halfWidth, position.y - halfHeight);
		positions[2] = new Vector2f(position.x + halfWidth, position.y + halfHeight);
		positions[3] = new Vector2f(position.x - halfWidth, position.y + halfHeight);

		//Get the correct texture index
		int textureIndex = 0;
		for(int i = 1; i < RendererGUI.textureSlotIndex; i++)
		{
			if(((OpenGLTexture2D) RendererGUI.textureSlots[i]).getRendererID() == ((OpenGLTexture2D) texture).getRendererID())	//OpenGL SPECIFIC!!!)
			{
				textureIndex = i;
				break;
			}
		}
		if(textureIndex == 0)
		{
			if(RendererGUI.textureSlotIndex >= RendererGUI.MAX_TEXTURE_SLOTS)
				flush();
			textureIndex = RendererGUI.textureSlotIndex;
			RendererGUI.textureSlots[textureIndex] = texture;
			RendererGUI.textureSlotIndex++;
		}

		for(int i = 0; i < 4; i++)
			setQuadVertex(positions[i], color, textureCoords[i], textureIndex, cornersRadius);
	}
	
	//Set a vertex to the vertices to draw
	private static void setQuadVertex(final Vector2f position, final Color color, final Vector2f textureCoords, final int textureIndex, final float cornersRadius)
	{
		//6 is the number of floats being assigned in this function
		int currentVertex = RendererGUI.quadVertexIndex * RendererGUI.vertexFloatCount;

		RendererGUI.quadVertices[currentVertex + 0] = position.x;	//Position.x
		RendererGUI.quadVertices[currentVertex + 1] = position.y;	//Position.y
		RendererGUI.quadVertices[currentVertex + 2] = color.r;		//Color.r
		RendererGUI.quadVertices[currentVertex + 3] = color.g;		//Color.g
		RendererGUI.quadVertices[currentVertex + 4] = color.b;		//Color.b
		RendererGUI.quadVertices[currentVertex + 5] = color.a;		//Color.a
		RendererGUI.quadVertices[currentVertex + 6] = textureCoords.x;			//TexCoords.x
		RendererGUI.quadVertices[currentVertex + 7] = textureCoords.y;			//TexCoords.y
		RendererGUI.quadVertices[currentVertex + 8] = textureIndex;			//TexIndex
		RendererGUI.quadVertices[currentVertex + 9] = cornersRadius;			//Corners Radius
		RendererGUI.quadVertexIndex++;
	}	
}
