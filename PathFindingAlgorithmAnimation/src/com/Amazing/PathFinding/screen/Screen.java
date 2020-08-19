package com.Amazing.PathFinding.screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.Alogrithms.Solutions;
import com.Amazing.PathFinding.Maze.Alogrithms.Maze;
import com.Amazing.PathFinding.Tile.Tile;
import com.Amazing.PathFinding.input.Mouse;
import com.Amazing.PathFinding.level.Level;
import com.Amazing.PathFinding.level.map.Arrow;
import com.Amazing.PathFinding.level.map.Path;
import com.Amazing.PathFinding.level.map.Wall;
import com.Amazing.PathFinding.level.point.BombPoint;
import com.Amazing.PathFinding.level.point.EndPoint;
import com.Amazing.PathFinding.level.point.Point;
import com.Amazing.PathFinding.level.point.StartPoint;

public class Screen {
	
	private int[] pixels;
	public static int width;
	public static int height;
	public static int tileSize;
	private Tile[] globalTiles;
	
	private Wall wall;
	public LinkedList<StartPoint> stps;
	public LinkedList<EndPoint> edps;
	public LinkedList<BombPoint> bbps;
	private Graph graph;

	private static Path path;
	private static Solutions searchAlg;
	private static Maze mazeAlg;
	
	private LinkedList<Arrow> arrows;
	public ArrayList<ArrayList<ArrayList<Integer>>> ultimateAns;
	private static boolean arrowRenderPermit = true;
	
	public static boolean solved = false;
	
	private Random random = new Random();
	public static int seed = 0;
	public static int currPoint = 0;
	
	public Screen(int width, int height, int tileSize)
	{
		pixels = new int[width*height];
		Screen.width = width;
		Screen.height = height;
		Screen.tileSize = tileSize;
		
		wall = new Wall(width, height, tileSize);
		stps = new LinkedList<>();
		edps = new LinkedList<>();
		bbps = new LinkedList<>();
		graph = new Graph(Level.htile - Game.barTileHeight, Level.wtile);
		
		globalTiles = new Tile[Level.wtile*Level.htile];
		
		path = new Path(width, height, tileSize);
		ultimateAns = new ArrayList<>();
		arrows = new LinkedList<>();
		searchAlg = new Solutions(width, height, tileSize);
		mazeAlg = new Maze(width, height, tileSize);
	}
	
