/*
AUTORES - ESTUDIANTES
 JHONATAN ADALID (320368)
 LORENZO ALDAO (307239)
*/
package obligatorio2parking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class VentanaMiniJuego extends JFrame implements KeyListener, ActionListener {
    
    // Estados del juego
    private enum GameState {
        MENU, PLAYING, PAUSED, GAME_OVER, INSTRUCTIONS
    }
    
    private GameState currentState = GameState.MENU;
    private Timer gameTimer;
    private Timer powerUpTimer;
    
    // Jugador
    private int playerX = 150;
    private final int playerY = 400;
    private int playerLane = 1; // 0, 1, 2
    private int lives = 3;
    private boolean invulnerable = false;
    private int invulnerabilityTimer = 0;
    
    // Juego
    private ArrayList<GameObject> obstacles = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private Random rand = new Random();
    private int score = 0;
    private int highScore = 0;
    private int level = 1;
    private int obstacleSpeed = 5;
    private int spawnRate = 60; // frames entre spawns
    private int frameCount = 0;
    
    // Power-ups
    private boolean shieldActive = false;
    private int shieldTimer = 0;
    private boolean slowMotion = false;
    private int slowMotionTimer = 0;
    
    // Gráficos
    private Image backgroundImg;
    private Font gameFont = new Font("Arial", Font.BOLD, 16);
    private Font titleFont = new Font("Arial", Font.BOLD, 32);
    
    public VentanaMiniJuego() {
        initializeGame(); 
    }
    
    private void initializeGame() {
        setTitle("Super Car Dodge - Advanced Edition");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        addKeyListener(this);
        setFocusable(true);
        
        gameTimer = new Timer(16, this); // ~60 FPS
        powerUpTimer = new Timer(5000, e -> spawnPowerUp()); // Power-up cada 5 segundos
        
        setVisible(true);
    }
    
    private void startGame() {
        currentState = GameState.PLAYING;
        resetGameVariables();
        gameTimer.start();
        powerUpTimer.start();
    }
    
    private void resetGameVariables() {
        playerX = 150;
        playerLane = 1;
        lives = 3;
        score = 0;
        level = 1;
        obstacleSpeed = 5;
        spawnRate = 60;
        frameCount = 0;
        obstacles.clear();
        powerUps.clear();
        invulnerable = false;
        shieldActive = false;
        slowMotion = false;
    }
    
    private void pauseGame() {
        if (currentState == GameState.PLAYING) {
            currentState = GameState.PAUSED;
            gameTimer.stop();
            powerUpTimer.stop();
        } else if (currentState == GameState.PAUSED) {
            currentState = GameState.PLAYING;
            gameTimer.start();
            powerUpTimer.start();
        }
    }
    
    private void gameOver() {
        currentState = GameState.GAME_OVER;
        gameTimer.stop();
        powerUpTimer.stop();
        if (score > highScore) {
            highScore = score;
        }
    }
    
    private void spawnObstacle() {
        int lane = rand.nextInt(3);
        int x = lane * 100 + 75;
        int type = rand.nextInt(3); // Diferentes tipos de obstáculos
        obstacles.add(new GameObject(x, -100, 50, 80, type));
    }
    
    private void spawnPowerUp() {
        if (currentState == GameState.PLAYING && rand.nextInt(3) == 0) {
            int lane = rand.nextInt(3);
            int x = lane * 100 + 75;
            int type = rand.nextInt(3); // 0: Shield, 1: Slow Motion, 2: Extra Life
            powerUps.add(new PowerUp(x, -50, 30, 30, type));
        }
    }
    
    @Override
    public void paint(Graphics g) {
        Image offImage = createImage(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) offImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch (currentState) {
            case MENU:
                drawMenu(g2);
                break;
            case PLAYING:
                drawGame(g2);
                break;
            case PAUSED:
                drawGame(g2);
                drawPauseOverlay(g2);
                break;
            case GAME_OVER:
                drawGameOver(g2);
                break;
            case INSTRUCTIONS:
                drawInstructions(g2);
                break;
        }
        
        g.drawImage(offImage, 0, 0, null);
    }
    
    private void drawMenu(Graphics2D g2) {
        // Fondo degradado
        GradientPaint gradient = new GradientPaint(0, 0, Color.DARK_GRAY, 0, getHeight(), Color.BLACK);
        g2.setPaint(gradient);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        // Título
        g2.setColor(Color.YELLOW);
        g2.setFont(titleFont);
        FontMetrics fm = g2.getFontMetrics();
        String title = "SUPER CAR DODGE";
        g2.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 150);
        
        // Opciones del menú
        g2.setFont(gameFont);
        g2.setColor(Color.WHITE);
        String[] options = {
            "Presiona ENTER para jugar",
            "Presiona I para instrucciones",
            "Presiona ESC para salir",
            "",
            "High Score: " + highScore
        };
        
        for (int i = 0; i < options.length; i++) {
            fm = g2.getFontMetrics();
            g2.drawString(options[i], (getWidth() - fm.stringWidth(options[i])) / 2, 250 + i * 30);
        }
    }
    
    private void drawGame(Graphics2D g2) {
        // Fondo de carretera
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        // Líneas de carril (animadas)
        g2.setColor(Color.WHITE);
        for (int i = 1; i < 3; i++) {
            for (int y = (frameCount % 40) - 40; y < getHeight(); y += 40) {
                g2.fillRect(i * 100 - 2, y, 4, 20);
            }
        }
        
        // Bordes de la carretera
        g2.setColor(Color.YELLOW);
        g2.fillRect(0, 0, 5, getHeight());
        g2.fillRect(295, 0, 5, getHeight());
        
        // Auto del jugador
        drawPlayer(g2);
        
        // Obstáculos
        for (GameObject obstacle : obstacles) {
            obstacle.draw(g2);
        }
        
        // Power-ups
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(g2);
        }
        
        // HUD
        drawHUD(g2);
    }
    
    private void drawPlayer(Graphics2D g2) {
        if (invulnerable && (invulnerabilityTimer / 5) % 2 == 0) {
            return; // Parpadeo cuando es invulnerable
        }
        
        if (shieldActive) {
            g2.setColor(Color.CYAN);
            g2.drawOval(playerX - 10, playerY - 10, 70, 120);
            g2.drawOval(playerX - 5, playerY - 5, 60, 110);
        }
        
        g2.setColor(Color.RED);
        g2.fillRect(playerX, playerY, 50, 100);
        g2.setColor(Color.RED);
        g2.fillRect(playerX + 5, playerY + 10, 40, 20);
        g2.fillRect(playerX + 5, playerY + 70, 40, 20);
        g2.setColor(Color.BLACK);
        g2.fillRect(playerX + 10, playerY + 35, 30, 30);
    }
    
    private void drawHUD(Graphics2D g2) {
        g2.setFont(gameFont);
        g2.setColor(Color.WHITE);
        
        // Información del juego
        g2.drawString("Score: " + score, 10, 30);
        g2.drawString("Level: " + level, 10, 50);
        g2.drawString("Lives: " + lives, 10, 70);
        
        // Power-ups activos
        if (shieldActive) {
            g2.setColor(Color.CYAN);
            g2.drawString("SHIELD: " + (shieldTimer / 60), 250, 30);
        }
        if (slowMotion) {
            g2.setColor(Color.BLUE);
            g2.drawString("SLOW-MO: " + (slowMotionTimer / 60), 250, 50);
        }
    }
    
    private void drawPauseOverlay(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 128));
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        g2.setColor(Color.WHITE);
        g2.setFont(titleFont);
        FontMetrics fm = g2.getFontMetrics();
        String pauseText = "PAUSED";
        g2.drawString(pauseText, (getWidth() - fm.stringWidth(pauseText)) / 2, getHeight() / 2);
        
        g2.setFont(gameFont);
        String resumeText = "Presiona P para continuar";
        fm = g2.getFontMetrics();
        g2.drawString(resumeText, (getWidth() - fm.stringWidth(resumeText)) / 2, getHeight() / 2 + 50);
    }
    
    private void drawGameOver(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        g2.setColor(Color.RED);
        g2.setFont(titleFont);
        FontMetrics fm = g2.getFontMetrics();
        String gameOverText = "GAME OVER";
        g2.drawString(gameOverText, (getWidth() - fm.stringWidth(gameOverText)) / 2, 200);
        
        g2.setColor(Color.WHITE);
        g2.setFont(gameFont);
        String[] gameOverInfo = {
            "Final Score: " + score,
            "High Score: " + highScore,
            "Level Reached: " + level,
            "",
            "Presiona R para reiniciar",
            "Presiona M para menú principal"
        };
        
        for (int i = 0; i < gameOverInfo.length; i++) {
            fm = g2.getFontMetrics();
            g2.drawString(gameOverInfo[i], (getWidth() - fm.stringWidth(gameOverInfo[i])) / 2, 280 + i * 25);
        }
    }
    
    private void drawInstructions(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        
        g2.setColor(Color.YELLOW);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();
        String title = "INSTRUCCIONES";
        g2.drawString(title, (getWidth() - fm.stringWidth(title)) / 2, 80);
        
        g2.setColor(Color.WHITE);
        g2.setFont(gameFont);
        String[] instructions = {
            "← → : Mover izquierda/derecha",
            "P : Pausar juego",
            "ESC : Salir",
            "",
            "POWER-UPS:",
            "🛡️ Escudo - Protección temporal",
            "⏰ Slow Motion - Ralentiza el tiempo",
            "❤️ Vida Extra - Recupera una vida",
            "",
            "¡Esquiva los obstáculos y consigue",
            "la puntuación más alta!",
            "",
            "Presiona M para volver al menú"
        };
        
        for (int i = 0; i < instructions.length; i++) {
            g2.drawString(instructions[i], 50, 130 + i * 25);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentState != GameState.PLAYING) return;
        
        frameCount++;
        updateGame();
        repaint();
    }
    
    private void updateGame() {
        // Actualizar timers
        if (invulnerable) {
            invulnerabilityTimer--;
            if (invulnerabilityTimer <= 0) {
                invulnerable = false;
            }
        }
        
        if (shieldActive) {
            shieldTimer--;
            if (shieldTimer <= 0) {
                shieldActive = false;
            }
        }
        
        if (slowMotion) {
            slowMotionTimer--;
            if (slowMotionTimer <= 0) {
                slowMotion = false;
            }
        }
        
        // Spawn obstáculos
        if (frameCount % spawnRate == 0) {
            spawnObstacle();
        }
        
        // Actualizar obstáculos
        int currentSpeed = slowMotion ? obstacleSpeed / 2 : obstacleSpeed;
        Iterator<GameObject> obstacleIt = obstacles.iterator();
        while (obstacleIt.hasNext()) {
            GameObject obstacle = obstacleIt.next();
            obstacle.y += currentSpeed;
            
            // Colisión con jugador
            if (!invulnerable && obstacle.intersects(new Rectangle(playerX, playerY, 50, 100))) {
                if (shieldActive) {
                    shieldActive = false;
                    shieldTimer = 0;
                } else {
                    lives--;
                    invulnerable = true;
                    invulnerabilityTimer = 120; // 2 segundos de invulnerabilidad
                    
                    if (lives <= 0) {
                        gameOver();
                        return;
                    }
                }
                obstacleIt.remove();
            }
            // Remover si sale de pantalla
            else if (obstacle.y > getHeight()) {
                obstacleIt.remove();
                score += 10;
                
                // Aumentar dificultad
                if (score % 200 == 0) {
                    level++;
                    if (obstacleSpeed < 12) obstacleSpeed++;
                    if (spawnRate > 20) spawnRate -= 2;
                }
            }
        }
        
        // Actualizar power-ups
        Iterator<PowerUp> powerUpIt = powerUps.iterator();
        while (powerUpIt.hasNext()) {
            PowerUp powerUp = powerUpIt.next();
            powerUp.y += currentSpeed;
            
            // Colisión con jugador
            if (powerUp.intersects(new Rectangle(playerX, playerY, 50, 100))) {
                activatePowerUp(powerUp.type);
                powerUpIt.remove();
            }
            // Remover si sale de pantalla
            else if (powerUp.y > getHeight()) {
                powerUpIt.remove();
            }
        }
    }
    
    private void activatePowerUp(int type) {
        switch (type) {
            case 0: // Shield
                shieldActive = true;
                shieldTimer = 300; // 5 segundos
                break;
            case 1: // Slow Motion
                slowMotion = true;
                slowMotionTimer = 180; // 3 segundos
                break;
            case 2: // Extra Life
                if (lives < 5) lives++;
                break;
        }
        score += 50; // Bonus por recoger power-up
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        switch (currentState) {
            case MENU:
                if (key == KeyEvent.VK_ENTER) {
                    startGame();
                } else if (key == KeyEvent.VK_I) {
                    currentState = GameState.INSTRUCTIONS;
                    repaint();
                } else if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                break;
                
            case PLAYING:
                if (key == KeyEvent.VK_LEFT && playerLane > 0) {
                    playerLane--;
                    playerX = playerLane * 100 + 75;
                } else if (key == KeyEvent.VK_RIGHT && playerLane < 2) {
                    playerLane++;
                    playerX = playerLane * 100 + 75;
                } else if (key == KeyEvent.VK_P) {
                    pauseGame();
                } else if (key == KeyEvent.VK_ESCAPE) {
                    currentState = GameState.MENU;
                    gameTimer.stop();
                    powerUpTimer.stop();
                    repaint();
                }
                break;
                
            case PAUSED:
                if (key == KeyEvent.VK_P) {
                    pauseGame();
                }
                break;
                
            case GAME_OVER:
                if (key == KeyEvent.VK_R) {
                    startGame();
                } else if (key == KeyEvent.VK_M) {
                    currentState = GameState.MENU;
                    repaint();
                }
                break;
                
            case INSTRUCTIONS:
                if (key == KeyEvent.VK_M) {
                    currentState = GameState.MENU;
                    repaint();
                }
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
    
    // Clase para objetos del juego
    private class GameObject {
        int x, y, width, height, type;
        
        GameObject(int x, int y, int width, int height, int type) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.type = type;
        }
        
        boolean intersects(Rectangle rect) {
            return new Rectangle(x, y, width, height).intersects(rect);
        }
        
        void draw(Graphics2D g2) {
            switch (type) {
                case 0: // Auto normal
                    g2.setColor(Color.BLUE);
                    break;
                case 1: // Camión
                    g2.setColor(Color.ORANGE);
                    height = 120;
                    break;
                case 2: // Auto rápido
                    g2.setColor(Color.GREEN);
                    break;
            }
            g2.fillRect(x, y, width, height);
            g2.setColor(Color.BLACK);
            g2.fillRect(x + 5, y + 10, width - 10, 15);
            g2.fillRect(x + 5, y + height - 25, width - 10, 15);
        }
    }
    
    // Clase para power-ups
    private class PowerUp {
        int x, y, width, height, type;
        
        PowerUp(int x, int y, int width, int height, int type) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.type = type;
        }
        
        boolean intersects(Rectangle rect) {
            return new Rectangle(x, y, width, height).intersects(rect);
        }
        
        void draw(Graphics2D g2) {
            switch (type) {
                case 0: // Shield
                    g2.setColor(Color.CYAN);
                    break;
                case 1: // Slow Motion
                    g2.setColor(Color.BLUE);
                    break;
                case 2: // Extra Life
                    g2.setColor(Color.PINK);
                    break;
            }
            g2.fillOval(x, y, width, height);
            g2.setColor(Color.WHITE);
            g2.drawOval(x, y, width, height);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new VentanaMiniJuego();
            }
        });
    }
}