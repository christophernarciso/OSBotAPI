package omniapi.data.collection;

import java.util.ArrayList;
import java.util.List;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;
import omniapi.data.NPC;

public class NPCs extends Collector<NPC> {

	public NPCs(OmniScript script) {
		super(script);
		cache = new ArrayList<>();
		lastCacheTime = 0;
	}

	@Override
	public List<NPC> getAll() {
		if (System.currentTimeMillis() > lastCacheTime + (Constants.TICK / 2)) {
			cache.clear();
			for (org.osbot.rs07.api.model.NPC n : getRawScript().getNpcs().getAll()) {
				cache.add(new NPC(getScript(), n));
			}
			lastCacheTime = System.currentTimeMillis();
		}
		return cache;
	}
}
