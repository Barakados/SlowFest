import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.GL11;


public class Button extends Entity {
	public Level level;
	public String activate;
	public Entity active=null;
	public Button(float x, float y, float width, float height, String activate, Level level) {
		try {
			vertShader = createShader("shaders/button.vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
			fragShader = createShader("shaders/button.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
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
