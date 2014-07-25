public class Player extends Entity {
	public static double ACCELERATION = Math.PI / 65 + .4;

	public void update() {
		vy += ACCELERATION;
		vx = (float) Math.max(Math.min(3, vx), -3);
		vy = (float) Math.max(Math.min(7, vy), -7);
		vx *= .9;
		x += vx;
		y += vy;
	}

	public Player(float x, float y) {
		super("screen");
		id = "PLAYER";
		this.x = x;
		this.y = y;
		this.width = 20;
		this.height = 20;
	}
}