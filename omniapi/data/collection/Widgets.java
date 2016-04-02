package omniapi.data.collection;

import java.util.ArrayList;
import java.util.List;

import omniapi.data.Widget;
import org.osbot.rs07.api.ui.RS2Widget;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.api.OmniScriptEmulator;

public class Widgets extends Collector<Widget> {

	public Widgets(OmniScript script) {
		super(script);
		cache = new ArrayList<>();
		lastCacheTime = 0;
	}

	@Override
	public List<Widget> getAll() {
		if (System.currentTimeMillis() > (lastCacheTime + (Constants.TICK / 2))) {
			cache.clear();
			for (RS2Widget o : getRawScript().getWidgets().getAll()) {
				cache.add(new Widget(getScript(), o));
			}
			lastCacheTime = System.currentTimeMillis();
		}
		return cache;
	}
}
