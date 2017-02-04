package org.lolyerk.natsu;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteStorage {
	private static SpriteStorage store = new SpriteStorage();
	@SuppressWarnings("rawtypes")//gonna be untyped so i can shove anything I want in there.
	private HashMap sprites = new HashMap(); 
	
	public static SpriteStorage get(){
		return store;
	}
	
	@SuppressWarnings("unchecked")//still: untyped hashmap
	public Sprite getSprite(String link){
		// if we've already got the sprite in the cache
		// then just return the existing version
		if (sprites.get(link) != null) {
			return (Sprite) sprites.get(link);
		}
		
		BufferedImage source = null;
		try{
			URL url = this.getClass().getClassLoader().getResource(link);
			
			if(url==null){
				System.out.println("Unable to load sprite from "+link);
				return null;
			}
			
			source = ImageIO.read(url);
		}
		catch(IOException e){
			System.out.println("Sprite failed to load from "+link+" due to "+e);
			return null;
		}
		
		//alocate GPU memory
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		Image image = gc.createCompatibleImage(source.getWidth(),source.getHeight(),Transparency.BITMASK);
		
		image.getGraphics().drawImage(source,0,0,null);//draw it out so it's buffered (i guess, not very good at this whole "gpu accelerated" thing)
		
		Sprite sprite = new Sprite(image);
		sprites.put(link,sprite);
		
		return sprite;
	}

}
