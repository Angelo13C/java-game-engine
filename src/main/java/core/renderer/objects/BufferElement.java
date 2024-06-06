package core.renderer.objects;

import core.renderer.objects.Shader.ShaderDataType;

public class BufferElement
{
	public String name;
	public ShaderDataType type;
	public int size;
	public int offset;
	public boolean normalized;
	
	public BufferElement(final ShaderDataType type, final String name)
	{
		this.name = name;
		this.type = type;
		this.size = ShaderDataType.getSize(type);
		this.offset = 0;
		this.normalized = false;
	}
	
	public BufferElement(final ShaderDataType type, final String name, final boolean normalized)
	{
		this.name = name;
		this.type = type;
		this.size = ShaderDataType.getSize(type);
		this.offset = 0;
		this.normalized = normalized;
	}
}
