package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class RecDiv extends Maze{
	public RecDiv(int width, int height, int tileSize) {
		super(width, height, tileSize);
		// TODO Auto-generated constructor stub
	}


	private Random random = new Random();
	
	public void divHeight(int x0, int y0, int x1, int y1) throws InterruptedException
	{
		if(x1<=x0 || y1<=y0) return;
		
		//div on height
		int ydiv = 0;
		while(ydiv%2==0) ydiv = random.nextInt(y1-y0+1)+y0;
		
		Thread.sleep(speed);
		for(int x = x0;x<=x1;x++) update(x, ydiv, wallTile, wallTile.getColor(), true);
		
		Thread.sleep(speed);
		int xdiv = 1;
		while(xdiv%2!=0) xdiv = random.nextInt(x1-x0+1)+x0;
				
		update(xdiv, ydiv, emptyTile, emptyTile.getColor(), true);
		
		divWidth(x0, y0, x1, ydiv-1);
		divWidth(x0, ydiv+1, x1, y1);
	}
	
	
	public void divWidth(int x0, int y0, int x1, int y1) throws InterruptedException
	{
		if(x1<=x0 || y1<=y0) return;

		int xdiv = 0;
		while(xdiv%2==0) xdiv = random.nextInt(x1-x0+1)+x0;
			
		Thread.sleep(speed);
		for(int y = y0;y<=y1;y++) update(xdiv, y, wallTile, wallTile.getColor(), true);
		
		Thread.sleep(speed);
		int ydiv = 1;
		while(ydiv%2!=0) ydiv = random.nextInt(y1-y0+1)+y0;

		update(xdiv, ydiv, emptyTile, emptyTile.getColor(), true);

		divHeight(x0, y0, xdiv-1, y1);
		divHeight(xdiv+1, y0, x1, y1);
	}
	
	
	public void generateMaze() throws InterruptedException {
		
		int width = Level.wtile;
		int height = Level.htile - Game.barTileHeight;
		divWidth(0, 0, width-1, height-1);
	}

}
