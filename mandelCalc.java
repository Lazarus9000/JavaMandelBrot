import java.awt.Color;
import java.awt.image.BufferedImage;

import javafx.scene.effect.ColorAdjust;

public class mandelCalc {
	//consider implementing using bigdecimal - requires reimplementing a lot of arithmetic though
	//private BigDecimal bxmin = new BigDecimal("0.0");
	//private double xmin = (double) -0.8, xmax = (double) -0.7;
	private double xmin = (double) -2.0, xmax = (double) 1;
	//private double ymin = (double) -0.3, ymax = (double) -0.2;
	private  double ymin = (double) -1.5, ymax = (double) 1.5;
	
	private int imgwidth = 800;
	private int imgheight = 800;
	
	public BufferedImage outputImage;
	public BufferedImage tempout;
	
	//These control the math calculations
	//higher iterations giver longer computation
	private int iterations = 100;
	//Limit affects appearance and to some degree computation time
	private int limit = 600;
	
	private int bins = 12000;
	//These are used for math, declared here to save time on declarations
	private double re = 0;
	private double im = 0;
	private float fre = 0;
	private float fim = 0;
	private double x_new = 0;
	private float fx_new = 0;
	private int iter = 0;
	
	
	private double escape = 0;
	
	//For letting the wold now the current zoom
	private double extscale = 0;
	
	//Precision toggle
	private boolean precision = false;
	
	public boolean getPrecision() {
		return precision;
	}
	
	public void setPrecision(boolean prec) {
		precision = prec;
	}
	
	
	//Main constructor
	public mandelCalc(int width, int height) {
		imgwidth = width;
		imgheight = height;
		tempout = new BufferedImage(imgwidth, imgheight, BufferedImage.TYPE_INT_RGB);
		outputImage = new BufferedImage(imgwidth, imgheight, BufferedImage.TYPE_INT_RGB);
        drawMandel();
	}
	
	//Overloaded constructor with default value
	public mandelCalc() {
		this(600,600);
	}
	
	//The math
	//This is where the magic happens
	public int mandelmath(double x, double y) {
		if(precision) {
			re = x;
			im = y;
			x_new = 0;
			
			iter = 0;
		    
	        //Iterate the calculation below until max iterations have been met, 
		    //or the result is out of bounds
		    while(iter < iterations && x*x+y*y <= limit) {
		    	x_new = x*x - y*y + re;
				y = 2*x*y + im;
				x = x_new;
				iter++;
			}
		    
		    escape = x*x+y*y;
		    
		} else {
			fre = (float)x;
			fim = (float)y;
			float fx = (float)x;
			float fy = (float)y;
			fx_new = 0;
			
			iter = 0;
		    
	        //Iterate the calculation below until max iterations have been met, 
		    //or the result is out of bounds
		    while(iter < iterations && fx*fx+fy*fy <= limit) {
		    	fx_new = fx*fx - fy*fy + fre;
				fy = 2*fx*fy + fim;
				fx = fx_new;
				iter++;
		    }
		    
		    escape = fx*fx+fy*fy;
	    }
	    
		
		
	    //Return amount of iterations it took to exceed the limit
		return iter;
		
	}
	
	public int mandelmath(float x, float y) {
		return mandelmath((double)x, (double)y);
	}
	
	public void redraw() {
		drawMandel();
	}
	
	public void zoom(double x, double y, double scale) {
		//Inputs are the center of the zoom and the scale
		//scale is defined by 0.5, being no zoom
		//+0.5 zooms out
		//-0.5 zooms in
		
		//Find current 'scale'
		double xscale = Math.abs(xmax - xmin);
		double yscale = Math.abs(ymax - ymin);
		
		extscale = xscale;
		
		//Find clicked point in the coordinate system
		double newCenterx = ((double)x/imgwidth)*xscale+xmin;
		double newCentery = ((double)y/imgheight)*yscale+ymin;
		
		//Rescale
		xscale = xscale * scale;
		yscale = yscale * scale;
		
		//calculate new coordinates
		xmin = newCenterx - xscale;
		xmax = newCenterx + xscale;
		ymin = newCentery - yscale;
		ymax = newCentery + yscale;
		
		//Draw new image using the coordinates
		drawMandel();
		
	}

	public double getScale() {
		return extscale;
	}
	
