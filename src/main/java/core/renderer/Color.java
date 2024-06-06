package core.renderer;

public class Color
{
	public float r;
	public float g;
	public float b;
	public float a;
	
	public Color(float r, float g, float b, float a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color(float r, float g, float b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1f;
	}

	public static final Color WHITE = new Color(1f, 1f, 1f, 1f);
	public static final Color BLACK = new Color(0f, 0f, 0f, 1f);
	public static final Color RED = new Color(1f, 0f, 0f, 1f);
	public static final Color GREEN = new Color(0f, 1f, 0f, 1f);
	public static final Color BLUE = new Color(0f, 0f, 1f, 1f);
}
