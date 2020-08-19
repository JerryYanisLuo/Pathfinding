package com.Amazing.PathFinding.level.point;

import java.util.Arrays;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.Tile.Tile;
import com.Amazing.PathFinding.input.Mouse;
import com.Amazing.PathFinding.level.Level;
import com.Amazing.PathFinding.screen.Screen;

public class Point extends Level{

	protected int activeState; 
	protected int xp = -1, yp = -1;
	protected Tile pointTile;
	
	public Point(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	
	public void generatePoint(Tile[] wall)
	{
		if(state!=activeState || Mouse.getMouseB()==-1) return;
		
		int xx = Mouse.getMouseX();
		int yy = Mouse.getMouseY();
		
		if(xx<0 || xx>=width || yy<Game.getBarHeight() || yy>= height) return;
		
		int xtile = xx / tileSize;
		int ytile = yy / tileSize;
		if(wall[xtile+ytile*wtile]!=emptyTile) return;

		xp = xtile;
		yp = ytile;
		
		Arrays.fill(tiles, emptyTile);
		tiles[xtile+ytile*wtile] = this.pointTile;

		Arrays.fill(pixels, 0xff00ff);
		for(int x = 0;x<tileSize;x++)
		{
			int xa = x + xtile*tileSize;
			for(int y = 0;y<tileSize;y++)
			{
				int ya = y + ytile*tileSize;
				if(xa<0 || xa>=this.width || ya<0 || ya>=this.height) continue;
				pixels[xa + ya * width] = pointTile.getColor();
			}
		}
	}
	
	
	public void clear()
	{
		Arrays.fill(tiles, emptyTile);
		Arrays.fill(pixels, emptyTile.getColor());
		Level.state = -1;
		
		xp = -1;
		yp = -1;
	}
	
	
	public boolean erase()
	{
		if(Mouse.getMouseB()==-1) return false;
		
		int xx = Mouse.getMouseX();
		int yy = Mouse.getMouseY();
		
		if(xx<0 || xx>=width || yy<Game.getBarHeight() || yy>= height) return false;
		
		int xtile = xx / tileSize;
		int ytile = yy / tileSize;
		
		if(xtile!=xp || ytile!=yp) return false;
		
		xp = -1;
		yp = -1;
		
		tiles[xtile+ytile*wtile] = emptyTile;
		
		for(int x = 0;x<tileSize;x++)
		{
			int xa = x + xtile*tileSize;
			for(int y = 0;y<tileSize;y++)
			{
				int ya = y + ytile*tileSize;
				if(xa<0 || xa>=this.width || ya<0 || ya>=this.height) continue;
				pixels[xa + ya * width] = emptyTile.getColor();
			}
		}

		return true;
	}
	
	public static int dragPoints(Tile[] globalTiles)
	{
		if(Mouse.getMouseB()==-1 || Level.state==1 || Level.state==0) return -10;
		
		int xx = Mouse.getMouseX();
		int yy = Mouse.getMouseY();
		if(xx<0 || xx>=Screen.width || yy<Game.getBarHeight() || yy>= Screen.height) return -10;
		
		int xtile = xx / Game.tileSize;
		int ytile = yy / Game.tileSize;
		
		int index = xtile+ytile*wtile;
		if(globalTiles[index] == emptyTile)
		{

		}
		else if(globalTiles[index]==startTile)
		{
			Level.state = 3;
		}
		else if(globalTiles[index]==endTile)
		{
			Level.state = 4;
		}
		else if(globalTiles[index]==midTile)
		{
			Level.state = 5;
		}
		
		return index;
	}


	public void updateCoordinate() {
		// TODO Auto-generated method stub
		
	}
}
