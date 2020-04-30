package com.github.elegantwhelp.gui;

import com.github.elegantwhelp.assets.Assets;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import com.github.elegantwhelp.io.Input;
import com.github.elegantwhelp.io.Window;
import com.github.elegantwhelp.render.*;

public class Gui {
	private Shader shader;
	private Camera camera;
	private TileSheet sheet;

	
	public Gui(Window window) {
		shader = new Shader("gui");
		camera = new Camera(window.getWidth(), window.getHeight());
		sheet = new TileSheet("test.png", 3);
	}
	
	public void resizeCamera(Window window) {
		camera.setProjection(window.getWidth(), window.getHeight());
	}
	
	public void update(Input input) {

	}
	
	public void render() {
		Matrix4f mat = new Matrix4f();
		camera.getProjection().scale(64,mat);
		mat.translate(-2,-2,0);
		shader.bind();
		shader.setUniform("projection",mat);
		sheet.bindTile(shader,3);
		Assets.getModel().render();
	}
}
