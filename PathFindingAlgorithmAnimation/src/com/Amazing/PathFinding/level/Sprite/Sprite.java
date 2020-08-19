package com.Amazing.PathFinding.level.Sprite;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.Amazing.PathFinding.Game;


public class Sprite{

	private String path;
	public int SIZE;
	public int[] pixels;
	
	public static Sprite arrow = new Sprite("res/texture/arrow.png", Game.tileSize);
	
	public Sprite(String path, int size)
	{
		this.path = path;
		SIZE = size;
		pixels = new int[SIZE*SIZE];
		load();
	}
	
	
	private void load()
	{
		try {
			BufferedImage image = ImageIO.read(new FileInputStream(path));
			image.getRGB(0, 0, this.SIZE, this.SIZE, pixels, 0, this.SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
