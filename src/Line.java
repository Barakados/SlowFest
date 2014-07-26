import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.TextureImpl;

public class Line {
	public Level level;
	public String line;
	public AngelCodeFont fnt;
	public int count = 0;
	public int magic = 60;
	public int strat = 2;
	public boolean isDone() {
		if (line.contains("|")) {
			String connect = line.split("\\|")[1];
			String[] parameter = connect.split(",");
			if (parameter[0].equals("ACTIVATE")) {
				level.connect(parameter[1]).active = true;
			}
			return true;
		} else {
			if (count < line.length()) {
				fnt.drawString(200, 550, line.substring(0, count));
				if (strat==0){
				count++;
				strat=2;
				}else{
					strat--;
				}
				return false;
			} else if (magic > 0) {
				fnt.drawString(200, 550, line.substring(0, count));
				magic--;
				return false;
			} else {
				return true;
			}
		}
	}

	public Line(String type, Level l) {
		this.level = l;
		this.line = type;
		if (line.contains("|")) {
			String connect = line.split("\\|")[1];
			String[] parameter = connect.split(",");
			if (parameter[0].equals("ACTIVATE")) {
				level.connect(parameter[1]).active = false;
			}
		}
		try {
			TextureImpl.unbind();
			Image img = new Image("Arial.png");
			img.bind();
			fnt = new AngelCodeFont("Arial.fnt", img);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (line.split("\"").length == 2) {

		}
	}

}
