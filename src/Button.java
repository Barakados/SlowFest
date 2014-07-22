
public class Button extends Entity {
	public Level level;
	public String activate;
	public Button(float x, float y, float width, float height, String activate, Level level) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.activate=activate;
		this.level=level;
	}

}
