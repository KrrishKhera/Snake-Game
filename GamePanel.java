import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

class GamePanel extends JPanel implements ActionListener {
   static final int SCREEN_WIDTH = 750;
   static final int SCREEN_HEIGHT = 750;
   static final int UNIT_SIZE = 25;
   static final int GAME_UNITS = 22500;
   static final int DELAY = 80;
   final int[] x = new int[22500];
   final int[] y = new int[22500];
   int bodyParts = 6;
   int appleseaten;
   int appleX;
   int appleY;
   char direction = 'R';
   boolean running = true;
   Timer timer;
   Random random = new Random();

   GamePanel() {
      this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
      this.setBackground(Color.black);
      this.setFocusable(true);
      this.addKeyListener(new MyKeyAdapter(this));
      this.startGame();
   }

   public void startGame() {
      this.newApple();
      this.running = true;
      this.timer = new Timer(80, this);
      this.timer.start();
   }

   public void paintComponent(Graphics var1) {
      super.paintComponent(var1);
      this.draw(var1);
   }

   public void draw(Graphics var1) {
      if (this.running) {
         var1.setColor(Color.red);
         var1.fillOval(this.appleX, this.appleY, 25, 25);

         for(int var2 = 0; var2 < this.bodyParts; ++var2) {
            if (var2 == 0) {
               var1.setColor(Color.green);
               var1.fillRect(this.x[var2], this.y[var2], 25, 25);
            } else {
               var1.setColor(new Color(this.random.nextInt(255), this.random.nextInt(255), this.random.nextInt(255)));
               var1.fillRect(this.x[var2], this.y[var2], 25, 25);
            }
         }

         var1.setColor(Color.red);
         var1.setFont(new Font("Ink Free", 1, 45));
         FontMetrics var3 = this.getFontMetrics(var1.getFont());
         var1.drawString("Your Score :" + this.appleseaten, (750 - var3.stringWidth("Your Score :" + this.appleseaten)) / 2, var1.getFont().getSize());
      } else {
         this.gameOver(var1);
      }

   }

   public void newApple() {
      this.appleX = this.random.nextInt(30) * 25;
      this.appleY = this.random.nextInt(30) * 25;
   }

   public void move() {
      for(int var1 = this.bodyParts; var1 > 0; --var1) {
         this.x[var1] = this.x[var1 - 1];
         this.y[var1] = this.y[var1 - 1];
      }

      switch (this.direction) {
         case 'D':
            this.y[0] += 25;
            break;
         case 'L':
            this.x[0] -= 25;
            break;
         case 'R':
            this.x[0] += 25;
            break;
         case 'U':
            this.y[0] -= 25;
      }

   }

   public void checkApple() {
      if (this.x[0] == this.appleX && this.y[0] == this.appleY) {
         ++this.bodyParts;
         ++this.appleseaten;
         this.newApple();
      }

   }

   public void checkCollisions() {
      for(int var1 = this.bodyParts; var1 > 0; --var1) {
         if (this.x[0] == this.x[var1] && this.y[0] == this.y[var1]) {
            this.running = false;
         }
      }

      if (this.x[0] < 0) {
         this.running = false;
      }

      if (this.x[0] > 750) {
         this.running = false;
      }

      if (this.y[0] < 0) {
         this.running = false;
      }

      if (this.y[0] > 750) {
         this.running = false;
      }

      if (!this.running) {
         this.timer.stop();
      }

   }

   public void gameOver(Graphics var1) {
      var1.setColor(Color.red);
      var1.setFont(new Font("Ink Free", 1, 45));
      FontMetrics var2 = this.getFontMetrics(var1.getFont());
      var1.drawString("Your Score :" + this.appleseaten, (750 - var2.stringWidth("Your Score :" + this.appleseaten)) / 2, var1.getFont().getSize());
      var1.setColor(Color.red);
      var1.setFont(new Font("Ink Free", 1, 75));
      FontMetrics var3 = this.getFontMetrics(var1.getFont());
      var1.drawString("Game Over", (750 - var3.stringWidth("Game Over")) / 2, 375);
   }

   public void actionPerformed(ActionEvent var1) {
      if (this.running) {
         this.move();
         this.checkApple();
         this.checkCollisions();
      }

      this.repaint();
   }
}
