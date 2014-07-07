package com.svt.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;

public class Mob {

	float x;
	float y;
	float z;
	float speed=0;
	ModelInstance model;
	AnimationController controller;
	float stateTime =0;
	float frame=0;

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

	public void update(float delta){
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
		if(delta>50){
			this.x=(float) this.x+(speed/delta);
			this.z=(float) this.z+(speed/delta);
			this.model.transform.translate(this.x,this.y,this.z);
		}
	}


}
