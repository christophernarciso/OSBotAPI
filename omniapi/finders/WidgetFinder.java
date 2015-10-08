package omniapi.finders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import omniapi.OmniScript;

import org.osbot.rs07.api.ui.RS2Widget;

public class WidgetFinder extends VirtualFinder<RS2Widget> {

	public WidgetFinder(OmniScript script) {
		super(script);
	}

	@Override
	public RS2Widget find(FinderCondition<RS2Widget> condition) {
		return (last = getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && condition.meetsCondition(widget))).findFirst().orElse(null));
	}
	
	public RS2Widget findFromText(String text) {
		return (last = getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widget.getMessage().toLowerCase().contains(text.toLowerCase()))).findFirst().orElse(null));
	}
	
	public List<RS2Widget> findAllFromText(String text) {
		return getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widget.getMessage().toLowerCase().contains(text.toLowerCase()))).collect(Collectors.toList());
	}
	
	public RS2Widget findFromText(String text, FinderCondition<RS2Widget> condition) {
		return (last = getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widget.getMessage().toLowerCase().contains(text.toLowerCase()) && condition.meetsCondition(widget))).findFirst().orElse(null));
	}
	
	public List<RS2Widget> findAllFromText(String text, FinderCondition<RS2Widget> condition) {
		return getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widget.getMessage().toLowerCase().contains(text.toLowerCase()) && condition.meetsCondition(widget))).collect(Collectors.toList());
	}
	
	public RS2Widget findFromAction(String action) {
		return (last = getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widgetHasMethod(widget, action))).findAny().orElse(null));
	}
	
	public List<RS2Widget> findAllFromAction(String action) {
		return getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widgetHasMethod(widget, action))).collect(Collectors.toList());
	}
	
	public RS2Widget findFromAction(String action, FinderCondition<RS2Widget> condition) {
		return (last = getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widgetHasMethod(widget, action) && condition.meetsCondition(widget))).findAny().orElse(null));
	}
	
	public List<RS2Widget> findAllFromAction(String action, FinderCondition<RS2Widget> condition) {
		return getWidgets().getAll().stream().filter((widget) -> (widget != null && widget.isVisible() && widgetHasMethod(widget, action) && condition.meetsCondition(widget))).collect(Collectors.toList());
	}
	
	public RS2Widget get(int root, int child) {
		return (last = getWidgets().get(root, child));
	}
	
	public RS2Widget get(int root, int child, int grandchild) {
		return (last = getWidgets().get(root, child, grandchild));
	}
	
	/* Private methods */
	private boolean widgetHasMethod(RS2Widget widget, String action) {
		if (widget == null) return false;
		if (widget.getInteractActions() == null || widget.getInteractActions().length <= 1) return false;
		List<String> actions = Arrays.asList(widget.getInteractActions());
		return actions.stream().filter(str -> (str != null && str.equalsIgnoreCase(action))).findAny().orElse(null) != null;
	}
}
