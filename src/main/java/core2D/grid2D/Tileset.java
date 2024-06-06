package core2D.grid2D;

import core.renderer.texture.SpriteAtlas;

public class Tileset
{
	private Tile[] tiles;	
	
	public void setTiles(final Tile[] tiles)
	{
		this.tiles = tiles;
	}
	
	public static Tileset createFromSpriteAtlas(final SpriteAtlas spriteAtlas)
	{
		Tileset tileset = new Tileset();
		Tile[] tiles = new Tile[spriteAtlas.getSprites().size()];
		for(int i = 0; i < tiles.length; i++)
		{
			tiles[i] = new Tile(spriteAtlas.getSprites().get(i));
		}
		tileset.setTiles(tiles);
		
		return tileset;
	}	
	
	public Tile getTile(final int index)
	{
		return this.tiles[index];
	}
}
