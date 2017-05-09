import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import java.math.BigDecimal;


public class mandelbrotRender {
	//Create mandelbrot renderer
	mandelCalc render = new mandelCalc();//(400,400);

	//Local variables used for presenting result
	private JLabel label;
	private Graphics g;
	private BufferedImage bg; 
	
	private MouseAdapter navigator = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            
//        	Debug - print local mousecoordinates and for performance measurement
//        	System.out.println("a" + e.getX() + ", " + e.getY());
        	long now = System.nanoTime();
            
        	switch(e.getButton()) {
	            //Zoom in on left mousebutton
                case MouseEvent.BUTTON1: 	render.zoom(e.getX(), e.getY(), 0.25);
                							break;
                //Navigate on middle mousebutton (basicly a zoom without magnification)
                case MouseEvent.BUTTON2:   	render.zoom(e.getX(), e.getY(), 0.50);
                							break;
//              Zoom out on right mousebutton                
                case MouseEvent.BUTTON3:	render.zoom(e.getX(), e.getY(), 0.75);
                							break;
                default:					System.out.println("Mouse event not recognized");
                							//No need to rerender and draw exit method
                							return;
            }

//        	Debug - for performance measurement          
            long time = System.nanoTime();
            System.out.println("Rendering took " + ((float)(time-now)/1000000000 + "s"));

//           Finally redraw image on label
            redraw(render.outputImage);

//        	Debug - for performance measurement            
            long drawtime = System.nanoTime();
            System.out.println("Redraw took " + ((float)(drawtime-time)/1000000000 + "s"));
        }
    };
	
	private void redraw(BufferedImage output) {
		//Redraw the image on the label
		g = bg.getGraphics();
        g.drawImage(output,0,0,output.getWidth(),output.getHeight(),null);
        g.dispose();
        
        label.repaint();
	}
	
	mandelbrotRender() {
    	//initiate program
		//Draw an initial image 
		bg = render.outputImage;
		label = new JLabel(new ImageIcon(bg));
		
		redraw(render.outputImage);
		
       //Listen for clicks to zoom on the label
        label.addMouseListener(navigator);

        JOptionPane.showMessageDialog(null, label);
    }
    
    public static void main(String[] args) throws Exception {
    	new mandelbrotRender();
    }
}