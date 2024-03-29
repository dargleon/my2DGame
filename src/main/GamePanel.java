package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	//SCREEN SETTINGS
	final int originalTileSize = 16; //16 x 16 tile
	final int scale = 3;
	final int tileSize = originalTileSize * scale; //48x48 tile
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	final int screenWidth = tileSize * maxScreenCol; // 768 pixels
	final int screenHeight = tileSize * maxScreenRow; // 578 pixels
	
	//FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	
	//Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4; // 4 pixels
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true); //This GamePanel can be focused to receive key input
	}
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

//	@Override
//	public void run() { //GameLoop with sleep method
//		while(gameThread != null ) {
//
//			double drawInterval = 1000000000/FPS; // 0.01666 seconds
//			double nextDrawTime = System.nanoTime() + drawInterval;
//			
//			// 1. UPDATE: Update information such as character positions
//			update();
//			// 2. DRAW: Draw the screen with the updated information
//			repaint();
//			
//			// 3. WAIT: Sleep the program the remaining time until the next frame
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime = remainingTime/1000000; //Convert nanoseconds to milliseconds
//				
//				if(remainingTime < 0) {
//					remainingTime = 0;
//				}
//				
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime += drawInterval;
//				
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//	}
	
	@Override
	public void run() { //GameLoop with Delta method
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime-lastTime)/drawInterval;
			timer += (currentTime-lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount ++;
			}
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer =0;
			}
		}
		
	}
	
	public void update() {
		if(keyH.upPressed == true) {
			playerY -= playerSpeed;
		}
		else if(keyH.downPressed == true) {
			playerY += playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX -=playerSpeed;
		}
		else if(keyH.rightPressed == true) {
			playerX += playerSpeed;
		}
		
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.white);
		g2.fillRect(playerX, playerY, tileSize, tileSize); //Draw a rectangle in the screen
		g2.dispose(); //Release system resources for graphics in this context
	}


	
}