	private void drawMandel() {
        //final BufferedImage scaled = new BufferedImage(
        //    fg.getWidth()/2,fg.getHeight()/2,BufferedImage.TYPE_INT_RGB);
         
        double tempx, tempy;
        int mandelResult = 0;
        float R, G, B;
		
        //Used for histogram coloring
        int[][] preRender = new int[imgwidth][imgheight];
        float[][] fpreRender = new float[imgwidth][imgheight];
        //Histogram
    	//int[] histogram = new int[iterations+1];
    	int[] histogram = new int[bins];
        
        //Loop through all pixels
        for ( int rc = 0; rc < imgheight; rc++ ) {
        	  for ( int cc = 0; cc < imgwidth; cc++ ) {
        		  //Convert pixel value to value in the coordinate space
        		  tempx = ((double)cc/imgwidth)*Math.abs(xmax-xmin)+xmin;
        		  tempy = ((double)rc/imgheight)*Math.abs(ymax-ymin)+ymin;
        		  
        		  //Do the mandelbrot calculation for the current point
        		  mandelResult = mandelmath(tempx,tempy);
        		  double mandelResultd = 0;
        		  
        		  /* smooth shading */
        		  //https://stackoverflow.com/questions/369438/smooth-spectrum-for-mandelbrot-set-rendering
        		  double sresult = mandelResult + 1 - Math.log(Math.log(escape))/Math.log(2);
        		  if ( mandelResult < iterations ) {
        			  sresult = sresult/(float)iterations;
        		  } else {
        			  sresult = 1.0f;
        		  }
        		  //Add to histogram
        		  histogram[(int)(sresult*bins)-1]++;
        		  //Save result
        		  preRender[cc][rc] = (int)sresult;
        		  fpreRender[cc][rc] = (float)sresult;
        	  }
        }
        
        //inspired by https://en.wikipedia.org/wiki/Mandelbrot_set#Histogram_coloring
        int total = 0;
        int[] sumhist = new int[bins];
        for (int i = 0; i < bins-1; i ++) {
		  total += histogram[i];
		  sumhist[i] = histogram[i];
		  if(i > 0) {
			  sumhist[i] += sumhist[i-1];
		  }
		}
		
		for ( int rc = 0; rc < imgheight; rc++ ) {
      	  for ( int cc = 0; cc < imgwidth; cc++ ) {
      		  	  
      		      //Final step of histogram coloring
      		      double histcolor = sumhist[(int)(fpreRender[rc][cc]*bins)-1] /  (double)total;
        		  
      		      //set the pixel
        		  
      		      //A bit psychedelic
      		      int testrgb = Color.HSBtoRGB(1.0f, (float)histcolor, (float)histcolor);
      		      
      		      //Scale color to greyscale values
        		  int farv = (int)(histcolor*255);
        		  Color as = new Color(farv,farv,farv);
        		  tempout.setRGB(rc, cc, testrgb);
        	  }
        }
        
        //Despeckle - removes single black pixels
        for ( int rc = 1; rc < imgheight-1; rc++ ) {
        	  for ( int cc = 1; cc < imgwidth-1; cc++ ) {
        		  if(tempout.getRGB(rc, cc) == Color.BLACK.getRGB()) {
        			  if (tempout.getRGB(rc+1, cc) != Color.BLACK.getRGB() ||
    					  tempout.getRGB(rc-1, cc) != Color.BLACK.getRGB() ||
						  tempout.getRGB(rc, cc+1) != Color.BLACK.getRGB() ||
						  tempout.getRGB(rc, cc-1) != Color.BLACK.getRGB()) {
        				 
        				  Color meancolor = new Color(tempout.getRGB(rc+1, cc));
        				  int sumr = meancolor.getRed();
        				  int sumg = meancolor.getGreen();
        				  int sumb = meancolor.getBlue();
        				  
        				  meancolor = new Color(tempout.getRGB(rc-1, cc));
        				  sumr += meancolor.getRed();
        				  sumg += meancolor.getGreen();
        				  sumb += meancolor.getBlue();
        				  
        				  meancolor = new Color(tempout.getRGB(rc, cc+1));
        				  sumr += meancolor.getRed();
        				  sumg += meancolor.getGreen();
        				  sumb += meancolor.getBlue();
        				  
        				  meancolor = new Color(tempout.getRGB(rc, cc-1));
        				  sumr += meancolor.getRed();
        				  sumg += meancolor.getGreen();
        				  sumb += meancolor.getBlue();
        				  
        				  Color resultcolor = new Color(sumr/4, sumg/4, sumb/4);
        				  
        				  outputImage.setRGB(rc, cc, resultcolor.getRGB());
        			  } else {
        				  outputImage.setRGB(rc, cc,  tempout.getRGB(rc, cc));
        			  }
        		 
        		  
	        	  } else {
	        		  outputImage.setRGB(rc, cc, tempout.getRGB(rc, cc));
	        	  }
        	  }
        }
	}

	//Maps a value from one range to another (and caps it within said range)	
	//Inspired by Arduinos map() - https://www.arduino.cc/en/reference/map
	float mapValue (float value, float fromLow, float fromHigh, float toLow, float toHigh) {
	   //Cap result at top
	   float result = Math.min(value, fromHigh);
	   
	   //cap result at bottom
	   result = Math.max(result, fromLow);
	   
	   //Calculate ranges
	   float oldRange =  fromHigh - fromLow;
	   float newRange = toHigh - toLow;
	   
	   //Normalize
	   result = (result-fromLow)/oldRange;
	   
	   //Scale to new range
	   result = (result+toLow)*newRange;
	   
	   return result;
	}
}
