package omniapi.webwalker;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.data.Entity;
import omniapi.interact.RectangleInteractor;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.input.mouse.MiniMapTileDestination;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.ConditionalSleep;

import omniapi.webwalker.data.WebNode;
import omniapi.webwalker.data.WebNodeFinderCondition;
import omniapi.webwalker.data.WebNodeManager;

public class OmniWebWalker extends OmniScriptEmulator<OmniScript> {

	private Script script;
	private WebNodeManager webNodeManager;
	private RectangleInteractor rectangleInteractor;
	
	public OmniWebWalker(OmniScript script) {
		super(script);
		this.script = script;
		webNodeManager = new WebNodeManager(script);
		rectangleInteractor = new RectangleInteractor(script);
	}
	
	public List<Position> toPositionList(List<WebNode> src) {
		List<Position> tmp = new ArrayList<Position>();
		for (WebNode webNode : src) if (webNode != null) tmp.add(webNode.pos());
		return tmp;
	}
	
	public boolean walkToPosition(Position pos) throws InterruptedException {
		if (getMap().distance(pos) <= 3) return true;
		
		if (pos.isOnMiniMap(getBot())) {
			MiniMapTileDestination dest = new MiniMapTileDestination(getBot(), pos);
			if (dest.isVisible()) {
				Rectangle bound = dest.getBoundingBox();
				if (!rectangleInteractor.interact(bound)) return false;
				if (!sleepUntil(() -> (getMap().distance(pos) <= 3), (1500 * getMap().distance(pos)))) return false;
			}
		}
		
		return getMap().distance(pos) <= 3;
	}
	
	public boolean walkPath(WebNode finish) throws InterruptedException {
		return walkPath(myPosition(), finish.pos());
	}
	
	public boolean walkPath(Position finish) throws InterruptedException {
		return walkPath(myPosition(), finish);
	}
	
	public boolean walkPath(Position start, Position finish) throws InterruptedException {
		if (script.getWalking().webWalk(new Position[] { finish })) return true;
		
		List<WebNode> path = webNodeManager.getPath(start, finish);
		if (path == null || path.size() == 0) return false;
		
		debug("running " + path.size());
		
		for (WebNode node : path) {
			if (node.pos().isOnMiniMap(script.getBot())) {
				if (path.indexOf(node) == (path.size() - 1) || node.getName().equals("null")) {
					while (getMap().distance(node.pos()) > 3) walkToPosition(node.pos());
				}
				else if (node.hasAction("Climb") || node.hasAction("Climb-up") || node.hasAction("Climb-down") || node.hasAction("Open") || node.hasAction("Pay-toll(10gp)") || node.hasAction("Talk-to")) {
					Entity entity = getEntityFinder().findClosest(node.getName(), (e) -> (e.getName().equalsIgnoreCase(node.getName()) && e.hasAction(node.getAction())));
					while (getMap().distance(node.pos()) > 3) walkToPosition(node.pos());
					if (!entity.interact(node.getAction())) return false;
					if (!sleepUntil(() -> (script.getMap().distance(node.pos()) <= 1 || !entity.exists() || !entity.hasAction(node.getAction())), (1500 * getMap().distance(node.pos())))) return false;
				}
			}
			
		}
		return (script.getMap().distance(path.get(path.size() - 1).pos()) <= 3);
	}
	
	public WebNode getClosestBank() {
    	return webNodeManager.getClosest((node) -> (node.hasAction("Bank")));
    }
    
    public WebNode getClosestWithNameAndAction(String name, String action) {
    	return webNodeManager.getClosest((node) -> (node.getName().equalsIgnoreCase(name) && node.hasAction(action)));
    }
    
    public WebNode getClosestWithAction(String action) {
    	return webNodeManager.getClosest((node) -> (node.hasAction(action)));
    }
    
    public WebNode getClosestWithName(String name) {
    	return webNodeManager.getClosest((node) -> (node.getName().equalsIgnoreCase(name)));
    }
    
    public WebNode getClosest(WebNodeFinderCondition webNodeFinderCondition) {
    	return webNodeManager.getClosest(webNodeFinderCondition);
    }

    public WebNode getClosest(WebNode webNode) {
        return webNodeManager.getClosest(webNode.pos());
    }
	
}
