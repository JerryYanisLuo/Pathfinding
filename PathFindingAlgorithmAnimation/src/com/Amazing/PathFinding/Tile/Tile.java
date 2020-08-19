package com.Amazing.PathFinding.Tile;

public class Tile {

	private int color;
	private boolean solid;
	
	public Tile(int color, boolean solid)
	{
		this.color = color;
		this.solid = solid;
	}
	
	public int getColor() {
		return color;
	}

	public boolean isSolid() {
		return solid;
	}
}
