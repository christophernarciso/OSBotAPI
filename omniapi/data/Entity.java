package omniapi.data;

import java.awt.Rectangle;

import org.osbot.rs07.api.def.EntityDefinition;
import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.Model;
import org.osbot.rs07.api.util.GraphicUtilities;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.interact.EntityInteractor;

public class Entity extends OmniScriptEmulator<OmniScript> implements EntityBase {

	private org.osbot.rs07.api.model.Entity child;
	
	public Entity(OmniScript script, org.osbot.rs07.api.model.Entity e) {
		super(script);
		child = e;
	}
	
	public org.osbot.rs07.api.model.Entity getRaw() {
		return child;
	}

	@Override
	public int getGridY() {
		if (!exists()) return -1;
		return child.getGridY();
	}

	@Override
	public int getGridX() {
		if (!exists()) return -1;
		return child.getGridX();
	}

	@Override
	public int getLocalX() {
		if (!exists()) return -1;
		return child.getLocalX();
	}

	@Override
	public int getLocalY() {
		if (!exists()) return -1;
		return child.getLocalY();
	}

	@Override
	public int getX() {
		if (!exists()) return -1;
		return child.getX();
	}

	@Override
	public int getY() {
		if (!exists()) return -1;
		return child.getY();
	}

	@Override
	public int getZ() {
		if (!exists()) return -1;
		return child.getZ();
	}

	@Override
	public int getSizeX() {
		if (!exists()) return -1;
		return child.getSizeX();
	}

	@Override
	public int getSizeY() {
		if (!exists()) return -1;
		return child.getSizeY();
	}

	@Override
	public int getHeight() {
		if (!exists()) return -1;
		return child.getHeight();
	}

	@Override
	public int getId() {
		if (!exists()) return -1;
		return child.getId();
	}

	@Override
	public int[] getModelIds() {
		return child.getModelIds();
	}

	@Override
	public boolean isVisible() {
		if (!exists()) return false;
		return child.isVisible();
	}

	@Override
	public boolean exists() {
		return (child != null && child.exists());
	}

	@Override
	public boolean examine() throws InterruptedException {
		return interact("Examine");
	}

	@Override
	public boolean hasAction(String... actions) {
		if (!exists()) return false;
		return child.hasAction(actions);
	}
	
	public boolean interact(boolean sleep, String... interactions) throws InterruptedException {
		if (!exists()) return false;
		if (!hasAction(interactions)) return false;
		if (!child.isVisible()) getCamera().toEntity(child);
		for (String interaction : interactions) {
			 if (!new EntityInteractor(getScript()).setEntity(this).shrinkRectangle(8).interact(interaction, sleep, 5)) return false;
		}
		return true;
	}

	@Override
	public boolean interact(String... interactions) throws InterruptedException {
		return interact(true, interactions);
	}

	/* Custom interaction methods */
	public boolean use() throws InterruptedException {
		return interact("Use");
	}
	
	public boolean hover(boolean reHover) {
		if (!exists()) return false;
		if (!reHover && GraphicUtilities.getModelBoundingBox(getBot(), getGridX(), getGridY(), getZ(), getModel()).contains(getMouse().getPosition())) return true;
		
		int mouseX = (int) getMouse().getPosition().getX();
		int mouseY = (int) getMouse().getPosition().getY();
		
		Rectangle rect = GraphicUtilities.getModelBoundingBox(getBot(), getGridX(), getGridY(), getZ(), getModel());
		
		int startX = (int) rect.getX() + 8;
		int startY = (int) rect.getY() + 8;
		int centreX = (int)(rect.getX() + (rect.getWidth() / 2));
		int centreY = (int)(rect.getY() + (rect.getHeight() / 2));
		int endX = (int)(rect.getX() + rect.getWidth()) - 8;
		int endY = (int)(rect.getY() + rect.getHeight()) - 8;
		
		/*
		 * We want to split our bounding box into the following conditions:
		 * Between start and finish -> skewed random point from the x/y within rect
		 * Before start -> skewed random point from start x (positive)
		 * After finish -> skewed random point from start x (negative)
		 * */
		
		/*boolean north = (mouseY <= startY);
		boolean east = (mouseX >= endX);
		boolean south = (mouseY >= endY);
		boolean west = (mouseX <= startX);
		
		boolean betweenX = (mouseX > startX) && (mouseX < endX);
		boolean betweenY = (mouseY > startY) && (mouseY < endY);
		
		int finalX = 0;
		int finalY = 0;
		
		if (betweenX) {
			if (mouseX > centreX) finalX = negativeSkewedRandom(startX, mouseX);
			else finalX = positiveSkewedRandom(mouseX, endX);
		}
		else {
			if (west) finalX = positiveSkewedRandom(startX, endX);
			else finalX = negativeSkewedRandom(startX, endX);
		}
		
		if (betweenY) {
			if (mouseY > centreY) finalY = negativeSkewedRandom(startY, mouseY);
			else finalY = positiveSkewedRandom(mouseY, endY);
		}
		else {
			if (north) finalY = positiveSkewedRandom(startY, endY);
			else finalY = negativeSkewedRandom(startY, endY);
		}*/
		
		int finalX = rand(startX, endX);
		int finalY = rand(startY, endY);
		
		Rectangle areaOfUncertainty = new Rectangle(finalX - 2, finalY - 2, 5, 5);
		//debug("betweenx " + betweenX + "; betweeny " + betweenY);
		debug(finalX + ", " + finalY);
		return !getMouse().move(finalX, finalY);
	}
	
	@Override
	public boolean hover() {
		if (!exists()) return false;
		
		return hover(false);
	}

	@Override
	public String[] getActions() {
		return child.getActions();
	}

	@Override
	public String getName() {
		if (!exists()) return "null";
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

}
