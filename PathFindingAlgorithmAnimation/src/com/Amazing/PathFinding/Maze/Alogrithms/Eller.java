package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class Eller extends Maze{

	private Random random = new Random();
	private HashMap<Long, Long> mp;
	
	public Eller(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	public long parent(long i)
	{
		if(mp.get(i)==i) return i;
		return parent(mp.get(i));
	}
	
	public long hashCode(int x, int y)
	{
		return x+y*width;
	}
	
	
	
	public void generateMaze() throws InterruptedException {
		super.fillWalls();
		
		int width = Level.wtile;
		int height = Level.htile - Game.barTileHeight;
		
		mp = new HashMap<>();
		
		for(int x = 0;x<width;x++)
		{
			for(int y = 0;y<height;y++)
			{
				if(x%2==0 && y%2==0)
				{
					mp.put(hashCode(x,y), hashCode(x,y));
				}
			}
		}

		int bound = height%2==0?2:1;
		for(int y = 0;y<height-bound;y+=2)
		{

			HashMap<Long, ArrayList<Integer>> row = new HashMap<>();

			for(int x = 1;x<width-1;x+=2)
			{
				//find parent of left and right
				long p = parent(hashCode(x-1,y));
				if(p==parent(hashCode(x+1,y))) continue;
				
				Thread.sleep(speed);
				update(x-1,y, emptyTile, emptyTile.getColor(), true);
				update(x+1,y, emptyTile, emptyTile.getColor(), true);
				if(random.nextInt(2)==1)
				{
					update(x,y, emptyTile, emptyTile.getColor(), true);
					mp.put(parent(hashCode(x+1,y)), p);
				}
				
				if(row.containsKey(p))
				{
					row.get(p).add(x-1);
				}
				else
				{
					ArrayList<Integer> arr = new ArrayList<Integer>();
					arr.add(x-1);
					row.put(p, arr);
				}
				
				if(x>=width-3)
				{
					p = parent(hashCode(x+1,y));
					if(row.containsKey(p))
					{
						row.get(p).add(x+1);
					}
					else
					{
						ArrayList<Integer> arr = new ArrayList<Integer>();
						arr.add(x+1);
						row.put(p, arr);
					}
				}
				
			}

			for (Entry<Long, ArrayList<Integer>> set : row.entrySet()) {
			    ArrayList<Integer> arr = set.getValue();
			    long p = set.getKey();
			    int len = arr.size();
			    int dice = len==1?1:(random.nextInt(len-1)+1);
				for(int i = 0;i<dice;i++)
				{
					int x = arr.get(random.nextInt(len));
					Thread.sleep(speed);
					update(x,y+1, emptyTile, emptyTile.getColor(), true);
					update(x,y+2, emptyTile, emptyTile.getColor(), true);
					mp.put(hashCode(x,y+1), p);
					mp.put(hashCode(x,y+2), p);
				}
			}
		}
		
		int y = height-bound;
		for(int x = 1;x<width-1;x+=2)
		{
			if(parent(hashCode(x-1,y))!=parent(hashCode(x+1,y)))
			{
				Thread.sleep(speed);
				update(x,y, emptyTile, emptyTile.getColor(), true);
				update(x-1,y, emptyTile, emptyTile.getColor(), true);
				update(x+1,y, emptyTile, emptyTile.getColor(), true);
			}
		}
		
	}
}
