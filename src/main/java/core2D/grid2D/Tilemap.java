package core2D.grid2D;

import org.joml.Vector2f;
import org.joml.Vector2i;

import core.ecs.Behaviour;
import core.ecs.update.DisableAutoUpdate;
import core.renderer.Color;
import core.renderer.texture.Sprite;
import core2D.Transform2D;
import core2D.renderer2D.Renderer2D;

@DisableAutoUpdate
public class Tilemap extends Behaviour
{		
	public Tileset tileset;
	private Grid2D<Integer> tilesGridID;

	public Tilemap()
	{
		this.tilesGridID = new Grid2D<Integer>(new Vector2i(40, 40), new Vector2f(1f, 1f));
		this.tilesGridID.setAllCells(-1);
	}	
	public Tilemap(final Vector2i tileCount)
	{
		this.tilesGridID = new Grid2D<Integer>(tileCount, new Vector2f(1f, 1f));
		this.tilesGridID.setAllCells(-1);
	}	
	public Tilemap(final Vector2i tileCount, final Vector2f tileSize)
	{
		this.tilesGridID = new Grid2D<Integer>(tileCount, tileSize);
		this.tilesGridID.setAllCells(-1);
	}
	
	public void setTileset(final Tileset tileset)
	{
		this.tileset = tileset;
	}
	
	public void setTile(final Vector2i gridPosition, final int tileID)
	{
		this.tilesGridID.setCell(gridPosition, tileID);
	}	
	public int getTileID(final Vector2i gridPosition)
	{
		return this.tilesGridID.getCell(gridPosition);
	}
	public Grid2D<Integer> getGrid()
	{
		return this.tilesGridID;
	}

	@Override
	protected void onUpdate() 
	{
		Vector2f position = new Vector2f();
		Vector2f scale = new Vector2f();
		float rotation = Transform2D.getValues(this.gameObject, position, scale);
		
		for(int x = 0; x < this.tilesGridID.getGridSize().x; x++)
		{
			for(int y = 0; y < this.tilesGridID.getGridSize().y; y++)
			{
				final int tileID = this.tilesGridID.getCell(new Vector2i(x, y));
				if(tileID == -1)
					continue;
				
				Sprite tileSprite = this.tileset.getTile(tileID).sprite;
				Vector2f tileScale = new Vector2f(scale.x, scale.y);
				tileScale.mul(this.tilesGridID.getCellSize());
				
				Vector2f tilePosition = new Vector2f(x, y);
				tilePosition.mul(tileScale);
				tilePosition.add(position);
				
				Renderer2D.drawQuad(tilePosition, tileScale, (float) Math.toRadians(rotation), tileSprite, Color.WHITE);
			}
		}
	}
}
