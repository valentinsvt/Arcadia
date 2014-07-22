package com.svt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;

public class Mob {

	float x;
	float y;
	float z;
	float speed=0;
	ModelInstance model;
	AnimationController controller;
	float stateTime =0;
	float frame=0;
	boolean animation=false;
	boolean move=false;
	Vector3 target;
	String animationId;
	PerspectiveCamera cam;

	public Mob(float x, float y,float z, ModelInstance model,AnimationController controller){
		this.x=x;
		this.y=y;
		this.z=z;
		this.model=model;
		this.controller=controller;
	}

	public void setSpeed(float speed){
		this.speed=speed;
	}

	public void setCam(PerspectiveCamera cam){
		this.cam=cam;
	}
	
	public void setAnimation(String id,int loop){
		this.animationId=id;
		controller.setAnimation(id,loop);
	}

	public void play(){
		controller.setAnimation(animationId,-1);		
		this.animation=true;
		this.move=true;
	}
	public void stop(){
		this.controller.setAnimation(animationId,1);
		this.move=false;
		this.animation=false;
	}
	public void toogle(){
		if(this.animation)
			stop();
		else
			play();
	}
	public void toogleMove(){
		if(this.move)
			stop();
		else{
			play();
		}
	}
	public void Move(){
		this.move=true;
	}
	public void setTarget(float x,float y, float z){
		target=new Vector3();
		this.target.x=x;this.target.y=y;this.target.z=z;
	}

	public void update(float delta){
		if(animation){
			controller.update(Gdx.graphics.getDeltaTime());
		}
		/*stateTime++;
		if(stateTime>10){
			this.x=(float) this.x+(speed/3);
			this.z=(float)  this.z+(speed/3);
			//System.out.println("velocidad "+this.x+"  "+this.z);
			//this.y=(float) (this.y+(delta*0.2));

			this.model.transform.translate(this.x,this.y,this.z);
			this.stateTime=0;
		}*/
		//System.out.println("delta "+delta);
		if(move){
			if(delta>50){
				
				//System.out.println("target pos "+target.x+"  "+target.z);
				if(target.x>x)
					this.x=(float) this.x+(speed/delta);
				else
					this.x=(float) this.x-(speed/delta);
				if(target.z>z)
					this.z=(float) this.z+(speed/delta);
				else
					this.z=(float) this.z+(speed/delta);
				System.out.println("current pos "+this.x+"  "+this.z);
				this.model.transform.translate(this.x,this.y,this.z);
			}
		}
	}


}
