package com.Amazing.PathFinding.level.map;

import java.util.ArrayList;
import java.util.Arrays;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;
import com.Amazing.PathFinding.level.Sprite.Sprite;

public class Arrow extends Level{

	private int arrow_id = 0;
	private ArrayList<ArrayList<Integer>> ans;
	
	public Arrow(int width, int height, int tileSize, ArrayList<ArrayList<Integer>> ans) {
		super(width, height, tileSize);
		this.ans = ans;
	}
	
	
	public void soluPathArrow(boolean anim)
	{
		if(ans==null || ans.size()==0 || !anim) return;
		
		arrow_id = arrow_id % ans.size();
		
		int xtile = ans.get(arrow_id).get(0);
		int ytile = ans.get(arrow_id).get(1) + Game.getBarHeight() / Game.tileSize;
		
		Arrays.fill(pixels, 0xff00ff);
		
		for(int x = 0;x<tileSize;x++)
		{
			int xa = x + xtile*tileSize;
			for(int y = 0;y<tileSize;y++)
			{
				int ya = y + ytile*tileSize;
				if(xa<0 || xa>=this.width || ya<0 || ya>=this.height) continue;
				int col = Sprite.arrow.pixels[x + y * tileSize];				
				if(col!=0xffff00ff) pixels[xa + ya * width] =  col;
			}
		}
		
		arrow_id++;
	}
}
