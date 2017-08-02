import java.awt.Color;
import java.lang.reflect.Array;
import java.util.Arrays;

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
			new Colors(100, new Color(255,0,0)), 
			new Colors(200, new Color(0,255,0)), 
			new Colors(300, new Color(0,0,255))
	};
						
	private int totalduration = calcDuration(colors);
	
	private Color[] pattern = new Color[totalduration];
	
	public palette() {
		int lastkey = 0;
		int keyindex = 0;
		int nextkeyindex = 1;
		int nextkey = colors[keyindex].duration;
		
		for(int i = 0; i < totalduration; i++) {
			float mix = (float)(i-lastkey)/(nextkey);
			pattern[i] = colormix(colors[keyindex].color, colors[nextkeyindex].color, mix);
			
			if(i-lastkey >= nextkey) {
				lastkey += nextkey;
				keyindex++;
				nextkey = colors[keyindex].duration;
				
				if(nextkeyindex != Array.getLength(colors)-1) {
					nextkeyindex++;
				} else {
					nextkeyindex=0;
				}
			}
		}
		
	}
	
	public Color[] getPattern() {
		return pattern;
	}
	
	public Color[] get256(int step) {
		int remainder = step%totalduration;
		int excess = totalduration - remainder - 256;
		//Check if next 256 values are within the generated pattern
		if(excess > 0) {
			return Arrays.copyOfRange(pattern, remainder, 256+remainder);
		} else {
		//The next 256 values needs to loop the pattern
			Color[] part1 = Arrays.copyOfRange(pattern, remainder, totalduration);
			Color[] part2 = Arrays.copyOfRange(pattern, 0, Math.abs(excess));
			Color[] both = new Color[256];
			System.arraycopy(part1, 0, both, 0, part1.length);
	        System.arraycopy(part2, 0, both, part1.length, part2.length);

			return both;
		}
	}
	
	private Color colormix(Color c1, Color c2, float mix) {
		int r = (int)(c1.getRed()*(1.0-mix)+c2.getRed()*(mix));
		int g = (int)(c1.getGreen()*(1.0-mix)+c2.getGreen()*(mix));
		int b = (int)(c1.getBlue()*(1.0-mix)+c2.getBlue()*(mix));
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