package omniapi.data;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class Widget extends OmniScriptEmulator<OmniScript> {

	private org.osbot.rs07.api.ui.RS2Widget child;
	
	public Widget(OmniScript script, org.osbot.rs07.api.ui.RS2Widget w) {
		super(script);
		child = w;
	}

	public org.osbot.rs07.api.ui.RS2Widget getRaw() {
		return child;
	}
	
	public int getAbsX() {
		if (!exists()) return -1;
		return child.getAbsX();
	}
	
	public int getAbsY() {
		if (!exists()) return -1;
		return child.getAbsY();
	}
	
	public int getActionType() {
		if (!exists()) return -1;
		return child.getActionType();
	}
	
	public int getAlpha() {
		if (!exists()) return -1;
		return child.getAlpha();
	}
	
	public Widget getChildWidget(int childId) {
		if (!exists()) return new DefaultWidget(getScript());
		return new Widget(getScript(), child.getChildWidget(childId));
	}
	
	public int getContentType() {
		if (!exists()) return -1;
		return child.getContentType();
	}
	
	public int getHeight() {
		if (!exists()) return -1;
		return child.getHeight();
	}
	
	public int getWidth() {
		if (!exists()) return -1;
		return child.getWidth();
	}
	
	public String[] getInteractActions() {
		if (!exists() || child.getInteractActions() == null) return new String[] { "null" };
		return child.getInteractActions();
	}
	
	public boolean hasAction(String... actions) {
		if (!exists()) return false;
		if (getInteractActions() == null || getInteractActions().length < 1) return false;
		List<String> actionList = Arrays.asList(getInteractActions());
		for (String action : actions) {
			if (!actionList.contains(action)) return false;
		}
		return true;
	}
	
	public String[] getInteractOptions() {
		if (!exists() || child.getInteractOptions() == null) return new String[] { "null" };
		return child.getInteractOptions();
	}
	
	public String getMessage() {
		if (!exists() || child.getMessage() == null) return "null";
		return child.getMessage();
	}
	
	public Rectangle getRectangle() {
		if (!exists()) return new Rectangle(0, 0, 0, 0);
		return child.getRectangle();
	}
	
	public Rectangle getBounds() {
		if (!exists()) return new Rectangle(0, 0, 0, 0);
		return child.getBounds();
	}
	
	public boolean exists() {
		return (child != null && child.isVisible());
	}
	
	public boolean isVisible() {
		if (!exists()) return false;
		return child.isVisible();
	}
	
	public int getSpriteIndex1() {
		if (!exists()) return -1;
		return child.getSpriteIndex1();
	}
	
	public int getSpriteIndex2() {
		if (!exists()) return -1;
		return child.getSpriteIndex2();
	}
	
	public int getRootId() {
		if (!exists()) return -1;
		return child.getRootId();
	}
	
	public int getSecondLevelId() {
		if (!exists()) return -1;
		return child.getSecondLevelId();
	}
	
	public int getThirdLevelId() {
		if (!exists()) return -1;
		return child.getThirdLevelId();
	}
	
	public boolean interact(String... interactions) {
		if (!exists()) return false;
		return child.interact(interactions);
	}
}
