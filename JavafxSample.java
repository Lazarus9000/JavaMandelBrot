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
   //@Overrideasdf
	 Image image;
	 mandelCalc render = new mandelCalc(400,400);;
	 ImageView imageView = new ImageView();
	 
	 
	 public void start(Stage primaryStage) throws Exception {            
	   image = SwingFXUtils.toFXImage(render.outputImage, null);
	   imageView.setImage(image);
	      
	      
	   //creating a Group object 
      Group group = new Group(); 
       
       group.getChildren().add(imageView);
      //Creating a Scene by passing the group object, height and width   
      Scene scene = new Scene(group ,400, 400); 
      
      scene.addEventFilter(MouseEvent.MOUSE_PRESSED,  new EventHandler<MouseEvent>() {
  	    
  	    public void handle(MouseEvent e) {
  	    	switch(e.getButton()) {
            
  	    	//Zoom in on left mousebutton
            case PRIMARY: 	
            	render.zoom((int)e.getX(), (int)e.getY(), 0.25);
            	break;
            
            //Navigate on middle mousebutton (basicly a zoom without magnification)
            case MIDDLE:   	
            	render.zoom((int)e.getX(), (int)e.getY(), 0.50);
            	break;
            	
//          Zoom out on right mousebutton                
            case SECONDARY:	
            	render.zoom((int)e.getX(), (int)e.getY(), 0.75);
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
  	    
  	    
    });
      
      //Setting the title to Stage. 
      primaryStage.setTitle("Sample Application"); 
   
      //Adding the scene to Stage 
      primaryStage.setScene(scene); 
       
      //Displaying the contents of the stage 
      primaryStage.show(); 
   }    
   public static void main(String args[]){          
      launch(args);     
   }         
   
   
} 