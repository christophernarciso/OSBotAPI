package omniapi.webwalker.data;

import java.util.ArrayList;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.Skill;

public class WebNode extends OmniScriptEmulator<OmniScript> {

	private int id, x, y, z;
	private String name;
	private IntList children;
	private String[] actions;
	
	private int gCost;
	private WebNode parent;
	private Position pos;
	
	private List<WebwalkerRequirement> requirements = new ArrayList<WebwalkerRequirement>();
	
	public WebNode(OmniScript script, String data) {
		super(script);
		
		String[] parts = data.split(",");
		this.id = Integer.valueOf(parts[0]);
		this.x = Integer.valueOf(parts[1]);
		this.y = Integer.valueOf(parts[2]);
		this.z = Integer.valueOf(parts[3]);
		this.name = parts[4];
		//debug(data);
		if (parts[5].contains("{")) { //Requirements
			parts[5] = parts[5].replace("{", "#");
			System.out.println(parts[5]);
			String[] actionParts = parts[5].split("#");
			System.out.println(actionParts);
			String actions = actionParts[0];
			
			//debug(actions);
			this.actions = (actions.contains(";") ? actions.split(";") : new String[] { actions });
			actionParts[0] = "";
			for (String s : actionParts) {
				if (s.length() > 1 && s.contains("=")) {
					String[] reqParts = s.split("=");
					int val = Integer.valueOf(reqParts[1]);
					switch (reqParts[0]) {
						case "coins":
							requirements.add(() -> (getInventory().getAmount("Coins") >= val));
							break;
							
						case "agility":
							requirements.add(() -> (getSkills().getDynamic(Skill.AGILITY) >= val));
							break;
							
						case "strength":
							requirements.add(() -> (getSkills().getDynamic(Skill.STRENGTH) >= val));
							break;
							
						case "ranged":
							requirements.add(() -> (getSkills().getDynamic(Skill.RANGED) >= val));
							break;
					}
				}
			}
		}
		else {
			//debug(parts[5]);
			this.actions = (parts[5].contains(";") ? parts[5].split(";") : new String[] { parts[5] });
		}
		
		this.pos = new Position(x, y, z);
		this.children = new IntList((data.contains(";") ? parts[6].split(";").length : 1));
		if (data.contains(";")) for (String child : parts[6].split(";")) children.add(Integer.valueOf(child));
	}
	
	public WebNode(OmniScript script, int id, int x, int y, int z, String name, String... actions) {
		super(script);
		this.id = id;
		this.x = x;
		this.y = y;
		this.z = z;
		this.name = name;
		this.actions = actions;
		this.pos = new Position(x, y, z);
		this.children = new IntList();
	}
	
	public void setParent(WebNode webNode) {
		parent = webNode;
	}
	
	public WebNode getParent() {
		return parent;
	}
	
	public void setF(int g) {
		gCost = g;
	}
	
	public int getF() {
		return gCost;
	}
	
	public void addChild(int i) {
		if (!children.contains(i)) children.add(i);
	}
	
	/*public void setChildren(List<Integer> children) {
		this.children = children;
	}*/
	
	public String toString() {
		String baseString = id + "," + x + "," + y + "," + z + "," + name + "," + actions[0] + ",";
		for (int child : children.get()) baseString += child + ";";
		return baseString;
	}
	
	public boolean hasActionDeep(String action) {
		if (actions == null || actions.length <= 0) return false;
		System.out.println("hasActionDeep " + action);
		for (String str : actions) for (String deepStr : str.split("/")) if (deepStr.equalsIgnoreCase(action)) return true;
		return false;
	}
	
	public boolean hasAction(String action) {
		if (actions == null || actions.length <= 0) return false;
		for (String str : actions) if (str.equalsIgnoreCase(action)) return true;
		return false;
	}
	
	public boolean hasAnyAction(String... findActions) {
		if (actions == null || actions.length <= 0) return false;
		for (String str : findActions) if (hasAction(str)) return true;
		return false;
	}
	
	public boolean hasAction(String... findActions) {
		if (actions == null || actions.length <= 0) return false;
		for (String str : findActions) if (!hasAction(str)) return false;
		return true;
	}
	
	public String getName() {
		return (name == null ? "null" : name);
	}
	
	public String getAction() {
		if (actions.length <= 0) return "null";
		return (actions[0] == null ? "null" : actions[0]);
	}
	
	public boolean meetsRequirements() {
		if (requirements.size() <= 0) return true;
		log("Assessing requirements for id " + id);
		log("Action " + getAction());
		for (WebwalkerRequirement req : requirements) if (!req.test()) return false;
		log("Meets all requirements!!");
		return true;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}
	
	public int getId() {
		return id;
	}
	
	public IntList getChildren() {
		return children;
	}
	
	public Position pos() {
		return pos;
	}
}
