package com.moss.texturedquad;

import org.newdawn.slick.opengl.Texture;

public class TexturedQuad {
	
	protected float x, y, width, height;
	
	protected Texture tex;
	
	TexturedQuad(float x, float y, float width, float height, Texture tex) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.tex = tex;
	}
	
	public void moveUp(float increment) { 
		y += increment;
	}
	
	public void moveDown(float increment) {
		y -= increment;
	}
	
	public void moveLeft(float increment) {
		x -= increment;
	}
	
	public void moveRight(float increment) {
		x += increment;
	}
	
	public void render() {
		RenderGL.renderTexQuad(this);
	}

}
