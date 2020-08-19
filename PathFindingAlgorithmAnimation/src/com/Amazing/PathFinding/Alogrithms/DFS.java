package com.Amazing.PathFinding.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DFS extends Solutions{

	boolean anim = true;
	
	
	public DFS(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}
	
	
	class Point
	{
		private int x,y;
		private Point parent = this;
		
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
	
	public Point find(Point p, int[][] visited, int[][] graph, int[] target, List<int[]> directions)
	{
		if(anim) update(p.x,p.y,closeCol);
		
		if(p.x==target[0] && p.y==target[1])
		{
			return p;
		}

		
		for(int[] v :  directions)
		{
			int x = p.x + v[0];
			int y = p.y + v[1];
			Point neighbor = new Point(x,y);
			if(x<0 || x>=graph.length || y<0 || y>=graph[0].length || graph[x][y]==1 || visited[x][y]==1) continue;
			
			
			if(anim)
			{
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				update(x,y,openCol);
			}

			neighbor.parent = p;
			visited[x][y]=1;
			
			Point node = find(neighbor, visited, graph, target, directions);
			if(node!=null) return node;
		}
		return null;
	}
	

	@Override
	public ArrayList<ArrayList<Integer>> findPath(int[][] graph, int[] source, int[] target, boolean anim) {
		
		this.anim = anim;
		
		int[][] visited = new int[graph.length][graph[0].length];
		for(int[] v : visited) Arrays.fill(v, 0);
		
		Point node = null;
		List<int[]> directions = Arrays.asList(new int[][] {{0,-1},{0,1},{-1,0},{1,0},});
		ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
		
		visited[source[0]][source[1]]=1;
		node = find(new Point(source[0], source[1]), visited, graph, target, directions);
		
		while(node!=null && node.parent!=node)
		{
			ans.add(new ArrayList<Integer>());
			ans.get(ans.size()-1).addAll(Arrays.asList(node.x,node.y));
			node = node.parent;
		}
		
		Collections.reverse(ans);
		return ans;
	}

}
