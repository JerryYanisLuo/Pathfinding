package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class Kruskal extends Maze{

	private int width = Level.wtile;
	private int height = Level.htile - Game.barTileHeight;
	private HashMap<Integer, Integer> mp = new HashMap<>();
	private Random random = new Random();
	
	public Kruskal(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	public int parent(int i)
	{
		if(mp.get(i)==i) return i;
		return parent(mp.get(i));
	}
	
	public int hashCode(int x, int y)
	{
		return x+y*width;
	}
	
	
	public void generateMaze() throws InterruptedException {
		super.fillWalls();
		LinkedList<int[]> edges = new LinkedList<>();
		
		for(int x = 0;x<width;x++)
		{
			for(int y = 0;y<height;y++)
			{
				if(x%2==0 && y%2==0)
				{
					mp.put(hashCode(x,y), hashCode(x,y));
				}
				else if(x%2!=0 && y%2==0 || x%2==0 && y%2!=0)
				{
					edges.add(new int[] {x,y});
				}
			}
		}
		
		while(edges.size()>0)
		{
			int id = random.nextInt(edges.size());
			int xp = edges.get(id)[0];
			int yp = edges.get(id)[1];
			edges.remove(id);

			int x0 = xp, y0 = yp, x1 = xp, y1 = yp;
			if(xp%2==0 && yp%2!=0)
			{
				if(yp<1 || yp>=height-1) continue;
				y0 = yp-1;
				y1 = yp+1;
			}
			else if(xp%2!=0 && yp%2==0)
			{
				if(xp<1 || xp>=width-1) continue;
				x0 = xp-1;
				x1 = xp+1;
			}

			if(parent(hashCode(x0,y0))!=parent(hashCode(x1,y1)))
			{
				mp.put(parent(hashCode(x1,y1)), parent(hashCode(x0,y0)));
				Thread.sleep(speed);
				update(xp,yp, emptyTile, emptyTile.getColor(), true);
				update(x0,y0, emptyTile, emptyTile.getColor(), true);
				update(x1,y1, emptyTile, emptyTile.getColor(), true);
			}
		}
	}

}
