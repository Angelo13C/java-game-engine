package core2D.renderer2D;

import java.nio.ByteBuffer;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import core.renderer.Color;
import core.renderer.RendererCommand;
import core.renderer.objects.BufferElement;
import core.renderer.objects.BufferLayout;
import core.renderer.objects.IndexBuffer;
import core.renderer.objects.Shader;
import core.renderer.objects.VertexArray;
import core.renderer.objects.VertexBuffer;
import openGL.objects.OpenGLTexture2D;
import core.renderer.objects.Shader.ShaderDataType;
import core.renderer.texture.Sprite;
import core.renderer.texture.Texture2D;
import core2D.Camera2D;

public final class Renderer2D
{
	private Renderer2D() {}

	private static final int MAX_QUAD_PER_DRAW = 2500;
	private static final int MAX_VERTICES_PER_DRAW = MAX_QUAD_PER_DRAW * 4;
	private static final int MAX_INDICES_PER_DRAW = MAX_QUAD_PER_DRAW * 6;
	private static final int MAX_TEXTURE_SLOTS = 8;
	
	private static Camera2D camera;
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
	
	private static final Vector4f[] QUAD_VERTEX_POSITIONS = 
			{ new Vector4f(-.5f, -.5f, 0f, 1f),
			  new Vector4f( .5f, -.5f, 0f, 1f),
			  new Vector4f( .5f,  .5f, 0f, 1f),
			  new Vector4f(-.5f,  .5f, 0f, 1f) } ;
	
	public static void initialize()
	{				
		Renderer2D.quadVertexArray = VertexArray.create();
		
		BufferLayout layout = new BufferLayout(new BufferElement[] {
				new BufferElement(ShaderDataType.FLOAT2, "a_Position"),
				new BufferElement(ShaderDataType.FLOAT4, "a_Color"),
				new BufferElement(ShaderDataType.FLOAT2, "a_TexCoord"),
				new BufferElement(ShaderDataType.FLOAT, "a_TexIndex")
		});
		
		final int vertexSize = layout.getStride();	
		Renderer2D.vertexFloatCount = 0;
		for(BufferElement element : layout.getElements())
		{
			vertexFloatCount += ShaderDataType.getComponentCount(element.type);
		}

		//All the quad vertices to draw
		quadVertices = new float[Renderer2D.MAX_VERTICES_PER_DRAW * vertexFloatCount];
		
		VertexBuffer quadVertexBuffer = VertexBuffer.create(Renderer2D.MAX_VERTICES_PER_DRAW * vertexSize);		
		quadVertexBuffer.setLayout(layout);
		
		Renderer2D.quadVertexArray.addVertexBuffer(quadVertexBuffer);

		int[] indices = new int[Renderer2D.MAX_INDICES_PER_DRAW];
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
		
		Renderer2D.whiteTexture = Texture2D.create(1, 1);
		ByteBuffer whiteTextureData = ByteBuffer.allocateDirect(4).putInt(0xffffffff).position(0);
		Renderer2D.whiteTexture.setData(whiteTextureData);
		
		int []samplers = new int[Renderer2D.MAX_TEXTURE_SLOTS];
		for(int i = 0; i < Renderer2D.MAX_TEXTURE_SLOTS; i++)
			samplers[i] = i;
		
		Renderer2D.shader = Shader.create("assets/shaders/SpriteDefault.glsl");
		Renderer2D.shader.bind();
		Renderer2D.shader.setIntArray("u_Textures", samplers);
		
		Renderer2D.textureSlots = new Texture2D[Renderer2D.MAX_TEXTURE_SLOTS];
		Renderer2D.textureSlots[0] = Renderer2D.whiteTexture;
	}
	
	public static void startScene(final Camera2D camera)
	{
		Renderer2D.quadVertexIndex = 0;
		Renderer2D.textureSlotIndex = 1;
		
		Renderer2D.camera = camera;
	}
	public static void endScene()
	{		
		flush();
	}
	
