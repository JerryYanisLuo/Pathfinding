package com.Amazing.PathFinding.screen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.Amazing.PathFinding.Game;
import com.Amazing.PathFinding.Alogrithms.AStar;
import com.Amazing.PathFinding.Alogrithms.BFS;
import com.Amazing.PathFinding.Alogrithms.DFS;
import com.Amazing.PathFinding.Alogrithms.Dijkstra;
import com.Amazing.PathFinding.Alogrithms.GBFS;
import com.Amazing.PathFinding.Maze.Alogrithms.BinaryTree;
import com.Amazing.PathFinding.Maze.Alogrithms.Eller;
import com.Amazing.PathFinding.Maze.Alogrithms.Kruskal;
import com.Amazing.PathFinding.Maze.Alogrithms.MazeDFS;
import com.Amazing.PathFinding.Maze.Alogrithms.Prim;
import com.Amazing.PathFinding.Maze.Alogrithms.RecDiv;
import com.Amazing.PathFinding.Maze.Alogrithms.Sidewinder;
import com.Amazing.PathFinding.level.Level;
import com.Amazing.PathFinding.level.point.BombPoint;
import com.Amazing.PathFinding.level.point.EndPoint;
import com.Amazing.PathFinding.level.point.StartPoint;

public class UI {

	private Screen screen;
	private Random random = new Random();
	
	private JPanel panel;
	private JButton btClear, btDraw, btErase;
	private JButton btStart, btEnd, btBomb;
	
	private JComboBox<String> pathSearchAlgs;
	private JButton btSearchAlg;
	private static int searchAlgState = 0;
	
	private JComboBox<String> mazeGenAlgs;
	private JButton btMazeAlg;
	private static int mazeAlgState = 0;

	public static JLabel pathlength = new JLabel("Path length", JLabel.CENTER);
	public static JLabel timecomsume = new JLabel("Time(ms)",JLabel.CENTER);
	public static JLabel path = new JLabel("-----",JLabel.CENTER);
	public static JLabel time = new JLabel("-----",JLabel.CENTER);
	
	private ArrayList<JButton> btns = new ArrayList<>();
	private ArrayList<JComboBox<String>> comboxes = new ArrayList<>();
	
	private int backGroundColor = 0x003b8f;
	
