package com.Amazing.PathFinding.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class Dijkstra extends Solutions{

	private int[][] visited, graph;
	private int[] target;
	private HashMap<Point, Point> mp;
	PriorityQueue<Point> pq;
	
	public Dijkstra(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	public class Point
	{
		private int x,y;
		private int distToSrc = 0;
		private Point parent = this;
		
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
	
	
	class Cmp implements Comparator<Point>
	{
		@Override
		public int compare(Point p1, Point p2) {

			if(p1.distToSrc<p2.distToSrc)
			{
				return -1;
			}
			else if(p1.distToSrc>p2.distToSrc)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		
	}
	
	
	public Point find(Point p, boolean anim)
	{
		if(p.x==target[0] && p.y==target[1]) return p;
		
		if(anim) update(p.x,p.y, closeCol);		

		for(int[] v : new int[][] {{0,-1},{0,1},{-1,0},{1,0}} )
		{
			int x = p.x + v[0];
			int y = p.y + v[1];
			if(x<0 || x>=graph.length || y<0 || y>=graph[0].length || visited[x][y]==1 || graph[x][y]==1) continue;
			
			if(anim)
			{
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				update(x,y, openCol);
			}

			
			Point c = new Point(x,y);
			if(mp.containsKey(c))
			{
				c = mp.get(c);
				if(c.distToSrc>p.distToSrc+1)
				{
					c.distToSrc = p.distToSrc+1;
					c.parent = p;
				}
			}
			else
			{
				c.distToSrc = p.distToSrc+1;
				c.parent = p;
				mp.put(c, c);
				pq.add(c);
			}			
		}
		visited[p.x][p.y] = 1;
		
		while(!pq.isEmpty() && visited[pq.peek().x][pq.peek().y] == 1)
		{
			mp.remove(new Point(pq.peek().x,pq.peek().y));
			pq.poll();
		}
		
		if(pq.isEmpty()) return null;
		return find(pq.peek(), anim);
	}
	
	
	public ArrayList<ArrayList<Integer>> findPath(int[][] graph, int[] source, int[] target, boolean anim)
	{
		visited = new int[graph.length][graph[0].length];
		for(int[] v : visited) Arrays.fill(v, 0);

		this.target = target;
		this.graph = graph;
		mp = new HashMap<>(graph.length*graph[0].length);
		pq = new PriorityQueue<>(new Cmp());
		
		
		Point src = new Point(source[0],source[1]);
		src.distToSrc = 0;
		mp.put(src, src);
		pq.add(src);
		
		Point p = find(src,anim);
		ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
		while(p!=null && p.parent!=p)
		{
			ans.add(new ArrayList<Integer>());
			ans.get(ans.size()-1).addAll(Arrays.asList(p.x,p.y));
			p = p.parent;
		}
		
		Collections.reverse(ans);
		return ans;
	}
}
