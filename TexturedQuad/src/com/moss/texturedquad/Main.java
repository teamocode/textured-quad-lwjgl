package com.moss.texturedquad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Main {
	
	private static Texture texture;
	
	private static TexturedQuad quad;
	
	private static enum GameState {
		DEMO, GAME
	}
	
	private static GameState gameState;
	
	private static enum QuadState {
		STATIC, MOVEUP, MOVEDOWN, MOVELEFT, MOVERIGHT
	}
	
	private static QuadState quadState;
	
	private static float quadSpeed;

	public static void main(String[] args) {
		
		// Main Thread
		
		createDisplay();
		
		loadTexture();
		
		setupQuad();
		
		RenderGL.setupGL();	
		
		loop();
		
	}
	
	private static void createDisplay() {
		
		// Create LWJGL Display
		
		try {
			Display.setTitle("Textured Quad");
			Display.setDisplayMode(new DisplayMode(700, 700));
			Display.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void loadTexture() {
		
		// Load texture from file
		
		texture = null;
		
		try {
			texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/test.png")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private static void setupQuad() {	
		
		// Set the quad's x, y, w, h; set quad's unit/frame speed; Set Game and Quad states
		
		quad = new TexturedQuad(0f, 0f, 0.5f, 0.5f, texture);
		gameState = GameState.DEMO;
		quadState = QuadState.STATIC;
		quadSpeed = 0.025f;
	}
	
	private static void loop() {
		
		// Game Loop method
		
		while(!Display.isCloseRequested()) {
			
			// Loop
			
			handleInput();
			
			handleGameState();
			
			renderScene();
			
			handleDisplay();
		}
		
		// Clean-up
		
		Display.destroy();
		System.exit(0);
	}
	
	private static void handleInput() {
		
		// Handle key presses
		
		switch(gameState) {
		case DEMO:
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) gameState = GameState.GAME;
			break;
		case GAME:
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) gameState = GameState.DEMO;
			
			quadState = QuadState.STATIC;
			if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
				quadState = QuadState.MOVEUP;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
				quadState = QuadState.MOVEDOWN;		
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
				quadState = QuadState.MOVELEFT;
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
				quadState = QuadState.MOVERIGHT;
			}
			break;
		}
	}
	
	private static void handleGameState() {
		
		// Handle the game's state
		
		switch(gameState) {
		case DEMO:
			demo();
			break;
		case GAME:
			game();
			break;
		}
	}
	
	private static void demo() {
		
		// Demo logic
		
		if(quadState == QuadState.STATIC) {
			quad.x = -0.25f;
			quadState = QuadState.MOVEUP;
		}
		
		switch(quadState) {
		case MOVEUP:
			if(quad.y <= 0.5f) {
				quad.y += quadSpeed;
			} else {
				quadState = QuadState.MOVERIGHT;
			}
			break;
		case MOVEDOWN:
			if(quad.y >= -0.5f) {
				quad.y -= quadSpeed;
			} else {
				quadState = QuadState.MOVELEFT;
			}
			break;
		case MOVELEFT:
			if(quad.x >= -0.5f) {
				quad.x -= quadSpeed;
			} else {
				quadState = QuadState.MOVEUP;
			}
			break;
		case MOVERIGHT:
			if(quad.x <= 0.5f) {
				quad.x += quadSpeed;
			} else {
				quadState = QuadState.MOVEDOWN;
			}
			break;
		}
		
	}
	
	private static void game() {
		
		// Game logic
		
		switch(quadState) {
		case MOVEUP:
			if(quad.y + quad.height / 2f < 0.95f) {
				quad.moveUp(quadSpeed);
			}
			break;
		case MOVEDOWN:
			if(quad.y - quad.height / 2f > -0.95f) {
				quad.moveDown(quadSpeed);
			}
			break;
		case MOVELEFT:
			if(quad.x - quad.width / 2f > -0.95f) {
				quad.moveLeft(quadSpeed);
			}
			break;
		case MOVERIGHT:
			if(quad.x + quad.width / 2f < 0.95f) {
				quad.moveRight(quadSpeed);
			}
			break;
		}
	}
	
	private static void renderScene() {
		
		// Rendering the scene
		
		RenderGL.clear();
		renderBorder();
		renderQuad();
	}
	
	private static void renderBorder() {
		
		// Render the border; params x1, y1, x2, y2, r, g, b, a
		
		RenderGL.renderColorLine(-0.96f, 0.96f, 0.96f, 0.96f, 1f, 0f, 0f, 1f);
		RenderGL.renderColorLine(0.96f, 0.96f, 0.96f, -0.96f, 1f, 0f, 0f, 1f);
		RenderGL.renderColorLine(0.96f, -0.96f, -0.96f, -0.96f, 1f, 0f, 0f, 1f);
		RenderGL.renderColorLine(-0.96f, -0.96f, -0.96f, 0.96f, 1f, 0f, 0f, 1f);
	}
	
	private static void renderQuad() {
		
		// Quad object renders itself
		
		quad.render();
	}
	
	private static void handleDisplay() {
		
		// Handle LWJGL Display
		
		Display.sync(60);
		Display.update();
	}

}

