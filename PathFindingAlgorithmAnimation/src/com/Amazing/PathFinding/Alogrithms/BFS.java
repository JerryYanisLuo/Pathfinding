package com.Amazing.PathFinding.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends Solutions{

	public BFS(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	
	@Override
	public ArrayList<ArrayList<Integer>> findPath(int[][] graph, int[] source, int[] target, boolean anim) {
		
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
		
		
		Queue<Point> q = new LinkedList<>();
		q.add(new Point(source[0], source[1]));
		HashSet<Point> visited = new HashSet<>();
		visited.add(new Point(source[0], source[1]));

		Point node = new Point();
		
		
		while(!q.isEmpty())
		{
			int len = q.size();
			Point p = q.poll();
			
			if(anim) update(p.x,p.y,closeCol);

			if(p.x==target[0] && p.y==target[1])
			{
				node = p;
				break;
			}
			
			for(int i = 0;i<len;i++)
			{
				for(int[] v : new int[][] {{0,-1},{0,1},{-1,0},{1,0}} )
				{
					int x = p.x + v[0];
					int y = p.y + v[1];
					Point neighbor = new Point(x,y);
					
					
					if(x<0 || x>=graph.length || y<0 || y>=graph[0].length || graph[x][y]==1 || visited.contains(neighbor)) continue;

					
					neighbor.parent = p;
					q.add(neighbor);
					visited.add(neighbor);
					
					if(anim)
					{
						try {
							Thread.sleep(speed);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						update(x,y,openCol);
					}
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
