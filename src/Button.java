public class Button extends Entity {
	public Level level;
	public String activate;
	public Entity active=null;
	public Button(float x, float y, float width, float height, String activate, Level level) {
		super("button");
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.activate=activate;
		this.level=level;
		if (level.crates.containsKey(activate)){
			active=level.crates.get(activate);
		}
		if (level.walls.containsKey(activate)){
			active=level.walls.get(activate);
		}
		if (level.entities.containsKey(activate)){
			active=level.entities.get(activate);
		}
		active.active=false;
	}
}