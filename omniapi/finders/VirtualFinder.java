package omniapi.finders;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;

public abstract class VirtualFinder<T> extends OmniScriptEmulator<OmniScript> {

	protected T last;
	
	public VirtualFinder(OmniScript script) {
		super(script);
	}

	public abstract T find(FinderCondition<T> condition);
	
	public boolean canFind(FinderCondition<T> condition) {
		return (find(condition) != null);
	}
	
	
	
	public T getLastFound() {
		return last;
	}
}
