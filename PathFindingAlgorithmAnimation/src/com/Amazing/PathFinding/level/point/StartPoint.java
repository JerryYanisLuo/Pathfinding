package com.Amazing.PathFinding.level.point;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.level.Level;

public class StartPoint extends Point{

	public int x = -1, y = -1;
	
	
	public StartPoint(int width, int height, int tileSize) {
		super(width, height, tileSize);
		this.activeState = 3;
		this.pointTile = Level.startTile;
	}
	
	public void updateCoordinate()
	{
		if(!(xp==-1 && yp==-1))
		{
			x = xp;
			y = yp - Game.barTileHeight;
		}
	}

}
