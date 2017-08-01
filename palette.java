import java.awt.Color;
import java.awt.image.BufferedImage;

import javafx.scene.effect.ColorAdjust;

public class palette {
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
	private int iterations = 200;
	//Limit affects appearance and to some degree computation time
	private int limit = 6;
	
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
	
	
}
