import java.awt.Color;
import java.lang.reflect.Array;

public class palette {
	//Needs to have getter (and setter secondarily) for color gradients
	//Produces gradients by linear interpolation between color points with distances
	//Option for either end of gradient to always fade to black
	
	class Colors {
		int duration = 0;
		Color color = new Color(0,0,0);
		
		public Colors(int inputduration, Color inputcolor) {
			duration = inputduration;
			color = inputcolor;
		}
	}
		
	private Colors[] colors = {
			new Colors(200, new Color(1,1,1)), 
			new Colors(300, new Color(0,0,0)), 
			new Colors(400, new Color(1,1,1))
	};
						
	private int totalduration = calcDuration(colors);
	
	private Color[] pattern = new Color[totalduration];
	
	public Color[] patternfun() {
		int lastkey = 0;
		int keyindex = 0;
		int nextkeyindex = 1;
		int nextkey = colors[keyindex].duration;
		
		for(int i = 0; i < totalduration; i++) {
			float mix = (lastkey+nextkey)/(float)(i+lastkey);
			pattern[i] = colormix(colors[keyindex].color, colors[nextkeyindex].color, mix);
			
			if(i >= nextkey) {
				lastkey = nextkey;
				keyindex++;
				nextkey = colors[keyindex].duration;
				
				if(nextkeyindex != Array.getLength(colors)) {
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
	
	private Color colormix(Color c1, Color c2, float mix) {
		int r = (int)(c1.getRed()*mix+c2.getRed()*(1.0-mix));
		int g = (int)(c1.getGreen()*mix+c2.getGreen()*(1.0-mix));
		int b = (int)(c1.getBlue()*mix+c2.getBlue()*(1.0-mix));
		return new Color(r,g,b);
	}
	
	private int calcDuration(Colors[] inputarray) {
	//Calculate total duration
		int tempdur = 0;
		for(int i = 0; i < Array.getLength(inputarray); i++) {
			tempdur += inputarray[i].duration;
		}
		return tempdur;
	}
	
}