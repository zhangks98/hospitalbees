package tomorrow.ntu.edu.sg.hospitalbees;

import javax.inject.Singleton;

import dagger.Component;
import tomorrow.ntu.edu.sg.hospitalbees.utilities.MyFirebaseInstanceIDService;
import tomorrow.ntu.edu.sg.hospitalbees.utilities.MyFirebaseMessagingService;

@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void inject(ChooseClinic activity);
    void inject(BookingDetails activity);
    void inject(MyQueue activity);
    void inject(Alerts activity);
    void inject(BookingHistory activity);
    void inject(MyFirebaseInstanceIDService service);
    void inject(MyFirebaseMessagingService service);
}
