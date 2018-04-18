package tomorrow.ntu.edu.sg.hospitalbees;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * The class for accessing the internet
 */
@Module
public class NetModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

}
