import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;


public class Player extends Entity {
	public static double ACCELERATION =Math.PI/65+.4;
	public void update() {
		vy+=ACCELERATION;
		vx=(float) Math.max(Math.min(3, vx), -3);
		vy=(float) Math.max(Math.min(7, vy), -7);
		vx*=.9;
		x+=vx;
		y+=vy;
		
	}
	public Player(float x, float y) {
		try {
			vertShader = createShader("shaders/screen.vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("shaders/screen.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
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
		id="PLAYER";
		this.x=x;
		this.y=y;
		this.width=20;
		this.height=20;
		useShader=true;
	}


}
