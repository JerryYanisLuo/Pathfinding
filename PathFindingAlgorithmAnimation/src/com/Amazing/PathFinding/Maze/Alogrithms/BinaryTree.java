package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class BinaryTree extends Maze{

	private int width = Level.wtile;
	private int height = Level.htile - Game.barTileHeight;
	private Random random = new Random();
	
	public BinaryTree(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	public void generateMaze() throws InterruptedException {
		super.fillWalls();
		
		int[][][] directions = new int[][][] {{{0,-1},{1,0}},{{0,-1},{-1,0}},{{0,1},{1,0}},{{0,1},{-1,0}}};
		int[][] orient = directions[random.nextInt(4)];
		
		height -= (height+1)%2;
		width -= (width+1)%2;
		
		for(int y = 0;y<height;y+=2)
		{
			for(int x = 0;x<width;x+=2)
			{
				Thread.sleep(speed);
				update(x,y, emptyTile, emptyTile.getColor(), true);
				
				int xp = -1, yp = -1;
				int count = 0;
				while(xp<0 || xp>=width || yp<0 || yp>=height)
				{
					int[] dice = orient[random.nextInt(2)];
					xp = dice[0] + x;
					yp = dice[1] + y;
					count++;
					if(count>10)
					{
						xp = 0;
						yp = 0;
						break;
					}
				}
				update(xp,yp, emptyTile, emptyTile.getColor(), true);
			}
		}
	}

}
