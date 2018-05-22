package main;

import java.io.IOException;

import com.sixlegs.png.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.util.Arrays;

public class PNGConvert {

	public static void main(String[] args) {
		PngImage png = new PngImage();
		int[] pixels = null;

		JFileChooser fileChooser = new JFileChooser(); //start setting up a file dialog
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png", "png");//set up a filter that only allows png (I left "ping" in to show myself how it works)
		fileChooser.setFileFilter(filter);//file dialog uses filter
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop/eyes"));//go to the folder I'll want to be in
		int result = fileChooser.showOpenDialog(fileChooser);
		//open the file chooser to the desktop and record if they picked a file or hit cancel

		if (result == JFileChooser.APPROVE_OPTION) {
			//if they picked a file
			try {
				java.awt.image.BufferedImage image = png.read(fileChooser.getSelectedFile());
				int picHeight = png.getHeight();
				int picWidth = png.getWidth();
				pixels = new int[picWidth*picHeight*4];
				//width x height x 4 ints per pixel
				image.getData().getPixels(0,0,picWidth,picHeight,pixels);
				//reads all the pixels into the array "pixels"
				
				int[][][] imagePx = new int[picWidth][picHeight][3];
				for(int y = 0; y < picHeight; y++){
					for(int x = 0; x < picWidth; x++){
						int pixelIndex = ((y*picWidth)+x)*4;
						int[] RGBA = new int[4];
						int[] RGB = new int[3];
						
						RGBA[0] = pixels[pixelIndex];
						RGBA[1] = pixels[pixelIndex+1];
						RGBA[2] = pixels[pixelIndex+2];
						RGBA[3] = pixels[pixelIndex+3];
						
						RGB = convertToNonAlpha(RGBA);
						
						imagePx[x][y] = RGB;
					}
				}
				//convert every pixel into its own array of just RGB, and organize it like the original picture
				printConvertedImage(imagePx);
				//System.out.println(getNthFrame(fileChooser.getSelectedFile(),301));
				ConvertToCode(imagePx, fileChooser.getSelectedFile().getName());



			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Well SOMETHING went wrong...");
				return;
			}
		}
		else{
			System.err.println("Hey fucknut, I need a file.");
			return;
		}

	}
	
