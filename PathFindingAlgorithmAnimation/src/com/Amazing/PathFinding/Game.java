package com.Amazing.PathFinding;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.Amazing.PathFinding.input.Mouse;
import com.Amazing.PathFinding.screen.Screen;
import com.Amazing.PathFinding.screen.UI;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public static final int tileSize = 16;
	private static final int width = tileSize*16;
	private static final int height = tileSize*9;
	private static final int barHeight = tileSize*1;
	private static int scale = 80 / tileSize;
	public static final int barTileHeight = barHeight*scale / tileSize;
	
	private static String title = "Amazing Pathfinding";
	private Thread thread;
	private JFrame frame;
	private UI gui;
	
	
	private boolean running = false;
	
	private BufferedImage image = new BufferedImage(getWindowWidth(), getWindowHeight(), BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;

	public Game() {
		Dimension size = new Dimension(getWindowWidth(), getWindowHeight());
		setPreferredSize(size);
		screen = new Screen(getWindowWidth(), getWindowHeight(), tileSize);
		frame = new JFrame();
		gui = new UI(screen);
		frame.add(gui.getPanel());
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}

	public synchronized void start() {
		this.running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		this.running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (running) {
			update();
			render();
		}
		stop();
	}
	
	public void update()
	{
		screen.update();
		
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs==null)
		{
			// the parameter 3 means that we have 2 extra buffers to store
			// speed improvement
			// while it's not good to make 4 or 10
			createBufferStrategy(3);
			return;
		}
		
		screen.render();
		
		for(int i = 0;i<pixels.length;i++)
		{
			pixels[i] = screen.getPixels()[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}
	
	
	public static int getWindowWidth()
	{
		return width*scale;
	}
	
	public static int getWindowHeight()
	{
		return height*scale;
	}
	
	public static int getBarHeight()
	{
		return barHeight*scale;
	}
	
	

	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}

}
