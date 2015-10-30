package omniapi;

import java.awt.Color;
import java.awt.Graphics2D;

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

	//Widget depositAll;
	
	int totalInteractions, failedInteractions, successfulInteractions;
	
	@Override
	public int onLoop() throws InterruptedException {
		
		if (getNPCFinder().findClosest("Man", (npc) -> (getMap().canReach(npc.getPosition()))).interact("Talk-to")) successfulInteractions++;
		else failedInteractions++;
		
		//debug(getRSGrandExchange().getItemNameBySlot(0));
		//debug(getRSGrandExchange().getAvailableSlots());
		//debug(getWidgetFinder().get(162, 32).getMessage());
		//debug(getRSGrandExchange().createOffer(getInventoryFinder().find("Feather"), 1));
		//debug(getRSGrandExchange().createOffer(getInventory().getItem("Small fishing net"), 1));
		//getNpcs().closest("Dwarf").interact("Attack");
		/*depositAll = getWidgetFinder().get(309, 4);
		debug(depositAll.getBounds());
		debug(depositAll.getRectangle());*/
		//getWebWalker().walkPath(new Position(3025, 3736, 0));
		//debug(getNPCFinder().findClosest("Man").pickpocket());
		//Widget w = 
		//Widget w = getWidgetFinder().find((widget) -> (widget.getSpriteIndex1() == 535));
		//debug(new EntityInteractor(this).setEntity(getEntityFinder().findClosest("Bank booth")).setItem(getInventory().getItem("Pot")).shrinkRectangle(10).interact("", true, 8));
		//debug(getNPCFinder().findClosest("Banker").interact("Bank"));
		//debug(getEntityFinder().findClosest("Bank booth").interact("Collect"));
		//sleep(Constants.TICK * 2);
		//debug(getWidgetFinder().findFromAction("Close", (widget) -> (widget.getSpriteIndex1() == 535)).interact());
		//log(w.getInteractOptions());
		return Constants.TICK;
	}

	@Override
	public void onPaint(Graphics2D g) {
		g.drawString("OmniAPI Interaction test: moving npc", 100, 100);
		g.drawString("Successful: " + successfulInteractions, 100, 150);
		g.drawString("Failed: " + failedInteractions, 100, 200);
		g.drawString("%: " + (float)(((failedInteractions) / (failedInteractions + successfulInteractions)) * 100), 100, 250);
	}
}
