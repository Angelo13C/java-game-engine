package sandbox;

import core.renderer.texture.Sprite;
import core.time.Time;
import core.util.Random;
import core2D.renderer2D.sprite.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector2i;

import core.Window;
import core.ecs.GameObject;
import core.ecs.Scene;
import core.ecs.SceneManager;
import core.renderer.Color;
import core.renderer.RendererAPI;
import core.renderer.RendererCommand;
import core.renderer.texture.SpriteAtlas;
import core.renderer.texture.Texture2D;
import core2D.Camera2D;
import core2D.CanvasLayer;
import core2D.Transform2D;
import core2D.grid2D.Tilemap;
import core2D.grid2D.Tileset;
import core2D.renderer2D.Renderer2D;
import core2D.renderer2D.sprite.SpriteSheetAnimation;
import glfw.GLFWApplication;
import glfw.GLFWWindow;
import openGL.OpenGLRendererAPI;

import java.text.DecimalFormat;

public class Sandbox extends GLFWApplication
{	
	private Scene scene;
	private GameObject player;
	private GameObject camera;
	private GameObject tilemap;

	private GameObject[] marioGameObjects;
	
	public Sandbox(final Window window) 
	{
		super(window);
		
		RendererAPI.selectAPI(new OpenGLRendererAPI());
		Renderer2D.initialize();
		
		RendererCommand.setClearColor(new Color(0f, 0.1f, 0.2f));

		this.scene = new Scene("Test");
		Texture2D playerTexture = Texture2D.create("assets/textures/grotto_escape_pack/graphics/player.png");
		SpriteAtlas playerSpriteAtlas = SpriteAtlas.createFromGridCount(playerTexture, new Vector2i(4, 2));
		
		this.player = new GameObject(this.scene);
		this.player.addBehaviour(Transform2D.class);
		InputMover playerMover = this.player.addBehaviour(InputMover.class);
		playerMover.setSpeed(5);
		SpriteSheetAnimation playerAnimation = this.player.addBehaviour(SpriteSheetAnimation.class);
		SpriteSheetAnimation.Animation idleAnimation = playerAnimation.new Animation();
		SpriteSheetAnimation.Animation runAnimation = playerAnimation.new Animation();
		playerAnimation.addAnimation(idleAnimation);
		playerAnimation.addAnimation(runAnimation);
		runAnimation.setLoop(true);
		runAnimation.addFramesFromSpriteAtlas(playerSpriteAtlas, 0.1f);
		playerAnimation.selectAnimation(runAnimation);	
		
		this.camera = new GameObject(scene);
		this.camera.addBehaviour(Transform2D.class);
		Camera2D camera2D = this.camera.addBehaviour(Camera2D.class);
		camera2D.setZoomLevel(6);
		InputMover inputMover = this.camera.addBehaviour(InputMover.class);
		inputMover.setSpeed(5);
		
		this.tilemap = new GameObject(scene);
		this.tilemap.addBehaviour(Transform2D.class).setLocalPosition(new Vector2f(0f, -1f));
		Texture2D tilemapTexture = Texture2D.create("assets/textures/grotto_escape_pack/graphics/tiles.png");
		SpriteAtlas tilemapSpriteAtlas = SpriteAtlas.createFromGrid(tilemapTexture, new Vector2i(16, 16));
		Tilemap tilemapBehaviour = this.tilemap.addBehaviour(Tilemap.class);
		Tileset tileset = Tileset.createFromSpriteAtlas(tilemapSpriteAtlas);
		tilemapBehaviour.setTileset(tileset);

		tilemapBehaviour.setTile(new Vector2i(0, 0), 30);
		tilemapBehaviour.setTile(new Vector2i(1, 0), 31);
		tilemapBehaviour.setTile(new Vector2i(2, 1), 32);
		tilemapBehaviour.setTile(new Vector2i(3, 1), 33);
		tilemapBehaviour.setTile(new Vector2i(4, 1), 34);
		tilemapBehaviour.setTile(new Vector2i(5, 1), 35);
		tilemapBehaviour.setTile(new Vector2i(6, 1), 36);
		tilemapBehaviour.setTile(new Vector2i(7, 1), 37);
		
		CanvasLayer background = new CanvasLayer();
		background.addObject(tilemapBehaviour);
		CanvasLayer middleground = new CanvasLayer();
		middleground.addObject(playerAnimation);
		CanvasLayer foreground = new CanvasLayer();
		CanvasLayer GUI = new CanvasLayer();

		var marioSpritesCount = 100;
		marioGameObjects = new GameObject[marioSpritesCount];
		var marioSprite = new Sprite(Texture2D.create("assets/textures/mario.png"));
		for(int i = 0; i < marioSpritesCount; i++)
		{
			marioGameObjects[i] = new GameObject(this.scene);
			var marioTransform = marioGameObjects[i].addBehaviour(Transform2D.class);
			marioTransform.setLocalPosition(new Vector2f(Random.getInRange(-5, 5), Random.getInRange(5, 15)));
			var marioSpriteRenderer = marioGameObjects[i].addBehaviour(SpriteRenderer.class);
			marioSpriteRenderer.setSprite(marioSprite);
			marioSpriteRenderer.setColor(Color.WHITE);
			middleground.addObject(marioSpriteRenderer);
		}
		
		this.camera.getBehaviour(Camera2D.class).addCanvasLayer(background);
		this.camera.getBehaviour(Camera2D.class).addCanvasLayer(middleground);
		this.camera.getBehaviour(Camera2D.class).addCanvasLayer(foreground);
		this.camera.getBehaviour(Camera2D.class).addCanvasLayer(GUI);
	}
	
	@Override
	public void onUpdate()
	{
		var startTime = Time.getTime();
		
		this.window.startFrame();
		
		DecimalFormat formatter = new DecimalFormat("0.0000");	
		var timeToDebug = Time.getTime();	
		RendererCommand.clear();
		System.out.println("Clear DT: " + formatter.format(1000 * (Time.getTime() - timeToDebug)) + "ms");
		
		timeToDebug = Time.getTime();
		Renderer2D.startScene(camera.getBehaviour(Camera2D.class));
		System.out.println("Start Scene DT: " + formatter.format(1000 * (Time.getTime() - timeToDebug)) + "ms");

		timeToDebug = Time.getTime();
		SceneManager.update();
		System.out.println("Scene Manager DT: " + formatter.format(1000 * (Time.getTime() - timeToDebug)) + "ms");
		
		timeToDebug = Time.getTime();
		Renderer2D.endScene();
		System.out.println("End Scene DT: " + formatter.format(1000 * (Time.getTime() - timeToDebug)) + "ms");

		timeToDebug = Time.getTime();
		RendererCommand.update();
		System.out.println("Renderer update DT: " + formatter.format(1000 * (Time.getTime() - timeToDebug)) + "ms");

		timeToDebug = Time.getTime();
		this.window.endFrame();
		System.out.println("End Frame DT: " + formatter.format(1000 * (Time.getTime() - timeToDebug)) + "ms");
		
		System.out.println("FRAME DT: " + formatter.format(1000 * (Time.getTime() - startTime)) + "ms");
		System.out.println("--------------------------------------");
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	public static void main(String[] args)
	{
		Sandbox app = new Sandbox(new GLFWWindow(new Vector2i(1000, 600), "Gear Engine"));
		app.start();
	}
}
