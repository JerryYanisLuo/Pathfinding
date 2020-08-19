package com.Amazing.PathFinding.level;

import java.util.Arrays;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.Tile.Tile;
import com.Amazing.PathFinding.input.Mouse;

public class Level {
	
	public int[] pixels;
	protected int width;
	protected int height;
	protected int tileSize;
	public Tile[] tiles;
	public static int wtile;
	public static int htile;
	public static int speed = 1;
	
	//-1: init, 0: draw, 1: erase, 2: clear, 3:Start, 4:End, 5:mid
	public static int state = -1;
	
	//tiles
	public static final Tile emptyTile = new Tile(0xff00ff, false);
	public static final Tile wallTile = new Tile(0, true);
	public static final Tile startTile = new Tile(0x9cff33, false);
	public static final Tile endTile = new Tile(0xff0000, false);
	public static final Tile soluTile = new Tile(0xffff00, false);
	public static final Tile midTile = new Tile(0xff6f00, false);
	
	
	public Level(int width, int height, int tileSize)
	{
		pixels = new int[width*height];
		Arrays.fill(pixels, 0xff00ff);
		this.tileSize = tileSize;
		this.height = height;
		this.width = width;
		wtile = width/tileSize;
		htile = height/tileSize;
		tiles = new Tile[wtile*htile];
		Arrays.fill(tiles, emptyTile);
	}
	
	public void clear()
	{
		Arrays.fill(tiles, emptyTile);
		Arrays.fill(pixels, emptyTile.getColor());
		Level.state = -1;
	}
	
	
	public boolean erase()
	{
		if(Mouse.getMouseB()==-1) return false;
		
		int xx = Mouse.getMouseX();
		int yy = Mouse.getMouseY();
		
		if(xx<0 || xx>=width || yy<Game.getBarHeight() || yy>= height) return false;
		
		int xtile = xx / tileSize;
		int ytile = yy / tileSize;
		
		if(tiles[xtile+ytile*wtile]==emptyTile) return false;
		
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
	

	public int getWtile() {
		return wtile;
	}

	public int getHtile() {
		return htile;
	}


	public int[] getPixels() {
		return pixels;
	}

	
}
