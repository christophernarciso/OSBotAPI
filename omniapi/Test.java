package omniapi;

import java.awt.Color;
import java.awt.Graphics2D;

import omniapi.api.Constants;
import omniapi.data.Widget;
import omniapi.debug.LogLevel;
import omniapi.interact.EntityInteractor;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.RS2Widget;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Bobrocket", info = "testing", logo = "", name = "OmniAPI", version = 0)
public class Test extends OmniScript {

	@Override
	public void onStart() {
		setLogLevel(LogLevel.WARN);
		//getWebWalker().addNode(new Position(3080, 3249, 0), "Master farmer", "Pickpocket");
		//getWebWalker().addNode(new Position(3268, 3410, 0), "Tea stall", "Steal-from");
	}

	//Widget depositAll;
	
	int totalInteractions, failedInteractions, successfulInteractions;
	
	@Override
	public int onLoop() throws InterruptedException {
		
		//if (getNPCFinder().findClosest("Man", (npc) -> (getMap().canReach(npc.getPosition()))).interact("Talk-to")) successfulInteractions++;
		//else failedInteractions++;
		
		//getWidgetFinder().findFromText("Gold amulet", (w) -> (w.getRootId() == 162)).interact();
		//getRSGrandExchange().createBuyOffer("Gold amulet", 2, 150);
		
		//debug(getWebWalker().walkAndInteract("Tea stall", "Steal-from")); //debug(getWebWalker().interactWith(getWebWalker().getClosest("Master farmer"), "Pickpocket", (target) -> (target.getName().equalsIgnoreCase("master farmer"))));
		//debug(getWebWalker().walkPath(new Position(3218, 3218, 0)));
		
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
		return Constants.TICK * 4;
	}

	@Override
	public void onPaint(Graphics2D g) {
	}
}