	public void createGrid()
	{
		for(int x = 0;x<width;x++)
		{
			for(int y = 0;y<height;y++)
			{
				if(x%tileSize==0 || y%tileSize==0){
					pixels[x + y * width] = 0xaaaaaa;
				}
				else
				{
					pixels[x + y * width] = 0xffffff;
				}
			}
		}
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	
	public void update()
	{
		updateGlobalTiles();
		updateWall();
		updatePoints();
		erase();
		clear();
		updateSolution();
		
		for(int k = 0;k<arrows.size();k++)
		{
			arrows.get(k).soluPathArrow(true);
		}
	}
	
	
	public void render()
	{
		createGrid();
		renderAll();
	}
	
	
	public void renderAll()
	{
		for(int i = 0;i<wall.getPixels().length;i++)
		{
			if(searchAlg.getPixels()[i]!=0xff00ff) this.pixels[i] = searchAlg.getPixels()[i];
			if(path.getPixels()[i]!=0xff00ff) this.pixels[i] = path.getPixels()[i];
			if(wall.getPixels()[i]!=0xff00ff) this.pixels[i] = wall.getPixels()[i];

			for(int j = 0;j<stps.size();j++)
			{
				if(stps.get(j).getPixels()[i]!=0xff00ff) this.pixels[i] = stps.get(j).getPixels()[i];
			}
			
			for(int j = 0;j<edps.size();j++)
			{
				if(edps.get(j).getPixels()[i]!=0xff00ff) this.pixels[i] = edps.get(j).getPixels()[i];
			}

			for(int j = 0;j<bbps.size();j++)
			{
				if(bbps.get(j).getPixels()[i]!=0xff00ff) this.pixels[i] = bbps.get(j).getPixels()[i];
			}
			
			if(mazeAlg.getPixels()[i]!=0xff00ff) this.pixels[i] = mazeAlg.getPixels()[i];
		}
		

		renderArrows();

	}
	
	public void renderArrows()
	{
		if(!arrowRenderPermit) return;
		for(int k = 0;k<arrows.size();k++)
		{
			for(int i = 0;i<arrows.get(k).getPixels().length;i++)
				if(arrows.get(k).getPixels()[i]!=0xff00ff) this.pixels[i] = arrows.get(k).getPixels()[i];
		}
	}
	
	
	public void updateSolution()
	{
		currPoint = Point.dragPoints(globalTiles);
		updatePoints();
		if(solved)
		{
			if(Mouse.getMouseB()!=-1 && Level.state>=1 && Level.state<=5)
			{
				arrowRenderPermit = false;
				int st = Level.state;
				graph.update(globalTiles);
				SearchPath(Screen.searchAlg, false);
				arrowRenderPermit = true;
				Level.state = st;
			}
		}
		else
		{
			graph.update(globalTiles);
		}
		
	}
	
	
	public void updateWall()
	{
		wall.generateWall(globalTiles);
	}
	
	
	public void updatePoints()
	{
		if(stps.size()>0 && Level.state==3)
		{
			if(currPoint>=0)
			{	
				int i = 0;
				for(;i<stps.size();i++)
				{
					if(stps.get(i).x + (stps.get(i).y+Game.barTileHeight)*Level.wtile == currPoint)
					{
						stps.get(i).generatePoint(globalTiles);
						stps.get(i).updateCoordinate();
						break;
					}
				}
				
				if(i==stps.size())
				{
					stps.getLast().generatePoint(globalTiles);
					stps.getLast().updateCoordinate();
				}
			}
		}

		
		if(edps.size()>0 && Level.state==4)
		{
			if(currPoint>=0)
			{	
				int i = 0;
				for(;i<edps.size();i++)
				{
					if(edps.get(i).x + (edps.get(i).y+Game.barTileHeight)*Level.wtile == currPoint)
					{
						edps.get(i).generatePoint(globalTiles);
						edps.get(i).updateCoordinate();
						break;
					}
				}
				
				if(i==edps.size())
				{
					edps.getLast().generatePoint(globalTiles);
					edps.getLast().updateCoordinate();
				}
			}
		}
		
		
		if(bbps.size()>0 && Level.state==5)
		{
			if(currPoint>=0)
			{	
				int i = 0;
				for(;i<bbps.size();i++)
				{
					if(bbps.get(i).x + (bbps.get(i).y+Game.barTileHeight)*Level.wtile == currPoint)
					{
						bbps.get(i).generatePoint(globalTiles);
						bbps.get(i).updateCoordinate();
						break;
					}
				}
				
				if(i==bbps.size())
				{
					bbps.getLast().generatePoint(globalTiles);
					bbps.getLast().updateCoordinate();
				}
			}
		}
		
		

	}
	
	
	public void updateGlobalTiles()
	{
		Arrays.fill(globalTiles, Level.emptyTile);
		for(int i=0;i<globalTiles.length;i++)
		{
			if(Screen.mazeAlg.tiles[i]==Level.wallTile)
			{
				globalTiles[i] = Level.wallTile;
			}
			
			if(wall.tiles[i]==Level.wallTile)
			{
				globalTiles[i] = Level.wallTile;
			}
			
			for(int j = 0;j<stps.size();j++)
			{
				if(stps.get(j).tiles[i]==Level.startTile)
				{
					globalTiles[i] = Level.startTile;
				}
			}

			for(int j = 0;j<edps.size();j++)
			{
				if(edps.get(j).tiles[i]==Level.endTile)
				{
					globalTiles[i] = Level.endTile;
				}
			}
			
			for(int j = 0;j<bbps.size();j++)
			{
				if(bbps.get(j).tiles[i]==Level.midTile)
				{
					globalTiles[i] = Level.midTile;
				}
			}
		}
		
		
	}
	
	
	
	public void clear()
	{
		if(Level.state!=2) return;
		wall.clear();
		path.clear();
		searchAlg.clear();
		mazeAlg.clear();
		
		stps = new LinkedList<>();
		edps = new LinkedList<>();
		bbps = new LinkedList<>();
		
		ultimateAns = new ArrayList<>();
		arrows = new LinkedList<>();
		
		Level.state=-1;
		solved = false;
	}
	
	public void erase()
	{
		if(Level.state!=1) return;
		wall.erase();
		
		for(int i  = 0;i<stps.size();i++)
		{
			if(stps.get(i).erase())	stps.remove(i);
		}
		
		for(int i  = 0;i<edps.size();i++)
		{
			if(edps.get(i).erase())	edps.remove(i);
		}
		
		for(int i  = 0;i<bbps.size();i++)
		{
			if(bbps.get(i).erase())	bbps.remove(i);
		}
		
		mazeAlg.erase();
	}
	
	public synchronized void SearchPath(Solutions alg, boolean anim)
	{
		Screen.searchAlg = alg;
		alg.clear();
		path.clear();
		ultimateAns = new ArrayList<>();
		arrows = new LinkedList<>();
		random = new Random(seed);
		arrowRenderPermit = false;
		
		if(stps.size()<=0 || edps.size()<=0) return;
		
		LinkedList<int[]> intermediates = new LinkedList<>();
		for(BombPoint bbp : bbps)
		{
			if(bbp.x>0 && bbp.y>0) intermediates.add(new int[] {bbp.x, bbp.y});
		}

		if(intermediates.size()>0)
		{
			for(int i = 0;i<stps.size();i++)
			{
				if(stps.get(i).x<0 || stps.get(i).y<0) continue;
				
				ArrayList<ArrayList<Integer>> ans = alg.findPath(Graph.graph, new int[] {stps.get(i).x, stps.get(i).y}, intermediates.getFirst(), anim);
				if(ans.size()==0) continue;
				path.generateSoluPath(ans, anim, random.nextInt(0x777777));
				ultimateAns.add(ans);
			}
			
			int [] src = intermediates.getFirst();
			for(int i = 1;i<intermediates.size();i++)
			{
				if(intermediates.get(i)[0]<0 || intermediates.get(i)[1]<0 || intermediates.get(i)[0]>=Graph.graph.length || intermediates.get(i)[1]>=Graph.graph[0].length)
				{
					intermediates.remove(i);
					continue;
				}
				ArrayList<ArrayList<Integer>> ans = alg.findPath(Graph.graph, src, intermediates.get(i), anim);
				if(ans.size()==0)
				{
					System.out.println("No path found");
					return;
				}
				path.generateSoluPath(ans, anim, random.nextInt(0x777777));
				src = intermediates.get(i);
				ultimateAns.add(ans);
			}
			
			int fail = 0;
			for(int i = 0;i<edps.size();i++)
			{
				if(edps.get(i).x<0 || edps.get(i).y<0) continue;
				ArrayList<ArrayList<Integer>> ans = alg.findPath(Graph.graph, src, new int[] {edps.get(i).x, edps.get(i).y}, anim);
				if(ans.size()==0)
				{
					fail++;
					continue;
				}
				path.generateSoluPath(ans, anim, random.nextInt(0x777777));
				ultimateAns.add(ans);
			}
			if(fail==edps.size()) return;
		}
		else
		{
			for(int i = 0;i<stps.size();i++)
			{
				if(stps.get(i).x<0 || stps.get(i).y<0) continue;
				for(int j = 0;j<edps.size();j++)
				{
					if(edps.get(j).x<0 || edps.get(j).y<0) continue;
					ArrayList<ArrayList<Integer>> ans = alg.findPath(Graph.graph, new int[] {stps.get(i).x, stps.get(i).y}, new int[] {edps.get(j).x, edps.get(j).y}, anim);
					if(ans.size()==0) continue;
					path.generateSoluPath(ans, anim, random.nextInt(0x777777));
					ultimateAns.add(ans);
				}
			}
		}
		
		solved = true;
		for(int k = 0;k<ultimateAns.size();k++)	arrows.add(new Arrow(width, height, tileSize, ultimateAns.get(k)));
		arrowRenderPermit = true;
	}
	
	public synchronized void generateMaze(Maze alg)
	{
		Level.state=2;
		clear();
		Screen.mazeAlg = alg;

		try {
			alg.generateMaze();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
