import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;

public class Entity {
	public String id = "";
	public float vx = 0;
	public float vy = 0;
	public boolean grounded = false;
	public float x = 0;
	public float y = 0;
	public float width = 0;
	public float height = 0;
	public float color = 0;
	public float red = 0f;
	public float green = 0f;
	public float blue = 0f;
	public boolean active=true;
	public boolean useShader;
	public int vertShader = 0;
	public int fragShader = 0;
	public int program = 0;
	public void draw() {
		if (useShader)
			ARBShaderObjects.glUseProgramObjectARB(program);
		GL11.glLoadIdentity();
		GL11.glColor3f(red, green, blue);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x - width / 2, y - height / 2);
		GL11.glVertex2f(x + width / 2, y - height / 2);
		GL11.glVertex2f(x + width / 2, y + height / 2);
		GL11.glVertex2f(x - width / 2, y + height / 2);
		GL11.glEnd();
		if (useShader)
			ARBShaderObjects.glUseProgramObjectARB(0);
	}

	public int createShader(String filename, int shaderType) throws Exception {
		int shader = 0;
		try {
			shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);

			if (shader == 0)
				return 0;

			ARBShaderObjects.glShaderSourceARB(shader,
					readFileAsString(filename));
			ARBShaderObjects.glCompileShaderARB(shader);

			if (ARBShaderObjects.glGetObjectParameteriARB(shader,
					ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
				throw new RuntimeException("Error creating shader: "
						+ getLogInfo(shader));

			return shader;
		} catch (Exception exc) {
			ARBShaderObjects.glDeleteObjectARB(shader);
			throw exc;
		}
	}

	public static String getLogInfo(int obj) {
		return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects
				.glGetObjectParameteriARB(obj,
						ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	}

	public String readFileAsString(String filename) throws Exception {
		StringBuilder source = new StringBuilder();

		InputStream in = this.getClass().getResourceAsStream(filename);

		Exception exception = null;

		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

			Exception innerExc = null;
			try {
				String line;
				while ((line = reader.readLine()) != null)
					source.append(line).append('\n');
			} catch (Exception exc) {
				exception = exc;
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					if (innerExc == null)
						innerExc = exc;
					else
						exc.printStackTrace();
				}
			}

			if (innerExc != null)
				throw innerExc;
		} catch (Exception exc) {
			exception = exc;
		} finally {
			try {
				in.close();
			} catch (Exception exc) {
				if (exception == null)
					exception = exc;
				else
					exc.printStackTrace();
			}

			if (exception != null)
				throw exception;
		}

		return source.toString();
	}

	public boolean collide(Entity move) {
		float distX = Math.abs(x - move.x);
		float distY = Math.abs(y - move.y);
		float combX = (width + move.width) / 2;
		float combY = (height + move.height) / 2;
		if (combY > distY && combX > distX) {
			float movX = combX - distX;
			float movY = combY - distY;
			if (movX < movY) {
				if (id.equals("BOX") && move.id.equals("BOX")) {
					if (x < move.x) {
						move.x += movX;
						move.vx = -move.vx;
					} else {
						move.x -= movX;
						move.vx = -move.vx;
					}
				} else if (id.equals("PLAYER") && move.id.equals("CRATE")) {
					if (x > move.x) {
						move.x -= movX;
						move.vx -= movX;
					} else {
						move.x += movX;
						move.vx += movX;
					}
				} else {
					if (x < move.x) {
						move.x += movX;
					} else {
						move.x -= movX;
					}
					if (move.id.equals("BOX")) {
						move.vx = -move.vx;
					}
				}
				if (move.id.equals("CRATE")&&id.equals("WALL")){
					move.vx=0;
					move.vy=0;
					if (x > move.x) {
						move.x -= movX;
					} else {
						move.x += movX;
					}
				}
			}
			if (movY < movX) {
				if (id.equals("BOX") && move.id.equals("BOX")) {
					if (y > move.y) {
						move.y -= movY;
						move.vy = -move.vy;
					} else {
						move.y += movY;
						move.vy = -move.vy;
					}
				} else if (id.equals("PLAYER") && move.id.equals("CRATE")) {
					if (y > move.y) {
						move.y += movY;
						move.vy -= movY;
					} else {
						move.y += movY;
						move.vy += movY;
					}
				} else if (id.equals("BOX") && move.id.equals("PLAYER")) {
					if (y > move.y) {
						move.y -= movY;
						move.grounded = true;
						move.vx=(float) (vx/.9/Level.speed());
						move.vy=0;
					} else {
						move.y += movY;
					}
				} else {
					if (y > move.y) {
						move.y -= movY;
				
						move.grounded = true;
						if (move.id.equals("PLAYER") || move.id.equals("CRATE")) {
							move.vy = 0;
							move.vy=vy;
						}
						if (move.id.equals("BOX")) {
							move.vy = -move.vy;
						}
					} else {
						move.y += movY;
						if (move.id.equals("BOX")) {
							move.vy = -move.vy;
						}
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean intersects(Entity move) {
		float distX = Math.abs(x - move.x);
		float distY = Math.abs(y - move.y);
		float combX = (width + move.width) / 2;
		float combY = (height + move.height) / 2;
		if (combY > distY && combX > distX) {
			return true;
		} else {
			return false;
		}
	}
}
