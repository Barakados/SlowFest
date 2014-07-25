package objects;

import big.Level;

public class Box extends Entity {
	public double direction = 0;

	public Box(float x, float y, float width, float height, double direction) {
		draw("box");
		this.x = x;
		id = "BOX";
		this.y = y;
		this.width = width;
		this.height = height;
		this.direction = direction;
		this.vx = (float) Math.cos(direction);
		this.vy = (float) Math.sin(direction);
	}

	public void update() {
		if (active) {
			x += vx / Level.speed();
			y += vy / Level.speed();
		}
	}

}
