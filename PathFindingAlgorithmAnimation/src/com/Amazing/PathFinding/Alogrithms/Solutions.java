package com.Amazing.PathFinding.Alogrithms;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class Solutions extends Level{
	
	protected int closeCol = 0xe0edff;
	protected int openCol = 0xcfd3ff;
	
	public Solutions(int width, int height, int tileSize) {
		super(width, height, tileSize);
	}

	public boolean checkPoints(int[] source, int[] target)
	{
		if(source[0]<0 || source[1]<0 || target[0]<0 || target[1]<0)
		{
			JOptionPane.showMessageDialog(null, "Please specify both source and target position!");
			return false;
		}
		else
		{
//			System.out.println("source: "+source[0]+","+source[1]+" target: "+target[0]+","+target[1]);
			return true;
		}
	}
	
	
	public ArrayList<ArrayList<Integer>> findPath(int[][] graph, int[] source, int[] target, boolean anim)
	{
		return null;
	}

	public void update(int xp, int yp, int color)
	{
		yp = yp + Game.barTileHeight;
		for(int x = 0;x<tileSize;x++)
		{
			int xa = x + xp*tileSize;
			for(int y = 0;y<tileSize;y++)
			{
				int ya = y + yp*tileSize;
				if(xa<0 || xa>=this.width || ya<0 || ya>=this.height) continue;
				pixels[xa + ya * width] = color;
			}
		}
	}
	
	
}
