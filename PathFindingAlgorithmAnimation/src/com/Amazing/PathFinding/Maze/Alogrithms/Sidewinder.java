package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class Sidewinder extends Maze{

	private int width = Level.wtile;
	private int height = Level.htile - Game.barTileHeight;
	private Random random = new Random();
	
	public Sidewinder(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	public void generateMaze() throws InterruptedException {
		super.fillWalls();
		
		for(int x = 0;x<width;x++)
		{
			Thread.sleep(speed);
			update(x,0, emptyTile, emptyTile.getColor(), true);
		}
		
		for(int y = 2;y<height;y+=2)
		{
			for(int x = 0;x<width;)
			{
				Thread.sleep(speed);
				update(x,y, emptyTile, emptyTile.getColor(), true);
				
				int xs = x;
				int count = 0;
				while(random.nextInt(2)==0)
				{
					Thread.sleep(speed);
					update(x,y, emptyTile, emptyTile.getColor(), true);
					update(x+1,y, emptyTile, emptyTile.getColor(), true);
					x+=2;
					count++;
				}
				
				int north = 0;
				if(count==0)
				{
					north = x;
					x+=2;
				}
				else
				{
					north = random.nextInt(count)*2+xs;
				}
				
				Thread.sleep(speed);
				update(north,y-1, emptyTile, emptyTile.getColor(), true);
			}
		}

		
	}
}
