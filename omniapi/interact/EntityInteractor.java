package omniapi.interact;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import omniapi.data.PhysicalBase;
import org.osbot.rs07.api.model.Item;
import org.osbot.rs07.api.ui.Option;
import org.osbot.rs07.api.util.GraphicUtilities;

import omniapi.OmniScript;
import omniapi.data.Entity;

public class EntityInteractor extends Interactor<PhysicalBase> {

	private PhysicalBase target;
	private Rectangle rect;
	private Item item;
	
	public EntityInteractor(OmniScript script) {
		super(script);
	}

	//setEntity(...).setItem(...).shrinkRectangle(...).do()
	public EntityInteractor setEntity(PhysicalBase e) {
		target = e;
		rect = GraphicUtilities.getModelBoundingBox(getBot(), target.getGridX(), target.getGridY(), target.getZ(), target.getModel());
		return this;
	}
	
	public EntityInteractor shrinkRectangle(int amt) {
		if (rect == null || target == null) return this;
		rect = shrinkRectangle(rect, amt);
		return this;
	}
	
	public EntityInteractor setItem(Item i) {
		item = i;
		return this;
	}
	
	public boolean interact(String interaction, boolean sleep, int deviate) throws InterruptedException {
		if (item == null && (target == null || !target.hasAction(interaction))) return false;
		
		if (getMenuAPI().isOpen()) {
			getMouse().click(false);
			if (sleep) sleep(rand(1 * deviate, 10 * deviate));
		}
		debug("Interacting");
		
		if (getMap().distance(target.getPosition()) > 6 && !getLocalWalker().walk(target.getPosition())) return false;
		
		if (item != null) {
			debug("Selecting item");
			if (getInventory().isItemSelected() && !getInventory().getSelectedItemName().equalsIgnoreCase(item.getName())) {
				getInventory().deselectItem();
				if (sleep) sleep(rand(10 * deviate, 25 * deviate));
			}
			if (!item.hover()) return false;
			debug("Hovered");
			if (sleep) sleep(rand(1 * deviate, 5 * deviate));
			if (!getMouse().click(false)) return false;
			debug("Clicked");
			interaction = "Use " + item.getName() + " -> " + target.getName(); 
		}
		 if (!target.hover()) return false;
		
		if (sleep) sleep(rand(1 * deviate, 10 * deviate));
		
		// Right click to open the menu
		//if (!rect.contains(getMouse().getPosition())) return false;
		debug(getMenuAPI().getTooltip());
		if (getMenuAPI().getTooltip().startsWith(interaction) && getMenuAPI().getTooltip().endsWith(target.getName())) return getMouse().click(false);
		if (!getMouse().click(true)) return false;
		
		if (sleep) sleep(rand(25 * deviate, 40 * deviate));
		
		int index = -1;
		List<Option> options = getMenuAPI().getMenu();
		for (int i = 0; i < options.size(); i++) {
			Option o = options.get(i);
			String s = o.action;
			debug(o.name);
			if ((item != null && getMenuAPI().stripFormatting(o.name).endsWith(target.getName())) || (s.equalsIgnoreCase(interaction) && getMenuAPI().stripFormatting(o.name).startsWith(target.getName()))) {
				index = i;
				break;
			}
		}
		
		if (index == -1) {
			getMouse().click(false); //Remove the menu API
			return false;
		}
		
		int mouseX = (int) getMouse().getPosition().getX();
		int mouseY = (int) getMouse().getPosition().getY();
		
		Rectangle menuRect = getMenuAPI().getOptionRectangle(index);
		
		int startX = (int) menuRect.getX() + 1;
		int startY = (int) menuRect.getY() + 1;
		int centreX = (int)(menuRect.getX() + (menuRect.getWidth() / 2));
		int centreY = (int)(menuRect.getY() + (menuRect.getHeight() / 2));
		int endX = (int)(menuRect.getX() + menuRect.getWidth()) - 1;
		int endY = (int)(menuRect.getY() + menuRect.getHeight()) - 1;
		
		/*
		 * We want to split our bounding box into the following conditions:
		 * Between start and finish -> skewed random point from the x/y within rect
		 * Before start -> skewed random point from start x (positive)
		 * After finish -> skewed random point from start x (negative)
		 * */
		
		boolean north = (mouseY <= startY);
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
		}

		debug("INTERACT betweenx " + betweenX + "; betweeny " + betweenY);
		debug(finalX + ", " + finalY);
		
		if (getMouse().move(finalX, finalY)) return false;
		if (sleep) sleep(rand(5 * deviate, 10 * deviate));
		return getMouse().click(false);
	}
}
