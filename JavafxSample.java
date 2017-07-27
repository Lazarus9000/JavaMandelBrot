import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JavafxSample extends Application {
	// Inititialize renderer
	mandelCalc render = new mandelCalc(400, 400);
	// Convert from BufferedImage to javaFX image
	Image image = SwingFXUtils.toFXImage(render.outputImage, null);
	ImageView imageView = new ImageView();
	Timer timerin;
	Timer timerout;
	Button button = new Button();
    

	class recZoom extends TimerTask {

		private final int x, y;
		private double zoom;
		public boolean running = false; 
		
		recZoom(int x, int y, double zoom) {
			this.x = x;
			this.y = y;
			this.zoom = zoom;
		}

		public void run() {
			// x =

			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int x = (int) b.getX();
			int y = (int) b.getY();
			// System.out.print(x + ", ");
			// System.out.println(y);
			Bounds c = imageView.localToScreen(imageView.getBoundsInLocal());
			// System.out.print("window: " + boundsInScreen.getMinX() + ", ");
			// System.out.println(boundsInScreen.getMinY());
			int lx = (int) (x - c.getMinX());
			int ly = (int) (y - c.getMinY());

			double dist = b.distance(c.getWidth() / 2 + c.getMinX(), c.getHeight() / 2 + c.getMinY());

			double maxdist = Math.sqrt(Math.pow(c.getWidth() / 2, 2) + Math.pow(c.getHeight() / 2, 2));
			double scaled = dist / maxdist;
			// System.out.println(dist + ", " + scaled);

			double halfW = c.getWidth() / 2;
			double halfH = c.getHeight() / 2;
			
			//Increases mobility at higher zoom
			double movement = 4+Math.pow(render.getScale(), 2);
			
			double sx = halfW + (lx - halfW) / halfW * movement;
			double sy = halfH + (ly - halfH) / halfH * movement;
			System.out.println(sx + ", " + sy + "- scale: " + render.getScale());
			// Debug
			// System.out.println("mouse click detected! " + e.getButton() + "
			// x: " + e.getX());
			render.zoom(sx, sy, zoom);
			// Redraw
			image = SwingFXUtils.toFXImage(render.outputImage, null);
			imageView.setImage(image);
		}
	}

	// Initialization of application
	public void start(Stage primaryStage) throws Exception {
		// Put image in viewer
		imageView.setImage(image);

		// Creating a Group object
		Group group = new Group();

			
		// Add image to group
		group.getChildren().add(imageView);
		button.setText("OK");
	    button.setFont(new Font("Tahoma", 24));
	    group.getChildren().add(button);
	    // Creating a Scene by passing the group object, height and width
		Scene scene = new Scene(group, 400, 400);

		// Add listener for mouseclicks
		imageView.addEventFilter(MouseEvent.MOUSE_PRESSED, mouseZoomstart);
		imageView.addEventFilter(MouseEvent.MOUSE_RELEASED, mouseZoomstop);

		// Setting the title to Stage.
		primaryStage.setTitle("Mandelbrot Set");

		// Adding the scene to Stage
		primaryStage.setScene(scene);

		// Maintain fixed size window
		primaryStage.setHeight(400);
		primaryStage.setWidth(400);
		primaryStage.setResizable(false);

		// Displaying the contents of the stage
		primaryStage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}

	// Handler for mouseclicks for navigating the set
	EventHandler<MouseEvent> mouseZoomstart = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {

			switch (e.getButton()) {

			// Zoom in on left mousebutton
			case PRIMARY:
				System.out.println("left mouse down");
				timerin = new Timer();
				timerin.scheduleAtFixedRate(new recZoom((int) e.getX(), (int) e.getY(), 0.49), 0, 50);
				break;
			case SECONDARY:
				System.out.println("right mouse down");
				timerout = new Timer();
				timerout.scheduleAtFixedRate(new recZoom((int) e.getX(), (int) e.getY(), 0.51), 0, 50);
				break;

			default:
				System.out.println("Mouse event not recognized");
				return;
			}

			// Debug
			// System.out.println("mouse click detected! " + e.getButton() + "
			// x: " + e.getX());

			// Redraw
			image = SwingFXUtils.toFXImage(render.outputImage, null);
			imageView.setImage(image);
		}
	};

	EventHandler<MouseEvent> mouseZoomstop = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {

			switch (e.getButton()) {

			// Zoom in on left mousebutton
			case PRIMARY:
				timerin.cancel();
				timerin.purge();
				System.out.println("left mouse up");
				break;
			case SECONDARY:
				timerout.cancel();
				timerout.purge();
				System.out.println("right mouse up");
				break;

			default:
				System.out.println("Mouse event not recognized");
				return;
			}

			// Redraw
			image = SwingFXUtils.toFXImage(render.outputImage, null);
			imageView.setImage(image);
		}
	};

}