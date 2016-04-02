package omniapi.data.collection;

import omniapi.OmniScript;
import omniapi.api.OmniScriptEmulator;
import omniapi.data.VirtualBase;

import java.util.List;

/**
 * Created by Bobrocket on 23/03/2016.
 */
public abstract class Collector<T extends VirtualBase> extends OmniScriptEmulator<OmniScript> {

    protected List<T> cache;
    protected long lastCacheTime;

    public Collector(OmniScript script) {
        super(script);
    }

    public abstract List<T> getAll();
}
