package com.Amazing.PathFinding.Maze.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;


public class MazeDFS extends Maze{

	public MazeDFS(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	
	
	class Point
	{
		private int x,y;
		
		public Point()
		{
			
		}
		
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
	
	public void maze(int xp, int yp, int[][] graph, List<int[]> d) throws InterruptedException
	{
		Thread.sleep(speed);
		update(xp,yp,emptyTile, emptyTile.getColor(), true);	

		List<int[]> directions = new ArrayList<>(4);
		for(int i = 0;i<4;i++) directions.add(d.get(i));
		Collections.shuffle(directions);
		
		
		for(int[] v :  directions)
		{
			int x,y;
			x = xp + 2*v[0];
			y = yp + 2*v[1];
			
			if(x<0 || x>=graph.length || y<0 || y>=graph[0].length || graph[x][y]==1) continue;

			int x1 = xp + v[0];
			int y1 = yp + v[1];
			
			Thread.sleep(speed);
			update(x1,y1,emptyTile, emptyTile.getColor(), true);
			
			graph[x1][y1] = 1;
			graph[x][y] = 1;
			
			maze(x, y, graph, directions);
		}
	}
	


	public void generateMaze() throws InterruptedException {
		super.fillWalls();
		
		List<int[]> directions = Arrays.asList(new int[][] {{-1,0},{1,0},{0,-1},{0,1}});
		int[][] graph = new int[Level.wtile][Level.htile - Game.barTileHeight];
		
		for(int i = 0;i<graph.length;i++)
			Arrays.fill(graph[i], 0);
		
		maze(1,1, graph, directions);
	}
	
	

}
