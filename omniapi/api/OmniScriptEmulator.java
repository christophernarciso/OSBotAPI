package omniapi.api;

import org.osbot.rs07.script.Script;

import omniapi.OmniScript;
import omniapi.data.Entities;
import omniapi.data.NPCs;

public class OmniScriptEmulator<S extends OmniScript> extends ScriptEmulator<S> {

	private Entities entities;
	
	public OmniScriptEmulator(S script) {
		super(script);
	}

	@Override
	public S getScript() {
		return script;
	}
	
	public Entities getEntities() {
		return script.getEntities();
	}
	
	public NPCs getNPCs() {
		return script.getNPCs();
	}
	
	public Script getRawScript() {
		return script.getRaw();
	}
}
