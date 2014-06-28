package com.svt.game;

import com.badlogic.gdx.math.Rectangle;

/**
 * @author svt
 *
 */
public class NthTile {
	
	int textureIndex;  /*Numero para determinar que textura pintar en el render. En la clase principal habrá un arreglo con todas las texturas para mejorar performance*/
	float x; /*posicion en x en el sistema de coordenadas*/
	float y; /*posicion en y en el sistema de coordenadas*/
	float width; 
	float height;
	Rectangle bounds; /*Rectangulo con la forma del tile*/
	int type=0; /*Tipo del tile*/
	/*Diccionario de tipos*/
	public final int ENABLED = 1;
	public final int DISABLED = 3;
	public final int WATER = 3;
	public final int OCCUPIED = 3;
	public final int TREE = 4;
	public final int MOUNTAIN = 4;
	/*--------------------*/
	
	public NthTile(int index,int x, int y, float width,float height,int type){
		this.textureIndex = index;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.type=type;
		this.setBounds();
		System.out.println("text");
	}
	
	
	
	public void setBounds(){
		this.bounds = new Rectangle(this.x, this.y, this.width, this.height);
	}
		
	
	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	

}
