package core.renderer.texture;

public abstract class Texture
{
	public abstract int getWidth();
	public abstract int getHeight();

	public abstract void bind(final int slot);
}
