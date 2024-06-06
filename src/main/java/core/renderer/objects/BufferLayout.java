package core.renderer.objects;

public class BufferLayout
{
	private BufferElement[] elements;
	private int stride = 0;
	
	public BufferLayout(BufferElement[] elements)
	{
		this.elements = elements;
		calculateOffsetsAndStride();
	}
	
	private final void calculateOffsetsAndStride()
	{
		this.stride = 0;
		for(BufferElement element : this.elements)
		{
			element.offset = this.stride;
			this.stride += element.size;
		}
	}
	
	public final BufferElement[] getElements()
	{
		return this.elements;
	}
	
	public final int getStride()
	{
		return this.stride;
	}
}
