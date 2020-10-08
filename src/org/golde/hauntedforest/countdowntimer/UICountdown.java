package org.golde.hauntedforest.countdowntimer;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UICountdown extends JFrame {

	private static final long serialVersionUID = -4088291878103254052L;

	private Image bgImage;
	private Font customFont;
	private final long timeToHit;

	public UICountdown(long timeToHit) {

		this.timeToHit = timeToHit;

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		setSize(600, 400);

		//full screen window
		this.setUndecorated(true);
		this.setResizable(false);
		for ( Window w : Window.getWindows() ) {
			GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow( w );
		}

		//press ESC to quit
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
			}
		});

		try {
			this.bgImage = ImageIO.read(new File("res/background.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//blank mouse cursor
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		getContentPane().setCursor(blankCursor);


		

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				repaint();
			}
		}, 100, 100);

		//font.ttf or use default font
		try {
			customFont = Font.createFont(Font.PLAIN, new File("res/font.ttf")).deriveFont(300f);
		}
		catch(FontFormatException | IOException e) {
			e.printStackTrace();
			customFont = new Font("Arial", Font.BOLD, 160);
		}
		
		this.setVisible(true);
	}

	@Override
	public void paint(Graphics graphicsDontUse) {

		BufferedImage buff = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = buff.createGraphics();

		//https://stackoverflow.com/questions/31536952/how-to-fix-text-quality-in-java-graphics
		Map<?, ?> desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");

		if (desktopHints != null) {
			g2d.setRenderingHints(desktopHints);
		}

		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		if(bgImage != null) {
			g2d.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
		}

		long remainingTime = timeToHit - System.currentTimeMillis();
		
		long seconds = remainingTime / 1000; //convert to seconds
		g2d.setColor(Color.red);
		g2d.setFont(customFont);
		if(seconds > 0) {
			String text = String.format("%d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, (seconds % 60));
			drawCenteredString(g2d, text, new Rectangle(this.getWidth() / 2 - 70, this.getHeight() / 2 + 200, 100, 100), customFont);
			//g2d.drawString(text, 629, 862);
		}
		else {
			drawCenteredString(g2d, "Starting Soon", new Rectangle(this.getWidth() / 2 - 70, this.getHeight() / 2 + 200, 100, 100), customFont);
		}
		
		

		
		

		/////////////////////////////////////////////////////////////////////////////////////

		graphicsDontUse.clearRect(0, 0, getWidth(), getHeight());
		graphicsDontUse.drawImage(buff, 0, 0, null);

	}
	private static void drawCenteredString(Graphics2D g2d, String text, Rectangle rect, Font font) {
		FontMetrics metrics = g2d.getFontMetrics(font);
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		System.out.println(x + " " + y);
		g2d.drawString(text, x, y);
	}

}
