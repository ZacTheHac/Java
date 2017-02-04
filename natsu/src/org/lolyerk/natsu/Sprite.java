package org.lolyerk.natsu;

import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	private Image img;
	
	public Sprite(Image image){
		this.img = image;
	}
	
	public int getWidth(){
		return img.getWidth(null);
	}
	
	public int getHeight(){
		return img.getHeight(null);
	}
	
	/**
	 * Draw the sprite onto the graphics context provided
	 * 
	 * @param g The graphics context on which to draw the sprite
	 * @param x The x location at which to draw the sprite
	 * @param y The y location at which to draw the sprite
	 */
	public void draw(Graphics g,int x,int y) {
		g.drawImage(img,x,y,null);
	}
	
	public void draw(Graphics g, int x, int y, Float scale){
		g.drawImage(img, x, y, ((int)Math.round(img.getWidth(null)*scale)), ((int)Math.round(img.getHeight(null)*scale)), null);
	}
	
	public void draw(Graphics g, int x, int y, int Width, int Height){
		g.drawImage(img, x, y, Width, Height, null);
	}
	
	public void draw(Graphics g, int x, int y, int WidthOrHeight, char WH){
		int Width = 0;
		int Height = 0;
		double multiplier = 1;
		if(WH == 'W'){
			//double imgRatio = img.getHeight(null)/img.getWidth(null);
			multiplier = WidthOrHeight/img.getWidth(null);
			
			Width = WidthOrHeight;
			Height = Math.round(Math.round(img.getHeight(null)*multiplier));//rounding it from a double, to float, to int.
			
			
		}
		else if(WH == 'H'){
			multiplier = WidthOrHeight/img.getHeight(null);
			
			Height = WidthOrHeight;
			Width = Math.round(Math.round(img.getWidth(null)*multiplier));//rounding it from a double, to float, to int.
		}
		else{
			throw new IllegalArgumentException("WH can be W or H, to denote scaling my width or height, respectively");
		}
		g.drawImage(img, x, y, Width, Height, null);
	}
	
	public void draw(Graphics g, int x, int y, int Width){
		double multiplier = Width/img.getWidth(null);
		int Height = Math.round(Math.round(img.getHeight(null)*multiplier));//rounding it from a double, to float, to int.
		
		g.drawImage(img, x, y, Width, Height, null);
	}

}