	public static String ConvertToCode(int[][][] Picture, String name){
		final int MaxColors = 10;
		String code = "";
		int[] black = {0,0,0};
		int width = Picture.length;
		int height = Picture[0].length;
		int[][] colors = new int[width*height][3];//maximum possible number of colors needed. 
		int colorArrayPointer = 0;
		int[][] ColorPositions = new int[MaxColors][width*height];//allow for all different colors or a solid color image
		
		//null them all out
		for(int i = 0; i<MaxColors; i++){
			for(int j = 0; j<width*height; j++){
				ColorPositions[i][j] = -1;
			}
		}
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int[] color = Picture[j][i];
				if(Arrays.equals(color,black)){
					continue;//we don't care about black
				}
				int colorIndex = ColorIndex(color,colors);
				if(colorIndex == -1){
					//add the color
					colors[colorArrayPointer] = color;
					colorIndex = colorArrayPointer;
					colorArrayPointer++;
					if(colorArrayPointer >= MaxColors){
						System.err.println("Too many unique colors. Only "+MaxColors+" are allowed. Exiting.");
						return "";
					}
				}
				//add the point to the list
				for(int k = 0; k< ColorPositions[colorIndex].length; k++){
					if(ColorPositions[colorIndex][k] != -1)
						continue;
					else
						ColorPositions[colorIndex][k] = ((i*Picture.length)+(j+1))-1;//calculate back to the led index
					break;
				}
			}
		}
		
		//okay, at this point, I have all the colors stripped out, and all of the indexes of them. In the instance of eye2, this compresses the number of numbers to 6% - an amazing savings! Only 50% on test2, but that's unrealistic 
		//God, I sound like I'm marketing myself...
		//now I need to make it produce code...
		//let's optimize the arrays
		int NumberOfColors = colorArrayPointer;
		int[] NumberOfPositions = new int[NumberOfColors];
		//for bragging rights
		int totalRecordedPositions = 0;
		int originalStorageCost = 0;
		int finalStorageCost = 0;
		///for bragging rights
		
		for(int i = 0; i < NumberOfColors; i++){
			for(int j = 0; j < width*height; j++){
				if(ColorPositions[i][j] == -1){
					NumberOfPositions[i] = j;
					break;
				}
				else
					totalRecordedPositions++;
			}
		}
		int[][] OptimizedColors = new int[NumberOfColors][3];
		int[][] OptimizedColorPositions = new int[NumberOfColors][];
		for(int i =0; i<NumberOfColors;i++){
			OptimizedColorPositions[i] = new int[NumberOfPositions[i]];
			//sets up a jagged array.
			OptimizedColors[i] = colors[i];
			//copies the colors to the new array
			for(int j =0; j<NumberOfPositions[i];j++){
				OptimizedColorPositions[i][j] = ColorPositions[i][j];
			}//copy the positions over
		}
		
		originalStorageCost = width*height*4;
		finalStorageCost = (NumberOfColors*4)+totalRecordedPositions;
		System.out.println("After Optimization, only "+finalStorageCost+" numbers are stored, vs the "+originalStorageCost+" it originally took. That's "+(((float)finalStorageCost/originalStorageCost)*100)+"%!");
		System.out.println(NumberOfColors+" unique colors.");
		System.out.println(totalRecordedPositions+" recorded pixels, vs "+width*height+" originally.");
		//okay, bragging over
		//so: do we just output code that's copy-pasted, or do we just spit out this optimized array, and have the microcontroller handle processing it?
		//lets do number 2
		//have an array, 1st 3 are color, 4th is how many items it has, then that repeats for multiple colors. unfortunately this means one more number per color, on a 99% monochrome display setup
		name = name.replace(' ', '_');
		for(int i = 0; i<name.length(); i++){
			if(name.charAt(i) == '.'){
				name = name.substring(0, i);
				break;	
			}
		}//cut off the extension
		int codeNumbers[] = new int[finalStorageCost];
		int codeNumbersIndex = 0;
		
		code ="const uint8_t "+name+"["+finalStorageCost+"] = {";
		for(int c = 0; c < NumberOfColors; c++){//heh, c++... this holds what color we're working on currently
			code += OptimizedColors[c][0];
			codeNumbers[codeNumbersIndex++] = OptimizedColors[c][0];
			code += ",";
			code += OptimizedColors[c][1];
			codeNumbers[codeNumbersIndex++] = OptimizedColors[c][1];
			code += ",";
			code += OptimizedColors[c][2];
			codeNumbers[codeNumbersIndex++] = OptimizedColors[c][2];
			code += ",";
			code += NumberOfPositions[c];
			codeNumbers[codeNumbersIndex++] = NumberOfPositions[c];
			code += ",";
			//copies the color, and number of indexes to the code block
			
			for(int i = 0; i < NumberOfPositions[c]; i++){//specific index we're on inside that color
				code += OptimizedColorPositions[c][i];
				codeNumbers[codeNumbersIndex++] = OptimizedColorPositions[c][i];
				code += ",";
				//copies the indexes over
			}
		}
		//trim the extra comma off the end and add an ending brace and semicolon
		code = code.substring(0, code.length()-1);
		code += "};";
		
		
		System.out.println(code);
		//VerifyCode(codeNumbers);
		return code;
	}
	
	public static void VerifyCode(int[] codeNumbers){
		int color[] = {0,0,0};
		int black[] = {0,0,0};
		int IndexCount = 0;
		//these are ints now, because java doesn't allow unsigned bytes

		for(int i = 0; i<codeNumbers.length; i++){
			if(i>IndexCount){
				//color = black;
				System.arraycopy(black,0,color,0,black.length);
			}
			if(Arrays.equals(color, black)){
				//if the color is done / it's the first color
				color[0] = codeNumbers[i];
				color[1] = codeNumbers[++i];
				color[2] = codeNumbers[++i];//I increment i in place, because I would have to later, anyways
				IndexCount = (codeNumbers[++i] + i);
				i++;
			}
			System.out.println("Color: "+color[0]+","+color[1]+","+color[2]+" at index "+codeNumbers[i]);
		}
	}
	
	public static int ColorIndex(int[] Color, int[][] ColorTable){
		for(int i = 0; i<ColorTable.length; i++){
			if(Arrays.equals(ColorTable[i],Color))
				return i;
		}
		return -1;
	}

	public static int[] convertToNonAlpha(int[] RGBA){
		int[] RGB = new int[3];

		if(RGBA[3] == 255){
			//100% opaque
			RGB[0] = RGBA[0];
			RGB[1] = RGBA[1];
			RGB[2] = RGBA[2];
		}
		else if(RGBA[3] == 0){
			//transparent = black
			RGB[0] = 0;
			RGB[1] = 0;
			RGB[2] = 0;
		}
		else{
			//find a mash between fullbright and black
			float transMult = RGBA[3]/255f;
			RGB[0] = Math.round(RGBA[0]*transMult);
			RGB[1] = Math.round(RGBA[1]*transMult);
			RGB[2] = Math.round(RGBA[2]*transMult);
			//note: very very dirty, but I honestly cannot be assed to learn LAB space.
			//LAB makes blue turn purple as it goes to black. fuck it, RGB looks great like this.
		}

		return RGB;
	}

	public static void printConvertedImage(int[][][] image){
		System.out.println();
		int width = image.length;
		int height = image[0].length;
		for(int i = 0; i<height ; i++){
			for(int j = 0; j<width ; j++){
				System.out.print("[");
				for(int k = 0; k < 3 ; k++){
					System.out.print(image[j][i][k]);
					if(k<2) System.out.print(",");
				}
				System.out.print("]");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static String getNthFrame(File start, int frame){
		String toReturn = null;
		String fileName = start.getName();
		String fileNameNoExt = null;
		String fileExt = null;
		int numNumbers = 0;
		if(fileName.matches(".*\\d+.*")){
			//if it ends in a number
			for(int i = 0; i < fileName.length(); i++){
				if(Character.isDigit(fileName.charAt(i))){
					numNumbers++;
				}
				else if(fileName.charAt(i) == '.'){
					fileNameNoExt = fileName.substring(0, i);
					//record the first part as the name
					fileExt = fileName.substring(i+1);
					//record the rest as the file extension
					break;
				}
				else{
					numNumbers = 0;//reset it if it comes across a letter, so we only get the last few digits
				}
			}
			fileNameNoExt = fileNameNoExt.substring(0, fileNameNoExt.length()-numNumbers);
			if(numNumbers == 1){
				//then it likely didn't format the numbers (or they only go up to single digits), so just tack it on
				toReturn = start.getParent() + File.separatorChar + fileNameNoExt + frame + "." + fileExt;
			}
			else{
				toReturn = start.getParent() + File.separatorChar + fileNameNoExt + String.format("%0"+numNumbers+"d", frame) + "." + fileExt;
			}
		}
		//it'll return a null if I can't predict the frame, so there's no extra logic here.
		
		return toReturn;
	}
}

