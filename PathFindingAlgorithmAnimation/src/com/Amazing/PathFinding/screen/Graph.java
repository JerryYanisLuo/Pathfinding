package com.Amazing.PathFinding.screen;

import com.Amazing.PathFinding.Tile.Tile;
import com.Amazing.PathFinding.level.Level;

public class Graph {

	//prepare for search algorithm
	public static int[][] graph;

	public Graph(int graphRow, int graphCol)
	{
		graph = new int[graphCol][graphRow];
	}
	
	public void update(Tile[] wall)
	{
		int diff = Level.htile - graph[0].length;
		for(int x = 0;x<graph.length;x++)
		{
			for(int y = 0;y<graph[0].length;y++)
			{
				if(wall[x+(y+diff)*Level.wtile].isSolid())
				{
					graph[x][y] = 1;
				}
				else
				{
					graph[x][y] = 0;
				}
			}
		}
	}
	
	public void showGraph()
	{
		for(int x = 0;x<graph.length;x++)
		{
			for(int y = 0;y<graph[0].length;y++)
			{
				System.out.println("x: "+x+" y: "+y+" "+graph[x][y]);
			}
		}
	}
	
}
