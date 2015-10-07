package omniapi;

import org.osbot.rs07.api.model.Entity;
import org.osbot.rs07.api.ui.RS2Widget;

public class Test extends OmniScript {

	@Override
	public int onLoop() throws InterruptedException {
		Entity man = getEntityFinder().findClosest("Man");
		RS2Widget closeButton = getWidgetFinder().findFromAction("Close", (widget) -> (widget.getSpriteIndex1() == 535));
		return 0;
	}

}
