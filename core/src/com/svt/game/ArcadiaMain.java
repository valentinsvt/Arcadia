package com.svt.game;


import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

public class ArcadiaMain implements ApplicationListener, InputProcessor{
	SpriteBatch batch;
	public Array<Texture> textures = new Array<Texture>(); /*texturas para renderisar el mapa*/
	public Array<String> assetsNames;
	PerspectiveCamera cam;
	int size_x=50;
	int size_y=50;
	public NthTile[][] tiles = new NthTile[size_x][size_y];
	final Matrix4 matrix = new Matrix4();
	final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
	final Vector3 intersection = new Vector3();
	NthTile lastSelectedTile = null;
	final Vector3 curr = new Vector3();
	final Vector3 last = new Vector3(-1, -1, -1);
	final Vector3 delta = new Vector3();
	public ModelBatch modelBatch;
	public AssetManager assets;
	public Array<ModelInstance> instances = new Array<ModelInstance>();
	public Environment environment;
	public int zoomingQuantity;
	public int maxScroll;
	public int minScroll;
	/*Controles*/
	public OrthographicCamera overLayCam;
	public Rectangle buildButon;
	public Rectangle exitButon;
	public Rectangle menu;
	Texture barraMenu;
	private boolean build;
	SpriteBatch batchMenu;
	/*fin controles*/
	/*mapa*/
	MapLoader nivel;
	/*Animation*/
	AnimationController controller;
	boolean animationUpdate = false;
    Mob mob;

	@Override
	public void create () {
		batch = new SpriteBatch();
		batchMenu = new SpriteBatch();
		textures.add(new Texture(Gdx.files.internal("tent/patchGrass.jpg")));	
		textures.add(new Texture(Gdx.files.internal("textures/grass.jpg")));
		textures.add(new Texture(Gdx.files.internal("textures/water.png")));
		zoomingQuantity=20;
		cam = new PerspectiveCamera(20, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		minScroll=1;
		maxScroll=60;
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0,0,0);
		cam.near = 1f;
		cam.far = 300f;
		cam.fieldOfView=20;
		cam.update();
		matrix.setToRotation(new Vector3(1, 0, 0), 90);
		Gdx.input.setInputProcessor(this);
		/*for(int z = 0; z < size_y; z++) {
			for(int x = 0; x < size_x; x++) {				
				tiles[x][z]= new NthTile(1, x, z, 1, 1, NthTile.GRASS);

			}
		}*/


		/*3d*/
		modelBatch = new ModelBatch();
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
		assets = new AssetManager();
		assetsNames=new Array<String>();
		assetsNames.add("luz_tree_01/luz_tree_01.g3db");
		assetsNames.add("svtTree/tree.g3db");
		assetsNames.add("mountain/mountain.g3db");
		assetsNames.add("miniMan/mini_man_jump.g3db");
		assetsNames.add("caveman/caveman2.g3db");
		for(int i=0;i<assetsNames.size;i++){
			assets.load(assetsNames.get(i), Model.class);
		}

		/*controles*/
		menu = new Rectangle();
		menu.x = -410;
		menu.y = 250;
		menu.width = 827;
		menu.height = 20;

		buildButon = new Rectangle();
		buildButon.x=-410;
		buildButon.y=250;
		buildButon.width=20;
		buildButon.height=20;

		exitButon = new Rectangle();
		exitButon.x=387;
		exitButon.y=250;
		exitButon.width=30;
		exitButon.height=20;

		overLayCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		barraMenu = new Texture("textures/menuBar.png");
		boolean doneLoading=false;
		while(!doneLoading){
			doneLoading = assets.update();
		}
		/*mapas*/
		nivel=new MapLoader(size_x, size_y, "maps/nivel2.txt");
		nivel.setAsset(assets);
		nivel.setNames(assetsNames);
		tiles=nivel.getTiles(tiles);
		instances=nivel.getInstances();

		
		/*models*/
		Model model = assets.get("caveman/caveman2.g3db", Model.class);
		ModelInstance mi =   new ModelInstance(model);	
		
		Vector3 touchPos = new Vector3();
		touchPos.set(0, 0, 0);
		cam.unproject(touchPos);
		System.out.println("!!! x "+touchPos.x+" "+touchPos.y+"  "+touchPos.z);
		mi.transform.setToTranslation(0,0,0);
		//mi.transform.rotate(Vector3.Y, 90);
		mi.transform.scale(0.2f, 0.2f, 0.2f);
		controller = new AnimationController(mi);		
		mob=new Mob(touchPos.x,0,touchPos.y,mi,controller);
		mob.setSpeed(1);
		mob.setAnimation("caveman_rigging_OK_hair_walk2_OK_no_bgs",-1);
		mob.setTarget(31, 0, 31);
	
        
       
		//instances.add(mi);
		
		
		
		
	}

	@Override
	public void render () {

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);		
		cam.update();
		overLayCam.update();

