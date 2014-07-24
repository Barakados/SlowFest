import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;

public class Level {
	public static AngelCodeFont fnt;
	public IntBuffer buffer = BufferUtils.createIntBuffer(1);
	public IntBuffer where = BufferUtils.createIntBuffer(1);
	public FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
	public FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
	public FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
	public FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f,0.0f,0.0f});
	FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[] { 0.0f, 0.0f, 0.0f,  0.0f, 0.0f, 0.0f });
	public void playSound(String file, Entity source){
		sourcePos.put(new float[]{source.x,source.y,0});
		sourceVel.put(new float[]{source.vx,source.vy,0});
		listenerPos.put(new float[]{player.x,player.y,0});
		listenerVel.put(new float[]{player.vx,player.vy,0});
	}
	public static int state = 0;
	public static float speed(){
		if (Level.state==0){
			return 1;
		}else{
		return (float) (2/Math.pow(2,Level.state));
		}
	}
	public Level(String string) {
		try {
			TextureImpl.unbind();
			Image img = new Image("Arial.png");
			img.bind();
			fnt = new AngelCodeFont("Arial.fnt",img);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is =getClass().getResourceAsStream("level1.txt");
		Scanner sc = null;
		sc = new Scanner(is);
		player = new Player(sc.nextFloat(), sc.nextFloat());
		int i = sc.nextInt();
		while (i > 0) {
			float tX = sc.nextFloat();
			float tY = sc.nextFloat();
			float bX = sc.nextFloat();
			float bY = sc.nextFloat();
			String s = sc.next();
			walls.put(s,new Wall((tX + bX) / 2, (tY + bY) / 2, Math.abs(tX - bX),
					Math.abs(tY - bY)));
			i--;
		}
		i = sc.nextInt();
		while (i > 0) {
			float tX = sc.nextFloat();
			float tY = sc.nextFloat();
			float bX = sc.nextFloat();
			float bY = sc.nextFloat();
			double sa = sc.nextDouble();
			String s = sc.next();
			entities.put(s,new Box((tX + bX) / 2, (tY + bY) / 2, Math
					.abs(tX - bX), Math.abs(tY - bY), Math.PI * sa));
			i--;
		}
		i = sc.nextInt();
		while (i > 0) {
			float tX = sc.nextFloat();
			float tY = sc.nextFloat();
			float bX = sc.nextFloat();
			float bY = sc.nextFloat();
			String s = sc.next();
			crates.put(s,new Crate((tX + bX) / 2, (tY + bY) / 2, Math
					.abs(tX - bX), Math.abs(tY - bY)));
			i--;
		}
		i = sc.nextInt();
		while (i > 0) {
			float tX = sc.nextFloat();
			float tY = sc.nextFloat();
			float bX = sc.nextFloat();
			float bY = sc.nextFloat();
			String connect = sc.next();
			String s = sc.next();
			buttons.put(s,new Button((tX + bX) / 2, (tY + bY) / 2, Math
					.abs(tX - bX), Math.abs(tY - bY),connect,this));
			i--;
		}
	}

	public Map<String,Wall> walls = new HashMap<String,Wall>();
	public Map<String,Box> entities = new HashMap<String,Box>();
	public Map<String,Crate> crates = new HashMap<String,Crate>();
	public Map<String,Button> buttons = new HashMap<String,Button>();
	public Player player;

	public void update() {
		player.update();
		for (Wall w : walls.values()) {
			w.collide(player);
			w.draw();
		}
		for (Box b : entities.values()) {
			b.update();
			for (Wall w : walls.values()) {
				w.collide(b);
			}
			for (Box bo : entities.values()) {
				if (!bo.equals(b)) {
					bo.collide(b);
				}
			}
			b.collide(player);
			for (Crate c : crates.values()) {
				b.collide(c);
			}
			b.draw();
		}
		for (Button b:buttons.values()){
			b.draw();
			for (Crate c: crates.values()){
				if (b.collide(c)||b.collide(player)){
					b.active.active=true;
				}else{
					b.active.active=false;
				}
			}
		}
		for (Crate c : crates.values()) {
			c.update();
			for (Wall w : walls.values()) {
				w.collide(c);
				if (player.collide(c)) {
					if (w.collide(c)) {
						c.collide(player);
					}
				}
			}
			c.draw();
		}
		player.draw();
		fnt.drawString(400, 400, "SEXY");
	}
}
