package core.renderer.texture;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector2i;

import core2D.grid2D.Grid2D;

public class SpriteAtlas 
{
	private Texture2D texture;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	
	public SpriteAtlas(final Texture2D texture) 
	{
		this.texture = texture;
	}
	
	public SpriteAtlas(final Texture2D texture, final Vector2f[] textureCoords) 
	{
		this.texture = texture;
		
		for(int i = 0; i < textureCoords.length; i += 4)
		{
			addSprite(new Vector2f[] {
					textureCoords[i + 0],
					textureCoords[i + 1],
					textureCoords[i + 2],
					textureCoords[i + 3]
			});
		}
	}
	

	public static SpriteAtlas createFromGridCount(final Texture2D texture, final Vector2i cellCount)
	{
		Vector2i cellSize = new Vector2i(Math.floorDiv(texture.getWidth(), cellCount.x), Math.floorDiv(texture.getHeight(), cellCount.y));
		return createFromGrid(texture, cellSize);
	}
	public static SpriteAtlas createFromGrid(final Texture2D texture, final Vector2i cellSize)
	{
		final int spriteCountX = Math.floorDiv(texture.getWidth(), cellSize.x);
		final int spriteCountY = Math.floorDiv(texture.getHeight(), cellSize.y);
		Vector2f[] textureCoords = new Vector2f[4 * spriteCountX * spriteCountY];
		
		final Vector2f textureSize = new Vector2f(texture.getWidth(), texture.getHeight());
		
		for(int x = 0; x < spriteCountX; x++)
		{
			for(int y = 0; y < spriteCountY; y++)
			{
				final int index = Grid2D.getIndexFromGridPosition(spriteCountX, new Vector2i(x, y));
				Vector2i cellPosition = new Vector2i(x, y).mul(cellSize);
				textureCoords[(index * 4) + 0] = new Vector2f(cellPosition.x, cellPosition.y).div(textureSize);
				textureCoords[(index * 4) + 1] = new Vector2f(cellPosition.x + cellSize.x, cellPosition.y).div(textureSize);
				textureCoords[(index * 4) + 2] = new Vector2f(cellPosition.x + cellSize.x, cellPosition.y + cellSize.y).div(textureSize);
				textureCoords[(index * 4) + 3] = new Vector2f(cellPosition.x, cellPosition.y + cellSize.y).div(textureSize);
			}
		}

		return new SpriteAtlas(texture, textureCoords);
	}
	
	public Sprite addSprite(final Vector2f[] textureCoords)
	{
		Sprite sprite = new Sprite(this.texture, new Vector2f[] {
				textureCoords[0],
				textureCoords[1],
				textureCoords[2],
				textureCoords[3]});
		this.sprites.add(sprite);
		return sprite;
	}
	
	public List<Sprite> getSprites()
	{
		return this.sprites;
	}
}
