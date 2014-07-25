import java.io.IOException;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class Sign extends Entity {
	public Texture texture;

	public Sign(float x, float y) {
		super("");
		this.x = x;
		this.y = y;
		this.width = 12;
		this.height = 18;
		try {
			texture = TextureLoader.getTexture("PNG", this.getClass()
					.getResourceAsStream("sign.png"));
			System.out.println(texture.getImageWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw() {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x-texture.getImageWidth()/2, y-texture.getImageHeight()/2);
			GL11.glTexCoord2f(texture.getWidth(), 0);
			GL11.glVertex2f(x+texture.getImageWidth()/2, y-texture.getImageHeight()/2);
			GL11.glTexCoord2f(texture.getWidth(), texture.getHeight());
			GL11.glVertex2f(x+texture.getImageWidth()/2, y+texture.getImageHeight()/2);
			GL11.glTexCoord2f(0, texture.getHeight());
			GL11.glVertex2f(x-texture.getImageWidth()/2, y+texture.getImageHeight()/2);
			GL11.glEnd();
	}
}
