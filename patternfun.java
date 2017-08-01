import java.awt.Color;

public class patternfun {
	//Needs to have getter (and setter secondarily) for color gradients
	//Produces gradients by linear interpolation between color points with distances
	//Option for either end of gradient to always fade to black
	
	private class Colors {
		int duration = 0;
		Color color = new Color();
		
		public void Colors(int inputduration, Color inputcolor) {
			duration = inputduration;
			color = inputcolor;
		}
	};
		
	private Colors[3] colors = [new Colors(200, new Color(1,1,1),
						new Colors(300, new Color(0,0,0),
						new Colors(400, new Color(1,1,1)];
						
	private totalduration = calcDuration(colors);
	
	private Color[totalduration] pattern;
	
	public Color[] patternfun() {
		int lastkey = 0;
		int keyindex = 0;
		int nextkeyindex = 1;
		int nextkey = colors[keyindex].duration;
		
		for(int i = 0; i < totalduration, i++) {
			float mix = (lastkey+nextkey)/(float)(i+lastkey);
			pattern[i] = colormix(colors[keyindex], colors[nextkeyindex], mix);
			
			if(i >= nextkey) {
				lastkey = nextkey;
				keyindex++;
				nextkey = colors[keyindex].duration;
				
				if(nextkeyindex != colors.length()) {
					nextkeyindex++;
				} else {
					nextkeyindex=0;
				}
			}
		}
		
		return pattern;
	}
	
	public Color[] getPattern() {
		return pattern;
	}
	
	private color colormix(Color c1, Color c2, float mix) {
		int r = c1.getRed()*mix+c2.getRed()*(1.0-mix);
		int g = c1.getGreen()*mix+c2.getGreen()*(1.0-mix);
		int b = c1.getBlue()*mix+c2.getBlue()*(1.0-mix);
		return new Color(r,g,b);
	}
	
	private int calcDuration(Colors[] inputarray) {
	//Calculate total duration
		for(int i = 0; i < inputarray.length(), i00) {
			totalduration += inputarray[i].duration;
		}
	}
	
	//These are used for math, declared here to save time on declarations
	private double re = 0;
	private double im = 0;
	private float fre = 0;
	private float fim = 0;
	private double x_new = 0;
	private float fx_new = 0;
	private int iter = 0;
	
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
		outputImage = new BufferedImage(imgwidth, imgheight, BufferedImage.TYPE_INT_RGB);
        drawMandel();
	}
	
	//Overloaded constructor with default value
	public mandelCalc() {
		this(600,600);
	}
}
