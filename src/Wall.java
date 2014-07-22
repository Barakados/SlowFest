import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;


public class Wall extends Entity{

	public Wall(float x, float y, float width, float height) {
		try {
			vertShader = createShader("shaders/wall.vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("shaders/wall.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
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
		this.x=x;
		id="WALL";
		this.y=y;
		this.width=width;
		this.height=height;
		useShader=true;
	}


}