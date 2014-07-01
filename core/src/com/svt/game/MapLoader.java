package com.svt.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MapLoader {
	public int width;
	public int heigth;
	public String file;
	public FileHandle handle;
	public String text;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public AssetManager assets;
	public Array<String> assetsNames;
	public MapLoader(int w , int y, String file){
		this.width=w;
		this.heigth=y;
		this.file=file;
		handle = Gdx.files.internal(file);
		text = handle.readString();
	}

	public NthTile[][]  getTiles(NthTile[][]  tiles){

		String[] datos = text.split(",");
		for(int y=0;y<heigth;y++){
			for(int x=0;x<width;x++){
				
				int type;
				int asset=-1;
				boolean active = true;
				switch(datos[(y)*width+x]){
				case "6":
					type=NthTile.GRASS;
					break;
				case "1107":
					type=NthTile.GRASS;
					active=false;
					break;
				case "84":
					type=NthTile.GRASS2;
					break;
				case "5":
					type=NthTile.GRASS;
					asset=NthTile.MOUNTAIN;
					break;
				case "3":
					type=NthTile.WATER;
					break;
				case "1":
					type=NthTile.GRASS;
					asset=NthTile.TREE;
					break;
				default:
					type=NthTile.GRASS;
					break;
				}
				tiles[x][y]=new NthTile(1, x, y, 1, 1,type);
				if(asset>-1){
					tiles[x][y].setAssetIndex(asset);
					Model model = assets.get(assetsNames.get(asset), Model.class);
					ModelInstance mi =   new ModelInstance(model);						
					for(int i=0;i<mi.materials.size;i++){
						if(mi.materials.get(i).id.equalsIgnoreCase("lambert3SG")){
							//System.out.println("SI!! "+mi.materials.get(i).id);
							mi.materials.get(i).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
						}
					}	
					Vector3 touchPos = new Vector3();
					touchPos.set(tiles[x][y].getX(), tiles[x][y].getY(), 0);
					mi.transform.setToTranslation(touchPos.x,-0.3f,touchPos.y);
					
					if(asset==1){
						mi.transform.scale(0.05f, 0.05f, 0.05f);
					}else{
						//mi.transform.scale(0.2f, 0.2f, 0.2f);
					}
					instances.add(mi);
				}
				tiles[x][y].setActive(active);
				//System.out.println("next tile pos (y)*width+x ---> "+((y)*width+x)+" Y -> "+y+" X -> "+x+" dato "+datos[(y)*width+x]+"  type!! "+tiles[x][y].getType());
			}
		}
		return tiles;
	}

	public void setAsset(AssetManager assets){
		this.assets=assets;
	}

	public void setNames(Array<String> names){
		this.assetsNames=names;
	}
	
	public Array<ModelInstance> getInstances(){
		return this.instances;
	}

}