	public UI(Screen screen)
	{
		this.screen = screen;
		
		panel = new JPanel(new GridBagLayout());
		btClear = new JButton("Clear");
		btns.add(btClear);
		
		btDraw = new JButton("Draw");
		btns.add(btDraw);
		
		btErase = new JButton("Erase");
		btns.add(btErase);
		
		btStart = new JButton("Add Start Point");
		btns.add(btStart);
		
		btEnd = new JButton("Add End Point");
		btns.add(btEnd);
		
		btBomb = new JButton("Add Bomb Point");
		btns.add(btBomb);
		
		pathSearchAlgs=new JComboBox<>();
		pathSearchAlgs.addItem("A* Search");
		pathSearchAlgs.addItem("Breadth-First Search");
		pathSearchAlgs.addItem("Depth-First Search");
		pathSearchAlgs.addItem("Dijkstra¡¯s Algorithm");
		pathSearchAlgs.addItem("Greedy Best First Search");
		//more algorithms...
		
		btSearchAlg = new JButton("Visualize Algorithm");
		btns.add(btSearchAlg);
		
		mazeGenAlgs=new JComboBox<>();
		mazeGenAlgs.addItem("Depth-First Search");
		mazeGenAlgs.addItem("Prim's Algorithm");
		mazeGenAlgs.addItem("Recursive Division");
		mazeGenAlgs.addItem("Eller's Algorithm");
		mazeGenAlgs.addItem("Kruskal's Algorithm");
		mazeGenAlgs.addItem("Binary Tree Algorithm");
		mazeGenAlgs.addItem("Sidewinder Algorithm");
		//more algorithms...
		
		btMazeAlg = new JButton("Generate Maze");
		btns.add(btMazeAlg);

		comboxes.add(pathSearchAlgs);
		comboxes.add(mazeGenAlgs);
		DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
	    listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
	    for(JComboBox<String> combox : comboxes)
	    {
	    	combox.setOpaque(false);
	    	combox.setRenderer(listRenderer);
	    	combox.setBackground(new Color(backGroundColor));
	    	combox.setForeground(Color.WHITE);
	    	combox.setUI(new BasicComboBoxUI() {
			    protected JButton createArrowButton() {
			        return new JButton() {

						private static final long serialVersionUID = 1L;

						public int getWidth() {
			                return 0;
			            }
			        };
			    }
			});
	    	
	    	combox.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					combox.setBackground(new Color(0xad0000));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					combox.setBackground(new Color(backGroundColor));
				}
			});
	    }
	    
		
		for(JButton btn : btns)
		{
			btn.setBackground(new Color(backGroundColor));
			btn.setBorderPainted(false);
			btn.setMargin(new Insets(4,4,4,4));
			btn.setForeground(Color.WHITE);
			btn.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					btn.setBackground(new Color(0x26d4a2));
				}

				@Override
				public void mouseExited(MouseEvent e) {
					btn.setBackground(new Color(0x003b8f));
				}
			});
		}

		panel.setBackground(new Color(backGroundColor));
		panel.setSize(Game.getWindowWidth(), Game.getBarHeight());
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gridConstraints = new GridBagConstraints();
		gridConstraints.anchor = GridBagConstraints.CENTER;
		gridConstraints.fill = GridBagConstraints.BOTH;
		gridConstraints.weightx = 50;
		gridConstraints.weighty = 100;
		
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 0;
		panel.add(btDraw,gridConstraints);
		
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 0;
		panel.add(btClear,gridConstraints);
		
		gridConstraints.gridx = 2;
		gridConstraints.gridy = 0;
		panel.add(btErase,gridConstraints);
		
		
		gridConstraints.gridx = 0;
		gridConstraints.gridy = 1;
		panel.add(btStart,gridConstraints);
		
		gridConstraints.gridx = 1;
		gridConstraints.gridy = 1;
		panel.add(btEnd,gridConstraints);
		
		gridConstraints.gridx = 2;
		gridConstraints.gridy = 1;
		panel.add(btBomb,gridConstraints);
		
		gridConstraints.gridx = 3;
		gridConstraints.gridy = 1;
		panel.add(pathSearchAlgs,gridConstraints);
		
		gridConstraints.gridx = 3;
		gridConstraints.gridy = 0;
		panel.add(btSearchAlg,gridConstraints);
		
		gridConstraints.gridx = 4;
		gridConstraints.gridy = 1;
		panel.add(mazeGenAlgs,gridConstraints);
		
		gridConstraints.gridx = 4;
		gridConstraints.gridy = 0;
		panel.add(btMazeAlg,gridConstraints);
		
		gridConstraints.gridx = 5;
		gridConstraints.gridy = 0;
		panel.add(pathlength,gridConstraints);
		
		gridConstraints.gridx = 5;
		gridConstraints.gridy = 1;
		panel.add(path,gridConstraints);
		
		gridConstraints.gridx = 6;
		gridConstraints.gridy = 0;
		panel.add(timecomsume,gridConstraints);
		
		gridConstraints.gridx = 6;
		gridConstraints.gridy = 1;
		panel.add(time,gridConstraints);
		

		pathlength.setForeground(Color.WHITE);
		timecomsume.setForeground(Color.WHITE);
		path.setForeground(Color.WHITE);
		time.setForeground(Color.WHITE);

		addButtonListener();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public void addButtonListener()
	{
		btClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level.state = 2;
				path.setText("-----");
				time.setText("-----");
			}
		});
		
		
		btDraw.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level.state = 0;
				path.setText("-----");
				time.setText("-----");
			}
		});
		
		btErase.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level.state = 1;
				path.setText("-----");
				time.setText("-----");
			}
		});
		
		btStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level.state = 3;
				path.setText("-----");
				time.setText("-----");
				if(screen.stps.size()==0 || screen.stps.getLast().x>0 && screen.stps.getLast().y>0)
				{
					screen.stps.addLast(new StartPoint(Screen.width, Screen.height, Screen.tileSize));
				}
			}
		});
		
		btEnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level.state = 4;
				path.setText("-----");
				time.setText("-----");
				if(screen.edps.size()==0 || screen.edps.getLast().x>0 && screen.edps.getLast().y>0)
				{
					screen.edps.addLast(new EndPoint(Screen.width, Screen.height, Screen.tileSize));
				}
			}
		});
		
		btBomb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Level.state = 5;
				path.setText("-----");
				time.setText("-----");
				if(screen.bbps.size()==0 || screen.bbps.getLast().x>0 && screen.bbps.getLast().y>0)
				{
					screen.bbps.add(new BombPoint(Screen.width, Screen.height, Screen.tileSize));
				}
			}
		});
		
		
		pathSearchAlgs.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					searchAlgState = pathSearchAlgs.getSelectedIndex();
				}
			}
			
		});
		
		
		mazeGenAlgs.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange()==ItemEvent.SELECTED)
				{
					mazeAlgState = mazeGenAlgs.getSelectedIndex();
				}
			}
			
		});
		
		
		btSearchAlg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Screen.solved = false;
				Screen.seed = random.nextInt();
				long before = System.currentTimeMillis();
				if(searchAlgState==0)
				{
					screen.SearchPath(new AStar(Screen.width, Screen.height, Screen.tileSize), true);
				}
				else if(searchAlgState==1)
				{
					screen.SearchPath(new BFS(Screen.width, Screen.height, Screen.tileSize), true);
				}
				else if(searchAlgState==2)
				{
					screen.SearchPath(new DFS(Screen.width, Screen.height, Screen.tileSize), true);
				}
				else if(searchAlgState==3)
				{
					screen.SearchPath(new Dijkstra(Screen.width, Screen.height, Screen.tileSize), true);
				}
				else if(searchAlgState==4)
				{
					screen.SearchPath(new GBFS(Screen.width, Screen.height, Screen.tileSize), true);
				}
				long after = System.currentTimeMillis();
				int len = 0;
				for(ArrayList<ArrayList<Integer>> p : screen.ultimateAns) len+=p.size();
				path.setText(len+"");
				time.setText((after-before)+"");
			}
		});
		
		
		btMazeAlg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				path.setText("-----");
				time.setText("-----");
				
				if(mazeAlgState==0)
				{
					screen.generateMaze(new MazeDFS(Screen.width, Screen.height, Screen.tileSize));
				}
				else if(mazeAlgState==1)
				{
					screen.generateMaze(new Prim(Screen.width, Screen.height, Screen.tileSize));
				}
				else if(mazeAlgState==2)
				{
					screen.generateMaze(new RecDiv(Screen.width, Screen.height, Screen.tileSize));
				}
				else if(mazeAlgState==3)
				{
					screen.generateMaze(new Eller(Screen.width, Screen.height, Screen.tileSize));
				}
				else if(mazeAlgState==4)
				{
					screen.generateMaze(new Kruskal(Screen.width, Screen.height, Screen.tileSize));
				}
				else if(mazeAlgState==5)
				{
					screen.generateMaze(new BinaryTree(Screen.width, Screen.height, Screen.tileSize));
				}
				else if(mazeAlgState==6)
				{
					screen.generateMaze(new Sidewinder(Screen.width, Screen.height, Screen.tileSize));
				}
			}
		});
	}
}
