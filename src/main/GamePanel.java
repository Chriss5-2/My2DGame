package main;

import com.sun.security.jgss.GSSUtil;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEEN SETINGS
    final int originalTileSize=16;
    final int scale=3;

    final int tileSize= originalTileSize*scale; //Escala de 48x48
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; //768
    final int screenHeight = tileSize * maxScreenRow;//576

    //FPS
    int FPS=60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //Posiciones
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
//    public void run() {
//        double drawInterval=1000000000/FPS; // 0.01666 seconds
//        double nextDrawTime = System.nanoTime() + drawInterval;
//        while(gameThread != null){
//
//            update();
//
//            repaint();
//
//            try{
//                double remainingTime = nextDrawTime - System.nanoTime();
//                remainingTime = remainingTime/1000000;
//
//                if(remainingTime<0){
//                    remainingTime=0;
//
//                }
//                Thread.sleep((long) remainingTime);
//
//                nextDrawTime += drawInterval;
//
//            }catch(InterruptedException e){
//                e.printStackTrace();
//            }
//        }
//    }
public void run(){
        double drawInterval = 1000000000/FPS; // 0.01666 seconds
        double delta = 0;
        long lastTime= System.nanoTime();
        long currentTime;
        long timer=0;
        long drawCount = 0;
        while(gameThread != null){
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer+= (currentTime - lastTime);
            lastTime = currentTime;

            if(delta>=1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer>=1000000000){
                System.out.println("FPS: "+drawCount);
                drawCount=0;
                timer=0;
            }
        }

    }
    public void update(){
        if(keyH.upPressed==true){
            playerY -= playerSpeed;
        }
        else if(keyH.downPressed==true){
            playerY += playerSpeed;
        }
        else if(keyH.leftPressed==true){
            playerX -= playerSpeed;
        }
        else if(keyH.rightPressed==true){
            playerX += playerSpeed;
        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.white);

        g2d.fillRect(playerX, playerY, tileSize, tileSize);

        g2d.dispose();
    }
}