	private static void flush()
	{
		Renderer2D.quadVertexArray.getVertexBuffers().get(0).setData(Renderer2D.quadVertices);
		
		Renderer2D.shader.bind();
		Renderer2D.shader.setMat4("u_ViewProjection", Renderer2D.camera.getViewProjectionMatrix());
		
		for(int i = 0; i < Renderer2D.textureSlotIndex; i++)
		{
			Renderer2D.textureSlots[i].bind(i);
		}
			
		int indicesCount = (int) (Renderer2D.quadVertexIndex * 1.5f);
		RendererCommand.drawIndexed(Renderer2D.quadVertexArray, indicesCount);
		
		Renderer2D.quadVertexIndex = 0;
		Renderer2D.textureSlotIndex = 1;
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final Color color)
	{
		drawQuad(position, size, Renderer2D.whiteTexture, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float rotationRadians, final Color color)
	{
		drawQuad(position, size, rotationRadians, Renderer2D.whiteTexture, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final Texture2D texture, final Color color)
	{
		drawQuad(position, size, 0f, texture, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float rotationRadians, final Texture2D texture, final Color color)
	{
		drawQuad(position, size, rotationRadians, texture, Renderer2D.QUAD_TEXTURE_COORDS, color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float rotationRadians, final Sprite sprite, final Color color)
	{
		drawQuad(position, size, rotationRadians, sprite.getTexture(), sprite.getTextureCoords(), color);
	}
	
	public static void drawQuad(final Vector2f position, final Vector2f size, final float rotationRadians, final Texture2D texture, final Vector2f[] textureCoords, final Color color)
	{
		if(Renderer2D.quadVertexIndex >= Renderer2D.MAX_VERTICES_PER_DRAW)
			flush();

		Vector2f[] positions = new Vector2f[4];
		if(rotationRadians == 0f)	//If no rotation is applied, don't use matrix math, it's slower
		{
			final float halfWidth = size.x / 2f;
			final float halfHeight = size.y / 2f;
			positions[0] = new Vector2f(position.x - halfWidth, position.y - halfHeight);
			positions[1] = new Vector2f(position.x + halfWidth, position.y - halfHeight);
			positions[2] = new Vector2f(position.x + halfWidth, position.y + halfHeight);
			positions[3] = new Vector2f(position.x - halfWidth, position.y + halfHeight);
		}
		else
		{
			Matrix4f transform = new Matrix4f();
			transform.translationRotateScale(new Vector3f(position.x, position.y, 0f), 
											 new Quaternionf().rotateZ(rotationRadians), 
											 new Vector3f(size.x, size.y, 1f));
			for(int i = 0; i < 4; i++)
			{
				Vector4f position4f = new Vector4f(QUAD_VERTEX_POSITIONS[i].x, QUAD_VERTEX_POSITIONS[i].y, 
						QUAD_VERTEX_POSITIONS[i].z, QUAD_VERTEX_POSITIONS[i].w).mul(transform);
				positions[i] = new Vector2f(position4f.x, position4f.y);
			}
		}

		//Get the correct texture index
		int textureIndex = 0;
		for(int i = 1; i < Renderer2D.textureSlotIndex; i++)
		{
			if(((OpenGLTexture2D) Renderer2D.textureSlots[i]).getRendererID() == ((OpenGLTexture2D) texture).getRendererID())	//OpenGL SPECIFIC!!!)
			{
				textureIndex = i;
				break;
			}
		}
		if(textureIndex == 0)
		{
			if(Renderer2D.textureSlotIndex >= Renderer2D.MAX_TEXTURE_SLOTS)
				flush();
			textureIndex = Renderer2D.textureSlotIndex;
			Renderer2D.textureSlots[textureIndex] = texture;
			Renderer2D.textureSlotIndex++;
		}

		for(int i = 0; i < 4; i++)
			setQuadVertex(positions[i], color, textureCoords[i], textureIndex);
	}
	
	//Set a vertex to the vertices to draw
	private static void setQuadVertex(final Vector2f position, final Color color, final Vector2f textureCoords, final int textureIndex)
	{
		//6 is the number of floats being assigned in this function
		int currentVertex = Renderer2D.quadVertexIndex * Renderer2D.vertexFloatCount;

		Renderer2D.quadVertices[currentVertex + 0] = position.x;	//Position.x
		Renderer2D.quadVertices[currentVertex + 1] = position.y;	//Position.y
		Renderer2D.quadVertices[currentVertex + 2] = color.r;		//Color.r
		Renderer2D.quadVertices[currentVertex + 3] = color.g;		//Color.g
		Renderer2D.quadVertices[currentVertex + 4] = color.b;		//Color.b
		Renderer2D.quadVertices[currentVertex + 5] = color.a;		//Color.a
		Renderer2D.quadVertices[currentVertex + 6] = textureCoords.x;			//TexCoords.x
		Renderer2D.quadVertices[currentVertex + 7] = textureCoords.y;			//TexCoords.y
		Renderer2D.quadVertices[currentVertex + 8] = textureIndex;			//TexIndex
		Renderer2D.quadVertexIndex++;
	}	
}
