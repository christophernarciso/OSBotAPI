package omniapi.finders;

import org.osbot.rs07.script.Script;

import omniapi.api.ScriptEmulator;

public abstract class VirtualFinder<T> extends ScriptEmulator<Script> {

	public VirtualFinder(Script script) {
		super(script);
	}

	public abstract T find(FinderCondition<T> condition);
}
