package omniapi;

import omniapi.api.modules.Module;
import omniapi.api.modules.RemoveRandoms;

/**
 * Created by Bobrocket on 23/03/2016.
 */
public abstract class ManagedOmniScript extends OmniScript {

    @Override
    public void onStart() {
        addModule(new RemoveRandoms(this));
        start();
    }

    @Override
    public int onLoop() throws InterruptedException {
        for (Module m : getModuleList()) if (m.shouldActivate()) sleep(m.activate());

        return loop();
    }

    @Override
    public void onExit() {
        exit();
    }

    public abstract void start();
    public abstract int loop();
    public abstract void exit();
}
