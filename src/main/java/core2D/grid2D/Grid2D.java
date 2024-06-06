/* ARRAY
 * package core2D;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class Grid2D<Cell>
{
	private Vector2f gridSize;
	private Vector2f cellSize;
	private Vector2i gridCellCount;
	private Cell[][] cells;
	
	public Grid2D(final Vector2f gridSize, final Vector2f cellSize) 
	{
		this.gridSize = gridSize;
		this.cellSize = cellSize;
		this.gridCellCount = new Vector2i(0, 0);
		updateGridCellCount();
	}
	
	public Vector2f getGridSize()
	{
		return this.gridSize;
	}	
	public Vector2f getCellSize()
	{
		return this.cellSize;
	}
	public Vector2i getGridCellCount()
	{
		return this.gridCellCount;
	}
	public void setGridSize(final Vector2f gridSize)
	{
		this.gridSize = gridSize;
		updateGridCellCount();
	}
	public void setCellSize(final Vector2f cellSize)
	{
		this.cellSize = cellSize;
		updateGridCellCount();
	}
	
	public Cell getCell(final Vector2i gridPosition)
	{
		return this.cells[gridPosition.x][gridPosition.y];
	}	
	public void setCell(final Vector2i gridPosition, final Cell value)
	{
		this.cells[gridPosition.x][gridPosition.y] = value;
	}
	
	@SuppressWarnings("unchecked")
	public void updateGridCellCount()
	{
		final Vector2i oldGridCellCount = this.gridCellCount;
		final Cell[][] oldCells = cells;
		
		this.gridCellCount = new Vector2i((int) Math.floor(gridSize.x / cellSize.x), (int) Math.floor(gridSize.y / cellSize.y));
		this.cells = (Cell[][]) new Object[gridCellCount.x][gridCellCount.y];
		for(int y = 0; y < oldGridCellCount.y; y++)
		{
			for(int x = 0; x < oldGridCellCount.x; x++)
			{
				this.cells[x][y] = oldCells[x][y];
System.out.println("Cell: " + this.cells[x][y]);
			}
		}
	}
}
 */
/*LIST */
package core2D.grid2D;

import java.util.ArrayList;
import java.util.Collections;

import org.joml.Vector2f;
import org.joml.Vector2i;

public class Grid2D<Cell>
{
	private Vector2i gridSize;
	private Vector2f cellSize;
	private ArrayList<ArrayList<Cell>> cells;	//The external list is the X (columns), the internal one is the Y (rows)
	
	public Grid2D(final Vector2i gridSize, final Vector2f cellSize) 
	{
		this.gridSize = gridSize;
		this.cellSize = cellSize;
		cells = new ArrayList<ArrayList<Cell>>();
		refreshGrid();
	}
	
	public Vector2i getGridSize()
	{
		return this.gridSize;
	}	
	public Vector2f getCellSize()
	{
		return this.cellSize;
	}
	public void setGridSize(final Vector2i gridSize)
	{
		this.gridSize = gridSize;
		refreshGrid();
	}
	public void setCellSize(final Vector2f cellSize)
	{
		this.cellSize = cellSize;
		refreshGrid();
	}
	
	public void setCell(final Vector2i gridPosition, final Cell value)
	{
		this.cells.get(gridPosition.x).set(gridPosition.y, value);
	}
	//Set all cells to the same value
	public void setAllCells(final Cell value)
	{
		for(int x = 0; x < this.cells.size(); x++)
		{
			for(int y = 0; y < this.cells.get(x).size(); y++)
				this.cells.get(x).set(y, value);
		}
	}
	public Cell getCell(final Vector2i gridPosition)
	{
		return this.cells.get(gridPosition.x).get(gridPosition.y);
	}
	public Vector2i getCellGridPosition(final Cell cell)
	{
		for(int x = 0; x < this.cells.size(); x++)
		{
			int y = this.cells.get(x).indexOf(cell);
			if(y != -1)
				return new Vector2i(x, y);
		}
		
		return null;
	}
	
	public void refreshGrid()
	{		
		Vector2i gridSizeDifference = new Vector2i(this.gridSize.x - this.cells.size(), this.gridSize.y);
		if(this.cells.size() > 0)
			gridSizeDifference.y = this.gridSize.y - this.cells.get(0).size();
		
		//Add the columns (X)
		if(gridSizeDifference.x > 0)
		{
			for(int x = 0; x < gridSizeDifference.x; x++)
				this.cells.add(new ArrayList<Cell>());
		}
		//Remove the columns (X)
		else if(gridSizeDifference.x < 0)
			this.cells.subList(this.gridSize.x, this.cells.size()).clear();

		//Add the rows (Y) in each column
		if(gridSizeDifference.y > 0)
		{
			for(int x = 0; x < gridSizeDifference.x; x++)
				this.cells.get(x).addAll(Collections.nCopies(gridSizeDifference.y, null));
		}
		//Remove the rows (Y) in each column
		else if(gridSizeDifference.y < 0)
		{
			this.cells.get(0).subList(this.gridSize.y, this.cells.get(0).size()).clear();
		}
	}
	
	public static int getIndexFromGridPosition(final int gridWidth, final Vector2i gridPosition)
	{
		return gridPosition.x + 				//X
			   gridPosition.y * gridWidth;		//Y
	}
}

