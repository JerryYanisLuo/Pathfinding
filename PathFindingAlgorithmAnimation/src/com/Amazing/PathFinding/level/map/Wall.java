package com.Amazing.PathFinding.level.map;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.Tile.Tile;
import com.Amazing.PathFinding.input.Mouse;
import com.Amazing.PathFinding.level.Level;

public class Wall extends Level{

	public Wall(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	
	public void generateWall(Tile[] globalTiles)
	{
		if(state!=0 || Mouse.getMouseB()==-1) return;
		
		int xx = Mouse.getMouseX();
		int yy = Mouse.getMouseY();
		
		if(xx<0 || xx>=width || yy<Game.getBarHeight() || yy>= height) return;
		
		int xtile = xx / tileSize;
		int ytile = yy / tileSize;
		
		if(globalTiles[xtile+ytile*wtile]!=emptyTile) return;
		
		tiles[xtile+ytile*wtile] = wallTile;

		int lineWidth = tileSize*1;
		
		for(int x = 0;x<lineWidth;x++)
		{
			int xa = x + xtile*tileSize;
			for(int y = 0;y<lineWidth;y++)
			{
				int ya = y + ytile*tileSize;
				if(xa<0 || xa>=this.width || ya<0 || ya>=this.height) continue;
				pixels[xa + ya * width] = wallTile.getColor();
			}
		}
	}
}
