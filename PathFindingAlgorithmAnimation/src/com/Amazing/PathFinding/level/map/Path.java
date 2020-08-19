package com.Amazing.PathFinding.level.map;

import java.util.ArrayList;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class Path extends Level{
	
	public Path(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	
	public void generateSoluPath(ArrayList<ArrayList<Integer>> ans, boolean anim, int col)
	{
		if(ans==null || ans.size()==0)
		{
//			System.out.println("No path found");
			return;
		}

		for(int i= 0;i<ans.size();i++)
		{
			int xtile = ans.get(i).get(0);
			int ytile = ans.get(i).get(1) + Game.getBarHeight() / Game.tileSize;
			
			for(int x = 0;x<tileSize;x++)
			{
				int xa = x + xtile*tileSize;
				for(int y = 0;y<tileSize;y++)
				{
					int ya = y + ytile*tileSize;
					if(xa<0 || xa>=this.width || ya<0 || ya>=this.height) continue;
					pixels[xa + ya * width] =  col;
				}
			}
			
			if(anim)
			{
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
}
