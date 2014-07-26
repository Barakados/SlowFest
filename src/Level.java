
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import org.lwjgl.BufferUtils;

public class Level {
	public IntBuffer buffer = BufferUtils.createIntBuffer(1);
	public IntBuffer where = BufferUtils.createIntBuffer(1);
	public FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3).put(
			new float[] { 0.0f, 0.0f, 0.0f });
	public FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3).put(
			new float[] { 0.0f, 0.0f, 0.0f });
	public FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(
			new float[] { 0.0f, 0.0f, 0.0f });
	public FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(
			new float[] { 0.0f, 0.0f, 0.0f });
	FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(
			new float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f });

	public void playSound(String file, Entity source) {
		sourcePos.put(new float[] { source.x, source.y, 0 });
		sourceVel.put(new float[] { source.vx, source.vy, 0 });
		listenerPos.put(new float[] { player.x, player.y, 0 });
		listenerVel.put(new float[] { player.vx, player.vy, 0 });
	}

	public static int state = 0;
	public Entity connect(String activate){
		Entity active = null;
		if (crates.containsKey(activate)){
			active=crates.get(activate);
		}
		if (walls.containsKey(activate)){
			active=walls.get(activate);
		}
		if (entities.containsKey(activate)){
			active=entities.get(activate);
		}
		if (background.containsKey(activate)){
			active=background.get(activate);
		}
		return active;
	}
	public static float speed() {
		if (Level.state == 0) {
			return 1;
		} else {
			return (float) (2 / Math.pow(2, Level.state));
		}
	}

	public Level(String string) {
		InputStream is = getClass().getResourceAsStream("level1.txt");
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
			walls.put(s,
					new Wall((tX + bX) / 2, (tY + bY) / 2, Math.abs(tX - bX),
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
			entities.put(s,
					new Box((tX + bX) / 2, (tY + bY) / 2, Math.abs(tX - bX),
							Math.abs(tY - bY), Math.PI * sa));
			i--;
		}
		i = sc.nextInt();
		while (i > 0) {
			float tX = sc.nextFloat();
			float tY = sc.nextFloat();
			float bX = sc.nextFloat();
			float bY = sc.nextFloat();
			String s = sc.next();
			crates.put(s,
					new Crate((tX + bX) / 2, (tY + bY) / 2, Math.abs(tX - bX),
							Math.abs(tY - bY)));
			i--;
		}
		i = sc.nextInt();
		while (i > 0) {
			System.out.println("NO");
			float tX = sc.nextFloat();
			float tY = sc.nextFloat();
			float bX = sc.nextFloat();
			float bY = sc.nextFloat();
			String connect = sc.next();
			String s = sc.next();
			buttons.put(s,
					new Button((tX + bX) / 2, (tY + bY) / 2, Math.abs(tX - bX),
							Math.abs(tY - bY), connect, this));
			i--;
		}
		i = sc.nextInt();
		while (i > 0) {
			String type = sc.next();
			float cx = sc.nextFloat();
			float cy = sc.nextFloat();
			String s = sc.next();
			if (type.equals("SIGN")) {
				background.put(s, new Sign(cx, cy));
			}
			i--;
		}
		i = sc.nextInt();
		sc.nextLine();
		while (i > 0) {
			String type = sc.nextLine();
			lines.add(new Line(type,this));
			i--;
		}
	}

	public ArrayList<Line> lines = new ArrayList<Line>();
	public Map<String, Entity> background = new HashMap<String, Entity>();
	public Map<String, Wall> walls = new HashMap<String, Wall>();
	public Map<String, Box> entities = new HashMap<String, Box>();
	public Map<String, Crate> crates = new HashMap<String, Crate>();
	public Map<String, Button> buttons = new HashMap<String, Button>();
	public Player player;

	public void update() {

		player.update();
		for (Entity e : background.values()) {
			e.draw();
		}
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
		for (Button b : buttons.values()) {
			b.draw();
			for (Crate c : crates.values()) {
				if (b.collide(c) || b.collide(player)) {
					b.active.active = true;
				} else {
					b.active.active = false;
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
		if (lines.size() > 0) {
			if (lines.get(0).isDone()) {
				lines.remove(0);
			}
		}
		player.draw();
	}
}
