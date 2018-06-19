package com.moss.texturedquad;

import static org.lwjgl.opengl.GL11.*;

public abstract class RenderGL {
	
	public static void setupGL() {
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static void renderColorLine(float x1, float y1, float x2, float y2, float r, float g, float b, float a) {	
		glColor4f(r, g, b, a);
		
		glBegin(GL_LINES);
		
		glVertex2f(x1, y1);
		glVertex2f(x2, y2);
		
		glEnd();
		
		glColor4f(1f, 1f, 1f, 1f);
	}
	
	public static void renderTexQuad(TexturedQuad q) {
		float minX = q.x - q.width / 2f;
		float maxY = q.y + q.height / 2f;
		float maxX = q.x + q.width / 2f;
		float minY = q.y - q.height / 2f;
		
		glBindTexture(GL_TEXTURE_2D, q.tex.getTextureID());
		
		glBegin(GL_QUADS);
		
		glTexCoord2f(0f, 0f);
		glVertex2f(minX, maxY);
		
		glTexCoord2f(1f, 0f);
		glVertex2f(maxX, maxY);
		
		glTexCoord2f(1f, 1f);
		glVertex2f(maxX, minY);
		
		glTexCoord2f(0f, 1f);
		glVertex2f(minX, minY);
		
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
}
