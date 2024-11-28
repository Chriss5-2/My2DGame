package main;

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
    public void run() {

        while(gameThread != null){

            //UPDATE THE COORDINATES
            update();

            //DRAW THE NEW COORDINATES IN THE SCREEN
            repaint();
            System.out.println("Running");
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