		batch.setProjectionMatrix(cam.combined);
		batch.setTransformMatrix(matrix);
		batch.begin();
		for(int z = 0; z < size_y; z++) {
			for(int x = 0; x < size_x; x++) {
				NthTile actual = tiles[x][z];
				//System.out.println("type actual "+actual.getType());
				//batch.setColor(actual.getR(),actual.getG(),actual.getB(),actual.getA());
				batch.draw(textures.get(actual.getType()),actual.getX(),actual.getY(),actual.getWidth(),actual.getHeight());
			}
		}
		batch.end();

		checkTileTouched();

		/*3d*/
	
		//System.out.println("fr"+Gdx.graphics.getFramesPerSecond ());
		if(instances.size > 0){

			modelBatch.begin(cam);
			modelBatch.render(instances, environment);
			modelBatch.render(mob.model,environment);
			modelBatch.end();
		}
		mob.update(Gdx.graphics.getFramesPerSecond());

		/*controles*/

		batchMenu.setProjectionMatrix(overLayCam.combined);
		batchMenu.begin();
		//100mx100m texture in world coordinates
		batchMenu.draw(barraMenu, menu.x,menu.y);
		batchMenu.end();

	}


	private void insertModel(NthTile lastSelectedTile) {

		//ModelLoader loader = new G3dModelLoader();
		//Model model = loader.loadModel(Gdx.files.internal("svtTree/tree.obj"));	

		Model model = assets.get("miniMan/mini_man_jump.g3db", Model.class);
		ModelInstance mi =   new ModelInstance(model);	
		//System.out.println("insert "+mi.animations.get(0).id);


		for(int i=0;i<mi.materials.size;i++){
			//System.out.println("material *"+mi.materials.get(i).id+"*");
			if(mi.materials.get(i).id.equalsIgnoreCase("lambert3SG")){
				//System.out.println("SI!! "+mi.materials.get(i).id);
				//mi.materials.get(i).set(new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA));
			}
		}	
		Vector3 touchPos = new Vector3();
		touchPos.set(lastSelectedTile.getX(), lastSelectedTile.getY(), 0);
		mi.transform.setToTranslation(touchPos.x,-0.3f,touchPos.y);
		//mi.transform.rotate(Vector3.Y, 135);
		//mi.transform.scale(0.05f, 0.05f, 0.05f);
		mi.transform.scale(0.2f, 0.2f, 0.2f);
		controller = new AnimationController(mi);
		controller.setAnimation("mini_man_jump",-1);
        animationUpdate=true;
		instances.add(mi);
	}

	private void checkTileTouched() {
		if(Gdx.input.justTouched()) {
			Ray pickRay = cam.getPickRay(Gdx.input.getX(), Gdx.input.getY());
			Intersector.intersectRayPlane(pickRay, xzPlane, intersection);
			int x = (int)intersection.x;
			int z = (int)intersection.z;

			if(x >= 0 && x < size_x && z >= 0 && z < size_y) {				
				NthTile tile = tiles[x][z];
				tile.setColor(1, 0, 0, 1);
				//insertModel(tile);
				//tile.setTextureIndex(0);
			}

		}
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		// TODO Auto-generated method stub

		Vector3 touchPos = new Vector3();
		touchPos.set(x, y, 0);
		System.out.println("x "+x+" y "+y+" z ");
		cam.unproject(touchPos);
		System.out.println("x2 "+touchPos.x+" y2 "+touchPos.y+ " z2 "+touchPos.z);
		mob.toogle();
		overLayCam.unproject(touchPos);
		System.out.println("x3 "+touchPos.x+" y3 "+touchPos.y+ " z3 "+touchPos.z);
		Rectangle click = new Rectangle(touchPos.x,touchPos.y,1,1);
		if(click.overlaps(buildButon)){
			build=true;
		}
		if(click.overlaps(exitButon)){
			System.exit(0);
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		last.set(-1, -1, -1);
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		Ray pickRay = cam.getPickRay(x, y);
		Intersector.intersectRayPlane(pickRay, xzPlane, curr);

		if(!(last.x == -1 && last.y == -1 && last.z == -1)) {
			pickRay = cam.getPickRay(last.x, last.y);
			Intersector.intersectRayPlane(pickRay, xzPlane, delta);			
			delta.sub(curr);
			cam.position.add(delta.x, delta.y, delta.z);
		}
		last.set(x, y, 0);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {

		zoomingQuantity+=amount;
		if(zoomingQuantity>maxScroll)
			zoomingQuantity=maxScroll;
		if(zoomingQuantity<minScroll)
			zoomingQuantity=minScroll;
		//System.out.println("scroll "+zoomingQuantity);
		//rotate
		//cam.rotate(zoomingQuantity, 0, 1, 0);

		//cam.translate(cam.position.x, cam.position.y+zoomingQuantity, cam.position.z);
		cam.fieldOfView=zoomingQuantity;
		return false;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		batch.dispose();
		assets.dispose();
		// TODO Auto-generated method stub

	}
}
