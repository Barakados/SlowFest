import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;

public class Crate extends Entity {
	public Crate(float x, float y, float width, float height) {
		try {
			vertShader = createShader("shaders/box.vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("shaders/box.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		finally {
			if (vertShader==0||fragShader==0){
				return;
			}
		}
		program = ARBShaderObjects.glCreateProgramObjectARB();
		if (program==0)
			return;
		ARBShaderObjects.glAttachObjectARB(program, vertShader);
		ARBShaderObjects.glAttachObjectARB(program, fragShader);
		ARBShaderObjects.glLinkProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB)==GL11.GL_FALSE){
			System.err.println(getLogInfo(program));
			return;
		}
		ARBShaderObjects.glValidateProgramARB(program);
		if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB)==GL11.GL_FALSE){
			System.err.println(getLogInfo(program));
			return;
		}
		useShader=true;
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
