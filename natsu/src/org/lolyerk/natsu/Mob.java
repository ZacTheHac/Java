package org.lolyerk.natsu;

import com.fathzer.soft.javaluator.*;

import java.awt.Graphics;


public class Mob {
	private int x = 0;
	private int y = 0;
	private Sprite sprite;
	private String yEqu = "0";
	private String xEqu = "1280-t";
	private long lifeTime = 0;
	StaticVariableSet<Double> variables = new StaticVariableSet<Double>();
	DoubleEvaluator eval = new DoubleEvaluator();
	
	public void update(long frameTime){
		lifeTime+=frameTime;
		variables.set("t", (double)lifeTime);
		//TODO: check if this mob needs to move, and move it

			x = (int) Math.round(eval.evaluate(xEqu, variables));//I may be using an external library, but fuck you?
			y = (int) Math.round(eval.evaluate(yEqu, variables));
	}
	
	public void draw(Graphics g){
		sprite.draw(g,(int) x,(int) y);
	}
	
	public Mob(String link,int x,int y) {
		this.sprite = SpriteStorage.get().getSprite(link);
		this.x = x;
		this.y = y;
	}

}
