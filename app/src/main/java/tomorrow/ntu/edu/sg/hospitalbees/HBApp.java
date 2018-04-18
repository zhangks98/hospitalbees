package tomorrow.ntu.edu.sg.hospitalbees;

import android.app.Application;

/**
 * The type Hb app.
 */
public class HBApp extends Application{
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();

    }

    /**
     * Method that get the net component
     *
     * @return the net component
     */
    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
