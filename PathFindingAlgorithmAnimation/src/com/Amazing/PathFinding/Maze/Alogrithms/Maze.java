package com.Amazing.PathFinding.Maze.Alogrithms;


import java.util.Arrays;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.Tile.Tile;
import com.Amazing.PathFinding.level.Level;

public class Maze extends Level{

	public Maze(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	
	public void fillWalls()
	{
		Arrays.fill(this.pixels, Level.wallTile.getColor());
		Arrays.fill(this.tiles, Level.wallTile);
	}
	
	public void generateMaze() throws InterruptedException {
		
	}	

	public void update(int xp, int yp, Tile tile, int color, boolean build)
	{		
		yp += Game.barTileHeight;
		if(build) tiles[xp+yp*wtile] = tile;
		for(int x = 0;x<tileSize;x++)
		{
			int xa = x + xp*tileSize;
			for(int y = 0;y<tileSize;y++)
			{
				int ya = y + yp*tileSize;
				
				if(xa<0 || xa>=width || ya<0 || ya>=height) continue;
				pixels[xa+ya*width] = color;
			}
		}
	}
}
