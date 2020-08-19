package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class Prim extends Maze{

	private Random random = new Random();
	
	public Prim(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	
	public class Point
	{
		int x,y;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Point)) return false;
	        Point obj = (Point) o;
	        return obj.x == this.x && obj.y == this.y;
	    }

		@Override
	    public int hashCode() {
			int tmp = (y + ((x+1)/2));
			return x + (tmp * tmp); 
	    }
	}

	public void generateMaze() throws InterruptedException {
		super.fillWalls();
		
		int width = Level.wtile;
		int height = Level.htile - Game.barTileHeight;

		List<int[]> directions = Arrays.asList(new int[][] {{-1,0},{1,0},{0,-1},{0,1}});
		LinkedList<Point> open = new LinkedList<>();
		HashSet<Point> close = new HashSet<>();
		
		int xs = random.nextInt(width);
		int ys = random.nextInt(height);
		open.add(new Point(xs,ys));
		
		while(!open.isEmpty())
		{
			Thread.sleep(speed);

			int index = random.nextInt(open.size());
			Point p = open.get(index);
			int xp = p.x;
			int yp = p.y;
			update(xp,yp,null,0x008cff,false);
			close.add(p);
			
			Thread.sleep(speed);
			
			List<int[]> d = new ArrayList<>(4);
			for(int i = 0;i<4;i++) d.add(directions.get(i));
			Collections.shuffle(d);
			boolean build = false;
			
			
			for(int[] v :  d)
			{
				int x2,y2, x1,y1;
				x2 = xp + 2*v[0];
				y2 = yp + 2*v[1];
				if(x2<0 || x2>=width || y2<0 || y2>=height) continue;
				
				if(!build && close.contains(new Point(x2,y2)))
				{
					x1 = xp + v[0];
					y1 = yp + v[1];
					
					//make road
					update(x1,y1,emptyTile,emptyTile.getColor(),true);
					close.add(new Point(x1,y1));
					build = true;
				}
				else
				{
					if(!close.contains(new Point(x2,y2)) && !open.contains(new Point(x2,y2)))
					{
						open.add(new Point(x2,y2));
						update(x2,y2,null,0xff0000, false);
					}
				}
			}
			update(xp,yp,emptyTile,emptyTile.getColor(),true);
			open.remove(index);
		}
	}
	
}
