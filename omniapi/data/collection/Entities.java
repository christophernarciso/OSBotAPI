package omniapi.data.collection;

import java.util.ArrayList;
import java.util.List;

import omniapi.data.Entity;
import org.osbot.rs07.api.model.RS2Object;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;

public class Entities extends Collector<Entity> {

	public Entities(OmniScript script) {
		super(script);
		cache = new ArrayList<>();
		lastCacheTime = 0;
	}

	@Override
	public List<Entity> getAll() {
		if (System.currentTimeMillis() > (lastCacheTime + (Constants.TICK / 2))) {
			cache.clear();
			for (RS2Object o : getObjects().getAll()) {
				cache.add(new Entity(getScript(), o));
			}
			lastCacheTime = System.currentTimeMillis();
		}
		return cache;
	}
}
