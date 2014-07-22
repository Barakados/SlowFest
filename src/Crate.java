public class Crate extends Entity {
	public Crate(float x, float y, float width, float height) {
		this.red=.5f;
		this.green=.5f;
		this.blue=.0f;
		this.id="CRATE";
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void update() {
		vy+=Player.ACCELERATION/Level.speed();
		vx=(float) Math.max(Math.min(12, vx), -12);
		vy=Math.max(Math.min(7/Level.speed(), vy), -7/Level.speed());
		vx*=Math.pow(.9,1/Level.speed());
		x+=vx;
		y+=vy;
		
	}
}
