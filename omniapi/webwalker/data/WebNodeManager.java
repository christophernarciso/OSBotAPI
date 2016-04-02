package omniapi.webwalker.data;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

import org.osbot.rs07.api.map.Position;

public class WebNodeManager extends OmniScriptEmulator<OmniScript> {

    List<WebNode> map = new ArrayList<WebNode>();

    public WebNodeManager(OmniScript script) {
    	super(script);
        List<String> lines;
        lines = readAllLines("C:\\web.dat");
        for (String line : lines) {
            if (line.contains(",")) {
                //script.log(line);
                map.add(new WebNode(line));
            }
        }
    }

    private List<String> readAllLines(String fileName) {
        List<String> retList = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/Bobrocket/TestWebWalker/master/web.dat").openStream())); //new BufferedReader(new FileReader(fileName)); //
            String line;
            while ((line = reader.readLine()) != null) retList.add(line);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    private int distance(WebNode a, WebNode b) {
        return distance(a.pos(), b.pos());
    }

    private int distance(Position a, Position b) {
        int horizontalCost = 10;
        int diagonalCost = 14;

        int diffX = Math.abs(a.getX() - b.getX()); //a^2
        int diffY = Math.abs(a.getY() - b.getY()); //b^2

        if (diffX == 0 && diffY == 0) return 0;

        return horizontalCost * (diffX + diffY) + (diagonalCost - 2 * horizontalCost) * Math.min(diffX, diffY);
    }

    public WebNode getClosest(Position p) {
        int lowestDistance = Integer.MAX_VALUE;
        WebNode closestNode = null;
        for (WebNode node : map) {
            int currentDistance = distance(p, node.pos());
            if (currentDistance < lowestDistance) {
                lowestDistance = currentDistance;
                closestNode = node;
            }
        }
        return closestNode;
    }
    
    public WebNode getClosestBank() {
    	return getClosest((node) -> (node.hasAction("Bank")));
    }
    
    public WebNode getClosestWithNameAndAction(String name, String action) {
    	return getClosest((node) -> (node.getName().equalsIgnoreCase(name) && node.hasAction(action)));
    }
    
    public WebNode getClosestWithAction(String action) {
    	return getClosest((node) -> (node.hasAction(action)));
    }
    
    public WebNode getClosestWithName(String name) {
    	return getClosest((node) -> (node.getName().equalsIgnoreCase(name)));
    }
    
    public WebNode getClosest(WebNodeFinderCondition webNodeFinderCondition) {
    	int lowestDistance = Integer.MAX_VALUE;
    	WebNode closestNode = null;
    	for (WebNode node : map) {
    		if (!webNodeFinderCondition.test(node)) continue;
    		
    		int currentDistance = distance(myPosition(), node.pos());
    		if (currentDistance < lowestDistance) {
    			lowestDistance = currentDistance;
    			closestNode = node;
    		}
    	}
    	return closestNode;
    }

    public WebNode getClosest(WebNode webNode) {
        return getClosest(webNode.pos());
    }

    public List<WebNode> getPath(Position start, Position end) {
        if (map.size() == 0) return null;
        return getPath(getClosest(start), getClosest(end));
    }

    private List<WebNode> finalList = new ArrayList<WebNode>();
    private ArrayDeque<WebNode> open = new ArrayDeque<>();
    private ArrayDeque<Position> openPos = new ArrayDeque<>();
    private Set<WebNode> finalListSet = new HashSet<WebNode>(); // LLAMAGOD

    private List<WebNode> closed = new ArrayList<WebNode>();
    private List<Position> closedPos = new ArrayList<Position>();

    public List<WebNode> getPath(WebNode start, WebNode end) {
        //System.gc();
        open.clear();
        openPos.clear();
        //ArrayDeque<WebNode> open = new ArrayDeque<>();
        //ArrayDeque<Position> openPos = new ArrayDeque<>();

        open.add(start);
        openPos.add(start.pos());

        //List<WebNode> closed = new ArrayList<WebNode>();
        //List<Position> closedPos = new ArrayList<Position>();
        closed.clear();
        closedPos.clear();

        while (!open.isEmpty()) {
            //script.log("!open.isEmpty()");
            WebNode current = open.getFirst();//open.get(open.size() - 1);

            open.removeFirst();
            openPos.removeFirst();

            closed.add(current);
            closedPos.add(current.pos());

            if (current.pos().equals(end.pos())) {
                //script.log("open size " + open.size());
                finalList.clear();
                finalListSet.clear();
                //List<WebNode> finalList = new ArrayList<WebNode>();
                while (current != null && !finalListSet.contains(current)) { // LLAMAGOD
                    finalList.add(current);
                    finalListSet.add(current); // LLAMAGOD
                    //System.out.println(finalList.size() + " " + finalList.get(finalList.size() - 1));
                    current = current.getParent();
                }
                Collections.reverse(finalList);
                return finalList;
            }

            for (WebNode neighbour : getChildren(current)) {
                int f = getGScore(start, current) + distance(neighbour, current);

                if (closedPos.contains(neighbour.pos())) continue;

                if (!openPos.contains(neighbour.pos()) || f < getGScore(start, neighbour)) {
                    int neighbourF = getGScore(start, end) + distance(neighbour, end);
                    neighbour.setF(neighbourF);
                    neighbour.setParent(current);
                    if (!openPos.contains(neighbour.pos())) {
                        //script.log("adding new node to list " + neighbour.getId());
                        if (!open.isEmpty() && neighbourF < open.getFirst().getF()) {
                            open.addFirst(neighbour);
                            openPos.addFirst(neighbour.pos());
                        } else {
                            open.addLast(neighbour);
                            openPos.addLast(neighbour.pos());
                        }
                    }
                }
                if (!openPos.contains(neighbour.pos()) && !closedPos.contains(neighbour.pos())) {
                    neighbour.setF(f);
                    neighbour.setParent(current);
                    open.add(neighbour);
                    openPos.add(neighbour.pos());
                }
            }
        }
        return null;
    }

    private int getGScore(WebNode start, WebNode pos) {
        if (pos != null && start != null) {
            double dx = start.getX() - pos.getX();
            double dy = start.getY() - pos.getY();
            return (int) Math.sqrt((dx * dx) + (dy * dy));
        }
        return Integer.MAX_VALUE;
    }

    public List<WebNode> getChildren(WebNode webNode) {
        List<WebNode> cache = new ArrayList<WebNode>();
        for (int child : webNode.getChildren().get()) cache.add(map.get(child - 1));
        return cache;
    }
}
