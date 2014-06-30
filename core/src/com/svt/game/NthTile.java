package com.svt.game;

import com.badlogic.gdx.math.Rectangle;

/**
 * @author svt
 *
 */
public class NthTile {

	int textureIndex;  /*Numero para determinar que textura pintar en el render. En la clase principal habrá un arreglo con todas las texturas para mejorar performance*/
	int assetNameIndex;  /**/
	boolean active = true;
	float x; /*posicion en x en el sistema de coordenadas*/
	float y; /*posicion en y en el sistema de coordenadas*/
	float width; 
	float height;
	Rectangle bounds; /*Rectangulo con la forma del tile*/
	int type=0; /*Tipo del tile*/
	float[] color = new float[4];
	/*Diccionario de tipos*/
	public final static int GRASS = 1;
	public final static int GRASS2 = 0;
	public final static int DISABLED = 3;
	public final static int WATER = 2;
	public final static int OCCUPIED = 10;
	public final static int TREE = 1;
	public final static int MOUNTAIN = 2;
	/*--------------------*/

	public NthTile(int index,float x, float y, float width,float height,int type){
		this.textureIndex = index;
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.type=type;
		this.setBounds();
		this.setColor(0.5f, 0.5f, 1, 1);
	}

	/*El color en formato r - g - b - a... por defecto negro*/
	public void setColor(){
		color[0]=1f;color[1]=1f;color[1]=1f;color[1]=1f;
	}
	public void setColor(float r, float g, float b, float alpha){
		color[0]=r;color[1]=g;color[1]=b;color[1]=alpha;
	}

	public float[] getColor(){
		return this.color;
	}

	public float getR(){
		return this.color[0];

	}
	public float getG(){
		return this.color[1];
	}
	public float getB(){
		return this.color[2];
	}
	public float getA(){
		return this.color[3];
	}

	public void setAssetIndex(int index){
		this.assetNameIndex=index;
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

	public void setActive(boolean a){
		this.active=a;
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



	public String toString(){
		return "X-> "+this.x+"  Y-> "+this.y+"  Type-> "+this.type+" W-> "+this.width+" H-> "+this.height+"  TextureIndex "+textureIndex;
	}


}
