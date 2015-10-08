package omniapi;

import omniapi.api.Constants;

import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Bobrocket", info = "testing", logo = "", name = "OmniAPI", version = 0)
public class Test extends OmniScript {

	@Override
	public int onLoop() throws InterruptedException {
		getNPCFinder().findClosest("Man").attack();
		RS2Widget closeButton = getWidgetFinder().findFromAction("Close", (widget) -> (widget.getSpriteIndex1() == 535));
		return Constants.TICK * 10;
	}

}
