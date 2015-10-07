package omniapi;

import omniapi.finders.EntityFinder;
import omniapi.finders.NPCFinder;
import omniapi.finders.WidgetFinder;

import org.osbot.rs07.script.Script;

public abstract class OmniScript extends Script {

	private EntityFinder entityFinder = new EntityFinder(this);
	private NPCFinder npcFinder = new NPCFinder(this);
	private WidgetFinder widgetFinder = new WidgetFinder(this);
	
	/* Getters */
	public EntityFinder getEntityFinder() {
		return entityFinder;
	}
	
	public NPCFinder getNPCFinder() {
		return npcFinder;
	}
	
	public WidgetFinder getWidgetFinder() {
		return widgetFinder;
	}
}
