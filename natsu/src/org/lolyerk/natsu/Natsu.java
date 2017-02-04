package org.lolyerk.natsu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
//import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Natsu extends Canvas {

	private static final long serialVersionUID = 1L;//woo, version 1.
	private BufferStrategy strategy;
	private boolean running = true;
	//private ArrayList<Mob> mobs = new ArrayList<Mob>();

	public static void main(String[] args) {
		Natsu T = new Natsu();
		
		T.Loop();
		
	}

	public Natsu(){
		JFrame container = new JFrame("テックスのなつ ");
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(1280,720));//1080p master race?
		panel.setLayout(null);
		setBounds(0,0,1280,720);
		panel.add(this);
		setIgnoreRepaint(true);
		container.pack();
		container.setResizable(true);
		container.setVisible(true);
		//simple enough to show a blank window, right?
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		//we want it to actually stop the program if the window is closed
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		
		
	}
	
	public void Loop(){
		long lastLoopTime = System.currentTimeMillis();
		//boolean loopOn = true;
		long lastFPSTime = System.currentTimeMillis();
		int numOfFrames = 0;
		int sumOfFrameTimes = 0;
		
		int startR = (int) 0;
		int startG = (int) 0;
		int startB = (int) 0;
		int endR = (int) 200;
		int endG = (int) 200;
		int endB = (int) 200;
		int colorSkipSize = 1;
		boolean startGoingUpR = true;
		boolean startGoingUpG = true;
		boolean endGoingUpR = true;
		boolean endGoingUpG = true;
		
		
		while(running){
			long frameTime = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			try{
				
				if(System.currentTimeMillis()-lastFPSTime >=1000){
					float FPS = (1000/((float)sumOfFrameTimes/(float)numOfFrames));//i made it a bunch of floats so it wouldn't break for high FPS
					//int FPS = (int) 1000/(sumOfFrameTimes/numOfFrames); //so i just cap the FPS at ~100
					//funny thing: due to errors: the first formula returns 99FPS while the 2nd returns 100
					System.out.print(String.valueOf(FPS).substring(0, 5) + " FPS ");
					System.out.println("Color: "+startR+","+startG+","+startB+" to "+endR+","+endG+","+endB);
					
					lastFPSTime = System.currentTimeMillis();
					sumOfFrameTimes=0;
					numOfFrames=0;//reset the counters when I post the avg FPS for that second
				}
				else{
					sumOfFrameTimes += frameTime;
					numOfFrames++;//increment these values if it's not time to display it yet
				}
			}
			catch(Exception ex){
				//do nothing. it means nothing to me. this usually happens on the first frame.
				System.out.println(ex.toString());
			}
			
			
			Graphics2D graph = (Graphics2D) strategy.getDrawGraphics();
			//lets make it make a gradient
			
			if(startGoingUpR){
				startR+=colorSkipSize;
			}
			else{
				startR-=colorSkipSize;
			}
			
			if(endGoingUpR){
				endR+=colorSkipSize;
			}
			else{
				endR-=colorSkipSize;
			}
			
			if(startR > 255){
				startR = 255;
				if(startGoingUpG){
					startG+=colorSkipSize;
				}
				else{
					startG-=colorSkipSize;
				}
				startGoingUpR=false;
			}
			if(startR < 0){
				startR=0;
				if(startGoingUpG){
					startG+=colorSkipSize;
				}
				else{
					startG-=colorSkipSize;
				}
				startGoingUpR=true;
			}
			
			if(startG>255){
				startG = 255;
				startGoingUpG = false;
				startB+=colorSkipSize;

			}
			if(startG < 0){
				startG = 0;
				startB += colorSkipSize;
				startGoingUpG = true;
			}
			
			if(startB>255){
				startR = 0;
				startG = 0;
				startB = 0;
				System.out.println("Done!");
			}
			
			if(endR > 255){
				endR = 255;
				if(endGoingUpG){
					endG+=colorSkipSize;
				}
				else{
					endG-=colorSkipSize;
				}
				endGoingUpR = false;
			}
			if(endR < 0){
				endR = 0;
				if(endGoingUpG){
					endG+=colorSkipSize;
				}
				else{
					endG-=colorSkipSize;
				}
				endGoingUpR = true;
			}
			
			if(endG>255){
				endG = 255;
				endGoingUpG = false;
				endB+=colorSkipSize;
			}
			if(endG<0){
				endG = 0;
				endGoingUpG = true;
				endB+=colorSkipSize;
			}
			if(endB>255){
				endR = 0;
				endG = 0;
				endB = 0;
			}
			
			int lastStepR = 0;
			int lastStepG = 0;
			int lastStepB = 0;
			float stepsizeR = 1280f/Math.abs(startR - endR);
			float stepsizeG = 1280f/Math.abs(startG - endG);
			float stepsizeB = 1280f/Math.abs(startB - endB);
			Color colour = new Color(startR,startG,startB);
			
			for(int i = 0; i<=1280; i++){
				try{
				if((i-lastStepR)>=stepsizeR){
					if(startR>endR){
						colour = new Color(colour.getRed()-1,colour.getGreen(),colour.getBlue());
					}
					else{
						colour = new Color(colour.getRed()+1,colour.getGreen(),colour.getBlue());
					}
					lastStepR = i;
				}
				if((i-lastStepG)>=stepsizeG){
					if(startG>endG){
						colour = new Color(colour.getRed(),colour.getGreen()-1,colour.getBlue());
					}
					else{
						colour = new Color(colour.getRed(),colour.getGreen()+1,colour.getBlue());
					}
					lastStepG = i;
				}
				if((i-lastStepB)>=stepsizeB){
					if(startB>endB){
						colour = new Color(colour.getRed(),colour.getGreen(),colour.getBlue()-1);
					}
					else{
						colour = new Color(colour.getRed(),colour.getGreen(),colour.getBlue()+1);
					}
					lastStepB = i;
				}
				graph.setColor(colour);
				graph.fillRect(i, 0, i+1, 720);
				}
				catch(Exception e){
					System.out.println(e.toString());
				}
			}
			graph.dispose();//get rid of the current frame
			strategy.show();//draw the next frame
			/*
			for (int i=0;i<mobs.size();i++) {
				Mob mob = (Mob) mobs.get(i);
				
				mob.update(frameTime);
			}//tell the mobs that they can update (if need be, a lot will be static)
			for (int i=0;i<mobs.size();i++) {
				Mob mob = (Mob) mobs.get(i);
				
				mob.draw(graph);//send over the graphics pane I'm using so it knows where to draw to.
			}
			*/
			
			
			long time = System.currentTimeMillis() - lastLoopTime;
			int targetFPS = 60;
			int targetSleep = 1000/targetFPS;
			if(time<=targetSleep){
				try { Thread.sleep(targetSleep-time); } catch (Exception e) {} //slow it down JUST a bit (to ~100FPS), don't want it wildly eating cpu and GPU time
				//even though making it sleep makes the fps drop, oh well.
				//limit it to 100 fps, so only sleep if it took more than 10 ms
			}
			
		}
	}

}
