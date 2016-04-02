package omniapi.data.collection;

import omniapi.OmniScript;
import omniapi.api.Constants;
import omniapi.data.GroundItem;

import java.util.List;

/**
 * Created by Bobrocket on 23/03/2016.
 */
public class GroundItems extends Collector<GroundItem> {
    public GroundItems(OmniScript script) {
        super(script);
    }

    @Override
    public List<GroundItem> getAll() {
        if (System.currentTimeMillis() > (lastCacheTime + (Constants.TICK / 2))) {
            cache.clear();
            for (org.osbot.rs07.api.model.GroundItem o : getRawScript().getGroundItems().getAll()) {
                cache.add(new GroundItem(getScript(), o));
            }
            lastCacheTime = System.currentTimeMillis();
        }
        return cache;
    }
}
