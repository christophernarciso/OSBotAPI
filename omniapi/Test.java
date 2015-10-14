package omniapi;

import omniapi.api.Constants;
import omniapi.data.Widget;
import omniapi.debug.LogLevel;
import omniapi.interact.EntityInteractor;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Bobrocket", info = "testing", logo = "", name = "OmniAPI", version = 0)
public class Test extends OmniScript {

	@Override
	public void onStart() {
		setLogLevel(LogLevel.WARN);
	}
	
	@Override
	public int onLoop() throws InterruptedException {
		//getNpcs().closest("Dwarf").interact("Attack");
		
		getWebWalker().walkPath(new Position(3085, 3533, 0));
		
		//Widget w = 
		//Widget w = getWidgetFinder().find((widget) -> (widget.getSpriteIndex1() == 535));
		//debug(new EntityInteractor(this).setEntity(getEntityFinder().findClosest("Bank booth")).setItem(getInventory().getItem("Pot")).shrinkRectangle(10).interact("", true, 8));
		//debug(getNPCFinder().findClosest("Banker").interact("Bank"));
		//debug(getEntityFinder().findClosest("Bank booth").interact("Collect"));
		//sleep(Constants.TICK * 2);
		//debug(getWidgetFinder().findFromAction("Close", (widget) -> (widget.getSpriteIndex1() == 535)).interact());
		//log(w.getInteractOptions());
		return Constants.TICK / 2;
	}

}
