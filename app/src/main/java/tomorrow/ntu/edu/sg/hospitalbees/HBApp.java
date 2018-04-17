package tomorrow.ntu.edu.sg.hospitalbees;

import android.app.Application;

public class HBApp extends Application{
    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule())
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
