package big;
import objects.Player;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Main {
	public Level selected;
	public boolean ready;
	public float yeah = 0f;
	public static void main(String[] args) {
		Main main = new Main();
		main.start();
	}

	public void input() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)
				|| Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (selected.player.grounded) {
				selected.player.vy -= 10;
				selected.player.grounded = false;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)
				|| Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			selected.player.vx -= Player.ACCELERATION*2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)
				|| Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			selected.player.vx += Player.ACCELERATION*2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)
				|| Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
			ready = true;
		}else{
			
			if (Math.abs(Level.state)==1&&ready==true){
				Level.state=-Level.state;
				ready=false;
			}
		}
	}

	public void update() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		selected.update();
	}

	public void setupOpenGL() {
		try {
			Display.setDisplayMode(new DisplayMode(600, 600));
			Display.setVSyncEnabled(true);
			Display.setTitle("Reverberations");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void start() {
		setupOpenGL();
		setupQuad();
		selected = new Level("level1.txt");
		Level.state=1;
		while (!Display.isCloseRequested()) {
			input();
			update();
			Display.sync(60);
			Display.update();
		}
		destroy();
	}

	public void destroy() {
		Display.destroy();
	}

	private void setupQuad() {
		GL11.glViewport(0,0, 600, 600);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 600, 600, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glShadeModel(GL11.GL_SMOOTH);
		GL11.glClearColor(0.3f, 0f, 0.5f, 0f);
		GL11.glClearDepth(1.0f);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
        
	}
}
