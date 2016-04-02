package omniapi.interact;

import java.awt.Point;
import java.awt.Rectangle;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public class RectangleInteractor extends OmniScriptEmulator<OmniScript> {

	public RectangleInteractor(OmniScript script) {
		super(script);
		// TODO Auto-generated constructor stub
	}
	
	public boolean interact(Rectangle src) throws InterruptedException {
		return interact(src, true, 5);
	}
	
	public boolean interact(Rectangle src, boolean sleep, int deviate) throws InterruptedException {
		int mouseX = (int) getMouse().getPosition().getX();
		int mouseY = (int) getMouse().getPosition().getY();
		int startX = (int) src.getX();
		int startY = (int) src.getY();
		int endX = (int)(startX + src.getWidth());
		int endY = (int)(startY + src.getHeight());
		int centreX = (int)(startX + (src.getWidth() / 2));
		int centreY = (int)(startY + (src.getHeight() / 2));
		Point mousePoint = getDistributedRandom(mouseX, mouseY, startX, startY, endX, endY, centreX, centreY);
		if (getMouse().move((int)mousePoint.getX(), (int)mousePoint.getY())) return false;
		if (sleep) sleep(rand(10 * deviate, 20 * deviate));
		if (!src.contains(getMouse().getPosition())) return false;
		return getMouse().click(false);
	}
	
	public Point getDistributedRandom(int mouseX, int mouseY, int startX, int startY, int endX, int endY, int centreX, int centreY) {
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
		
		return new Point(finalX, finalY);
	}

}
