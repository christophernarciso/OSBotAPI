package omniapi.data;

import org.osbot.rs07.api.def.EntityDefinition;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Model;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class NPC extends OmniScriptEmulator<OmniScript> implements EntityBase {

	private org.osbot.rs07.api.model.NPC child;
	
	public NPC(OmniScript script, org.osbot.rs07.api.model.NPC n) {
		super(script);
		child = n;
	}

	@Override
	public int getGridY() {
		return child.getGridY();
	}

	@Override
	public int getGridX() {
		return child.getGridX();
	}

	@Override
	public int getLocalX() {
		return child.getLocalX();
	}

	@Override
	public int getLocalY() {
		return child.getLocalY();
	}

	@Override
	public int getX() {
		return child.getX();
	}

	@Override
	public int getY() {
		return child.getY();
	}

	@Override
	public int getZ() {
		return child.getZ();
	}

	@Override
	public int getSizeX() {
		return child.getSizeX();
	}

	@Override
	public int getSizeY() {
		return child.getSizeY();
	}

	@Override
	public int getHeight() {
		return child.getHeight();
	}

	@Override
	public int getId() {
		return child.getId();
	}

	@Override
	public int[] getModelIds() {
		return child.getModelIds();
	}

	@Override
	public boolean isVisible() {
		return child.isVisible();
	}

	@Override
	public boolean exists() {
		return (child != null && child.exists());
	}

	@Override
	public boolean examine() {
		if (!isAlive()) return false;
		return interact("Examine");
	}

	@Override
	public boolean hasAction(String... actions) {
		return child.hasAction(actions);
	}

	@Override
	public boolean interact(String... interactions) {
		if (!exists() || !isAlive()) return false;
		if (!hasAction(interactions)) return false;
		if (!child.isVisible()) getCamera().toEntity(child);
		return child.interact(interactions);
	}
	
	/* Custom interaction methods for ease of use :D */
	public boolean attack() {
		if (!isAttackable() || isUnderAttack() || myPlayer().isUnderAttack()) return false;
		return interact("Attack");
	}
	
	public boolean pickpocket() {
		if (isUnderAttack() || myPlayer().isUnderAttack()) return false;
		return interact("Pickpocket");
	}
	
	@Override
	public boolean hover() {
		return child.hover();
	}

	@Override
	public String[] getActions() {
		return child.getActions();
	}

	@Override
	public String getName() {
		return child.getName();
	}

	@Override
	public Position getPosition() {
		return child.getPosition();
	}

	@Override
	public Model getModel() {
		return child.getModel();
	}

	@Override
	public Area getArea(int z) {
		return child.getArea(z);
	}

	@Override
	public EntityDefinition getDefinition() {
		return child.getDefinition();
	}
	
	public int getHealth() {
		return child.getHealth();
	}
	
	public boolean isUnderAttack() {
		return child.isUnderAttack();
	}
	
	public boolean isAttackable() {
		return hasAction("Attack") && (getHealth() > 0);
	}
	
	public boolean isAnimating() {
		return child.isAnimating();
	}
	
	public boolean isMoving() {
		return child.isMoving();
	}
	
	public boolean isAlive() {
		return (getHealth() != 0);
	}
}
