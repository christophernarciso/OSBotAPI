package omniapi.interact;

import java.awt.Rectangle;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public abstract class Interactor extends OmniScriptEmulator<OmniScript> {

	private final int INTERACT_MINIMUM_SIZE = 4;
	
	public Interactor(OmniScript script) {
		super(script);
	}

	protected Rectangle shrinkRectangle(Rectangle rect, int amt) {
		int width = (int)(rect.getWidth() - amt);
		int height = (int)(rect.getHeight() - amt);
		int x = (int)(rect.getX() + (amt / 2));
		int y = (int)(rect.getY() + (amt / 2));
		
		if (width < INTERACT_MINIMUM_SIZE) width = INTERACT_MINIMUM_SIZE;
		if (height < INTERACT_MINIMUM_SIZE) height = INTERACT_MINIMUM_SIZE;
		
		return new Rectangle(x, y, width, height);
	}
}
