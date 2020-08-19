package com.Amazing.PathFinding.Alogrithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public class AStar extends Solutions{

	public AStar(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}


	private int[] target;
	
	
	class Node
	{
		private Node parent = this;
		private int[] cord = new int[2];
		private int Gcost, Hcost, Fcost;
		
		/**
		 * 
		 * @param Gcost : the distance from start node -- the distance from current node + the current node's Gcost
		 * @param Hcost : the distance from target node
		 */
		public Node(int x, int y)
		{
			this.cord[0] = x;
			this.cord[1] = y;
			
			this.Gcost = 0;
			this.Hcost = AStar.this.distance(this.cord, AStar.this.target);
			this.Fcost = Hcost; 
		}
		
		
		
		public Node(int x, int y, Node parent)
		{
			this.cord[0] = x;
			this.cord[1] = y;
			this.parent = parent;
			
			this.Gcost = AStar.this.distance(this.cord, parent.cord)+parent.Gcost;
			this.Hcost = AStar.this.distance(this.cord, AStar.this.target);
			this.Fcost = Gcost+Hcost; 
		}
		
		
		public void updateCost(int Gcost)
		{
			this.Gcost = Gcost;
			this.Fcost = Gcost+Hcost; 
		}
		
		
		
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Node)) return false;
	        Node obj = (Node) o;
	        return obj.cord[0] == this.cord[0] && obj.cord[1] == this.cord[1];
	    }

		@Override
	    public int hashCode() {
			int tmp = (cord[1] + ((cord[0]+1)/2));
			return cord[0] + (tmp * tmp); 
			
	    }
		
	}
	
	
	public int distance(int[] src, int[] tag)
	{
		int dx = 10*Math.abs(src[0] - tag[0]);
		int dy = 10*Math.abs(src[1] - tag[1]);

		return dx+dy;
	}
	
	
	class Cmp implements Comparator<Node>
	{

		@Override
		public int compare(Node o1, Node o2) {
			
			if(o1.Fcost<o2.Fcost)
			{
				return -1;
			}
			else if(o1.Fcost>o2.Fcost)
			{
				return 1;
			}
			else
			{
				if(o1.Hcost<o2.Hcost)
				{
					return -1;
				}
				else if(o1.Hcost>o2.Hcost)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}

		}
		
	}
	
	
	/**
	 * 
	 * @param graph : row-by-col matrix, elements only contains 0 and 1, 0 means road and 1 means wall
	 * @param source : 1-by-2 vector, donates coordinate (ith row, jth col)
	 * @param target : 1-by-2 vector, donates coordinate (ith row, jth col)
	 * @return
	 */
	public ArrayList<ArrayList<Integer>> findPath(int[][] graph, int[] source, int[] target, boolean anim)
	{		
		/**
		 * A star algorithm here
		 */
		
		int col = graph.length;
		int row = graph[0].length;
		this.target = target;
		
		PriorityQueue<Node> open = new PriorityQueue<>(new Cmp());
		HashMap<Node,Node> mp = new HashMap<>();
		HashSet<Node> closed = new HashSet<>();
		
		Node start = new Node(source[0], source[1]);
		open.add(start);
		mp.put(start, start);
		
		while(!open.isEmpty())
		{
			Node current = open.poll();
			closed.add(current);
			
			if(anim) update(current.cord[0],current.cord[1],closeCol);

			if(current.cord[0]==target[0] && current.cord[1]==target[1]) break;
			
			for(int c = 0;c<9;c++)
			{
				int xo = current.cord[0];
				int yo = current.cord[1];
				int x = xo + c%3 - 1;
				int y = yo + c/3 - 1;
				
				//just go up down left right
				if(Math.abs(x-xo)==1 && Math.abs(y-yo)==1) continue;
				
				if(x<0 || x>=col || y<0 || y>=row || graph[x][y]==1) continue;

				Node neighbor = new Node(x, y, current);
				if(closed.contains(neighbor)) continue;

				if(!mp.containsKey(neighbor) || mp.get(neighbor).Gcost>neighbor.Gcost)
				{
					mp.put(neighbor, neighbor);
					open.add(neighbor);
					
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
	
		
		ArrayList<ArrayList<Integer>> ans = new ArrayList<ArrayList<Integer>>();
		Node node = mp.get(new Node(target[0], target[1]));
		if(node==null) return ans;
		
		while(node!=null && node.parent!=node)
		{
			ans.add(new ArrayList<Integer>());
			ans.get(ans.size()-1).addAll(Arrays.asList(node.cord[0],node.cord[1]));
			node = node.parent;
		}
		
		Collections.reverse(ans);
		return ans;
	}

}
