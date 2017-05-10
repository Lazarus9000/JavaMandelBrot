import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application; 
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Group; 
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;  

public class JavafxSample extends Application { 
	 //Inititialize renderer
	 mandelCalc render = new mandelCalc(400,400);
	 //Convert from BufferedImage to javaFX image
	 Image image = SwingFXUtils.toFXImage(render.outputImage, null);
	 ImageView imageView = new ImageView();
	 
	 class recZoom extends TimerTask {

		    private final int x, y;
            private double zoom;

            recZoom ( int x, int y, double zoom )
		    {
		      this.x = x;
		      this.y = y;
		      this.zoom = zoom;
		    }

		    public void run() {
		    	//x = 
		    	render.zoom(x, y, zoom);
	  	        
	  	    	//Debug
	  	    	//System.out.println("mouse click detected! " + e.getButton() + " x: " + e.getX());
	  	        
	  	    	//Redraw
	  	        image = SwingFXUtils.toFXImage(render.outputImage, null);
	  	        imageView.setImage(image);
		    }
		}
	 
	 Timer timer = new Timer();
	 
//	 recZoom task = new recZoom()
//	 {
//	         public void run()
//	         {
//	        	 render.zoom(200, 200, 0.49);
//	            	  	        
//	  	    	//Debug
//	  	    	//System.out.println("mouse click detected! " + e.getButton() + " x: " + e.getX());
//	  	        
//	  	    	//Redraw
//	  	        image = SwingFXUtils.toFXImage(render.outputImage, null);
//	  	        imageView.setImage(image);
//	         }
//
//	 };
	 
	 //Initialization of application
	 public void start(Stage primaryStage) throws Exception {            
		 //Put image in viewer
		 imageView.setImage(image); 
		  
		 //Creating a Group object 
	     Group group = new Group(); 
	     
	     //Add image to group
	     group.getChildren().add(imageView);
	     
	     //Creating a Scene by passing the group object, height and width   
	     Scene scene = new Scene(group, 400, 400); 
	     
	     //Add listener for mouseclicks
	     scene.addEventFilter(MouseEvent.MOUSE_PRESSED,  mouseZoom);
	      
	     //Setting the title to Stage. 
	     primaryStage.setTitle("Mandelbrot Set"); 
	   
	     //Adding the scene to Stage 
	     primaryStage.setScene(scene); 
	     
	     //Maintain fixed size window
	     primaryStage.setHeight(400);
	     primaryStage.setWidth(400);
	     primaryStage.setResizable(false);
	     
	     //Displaying the contents of the stage 
	     primaryStage.show(); 
   }    
	 
   public static void main(String args[]){          
      launch(args);     
   }         
   
   
   //Handler for mouseclicks for navigating the set
   EventHandler<MouseEvent> mouseZoom = new EventHandler<MouseEvent>() {
	   public void handle(MouseEvent e) {
 	    	switch(e.getButton()) {
           
 	    	//Zoom in on left mousebutton
           case PRIMARY: 	
        	   timer.scheduleAtFixedRate(new recZoom((int)e.getX(), (int)e.getY(), 0.49),0,100);
           	//render.zoom((int)e.getX(), (int)e.getY(), 0.49);
           	break;
           
           //Navigate on middle mousebutton (basicly a zoom without magnification)
           case MIDDLE:   	
           	render.zoom((int)e.getX(), (int)e.getY(), 0.50);
           	break;
           	
           //Zoom out on right mousebutton                
           case SECONDARY:	
           	render.zoom((int)e.getX(), (int)e.getY(), 0.51);
           	break;
           	
           default:					
           	System.out.println("Mouse event not recognized");
           	//No need to rerender and draw exit method
           	return;
       
 	    	}
 	        
 	    	//Debug
 	    	//System.out.println("mouse click detected! " + e.getButton() + " x: " + e.getX());
 	        
 	    	//Redraw
 	        image = SwingFXUtils.toFXImage(render.outputImage, null);
 	        imageView.setImage(image);
 	      }
   };
   
} 