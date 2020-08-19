package com.Amazing.PathFinding.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;


public class GBFS extends Solutions{
	
	public GBFS(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	
	public class Point
	{
		private int x,y;
		private int distance = 0;
		private Point parent = this;
		
		public Point(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public int distance(Point a, Point b)
	{
		return Math.abs(a.x - b.x)+Math.abs(a.y-b.y);
	}
	
	class Cmp implements Comparator<Point>
	{
		@Override
		public int compare(Point p1, Point p2) {

			if(p1.distance<p2.distance)
			{
				return -1;
			}
			else if(p1.distance>p2.distance)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
	}
	
	
	@Override
	public ArrayList<ArrayList<Integer>> findPath(int[][] graph, int[] source, int[] target, boolean anim) {
		PriorityQueue<Point> pq = new PriorityQueue<>(new Cmp());
		int[][] visited = new int[graph.length][graph[0].length];
		for(int[] arr : visited) Arrays.fill(arr, 0);
		visited[source[0]][source[1]] = 1;
		Point src = new Point(source[0], source[1]);
		Point tag = new Point(target[0], target[1]);
		src.distance = distance(src,tag);
		pq.add(src);
		
		
		Point node = null;
		while(!pq.isEmpty())
		{
			Point p = pq.poll();
			if(anim) update(p.x,p.y, closeCol);
			
			if(p.x==tag.x && p.y==tag.y) {
				node = p;
				break;
			}
			
			for(int[] v : new int[][] {{0,-1},{0,1},{-1,0},{1,0}})
			{
				int x = p.x + v[0];
				int y = p.y + v[1];
				if(x<0 || x>=graph.length || y<0 || y>=graph[0].length || visited[x][y]==1 || graph[x][y]==1) continue;
				visited[x][y] = 1;
				Point pp = new Point(x,y);
				pp.distance = distance(pp, tag);
				pp.parent = p;
				pq.add(pp);
				
				if(anim)
				{
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					update(x,y, openCol);
				}

			}
		}
		
		ArrayList<ArrayList<Integer>> ans = new ArrayList<>();
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
