package tomorrow.ntu.edu.sg.hospitalbees;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    void inject(ChooseClinic activity);
    void inject(BookingDetails activity);
    void inject(MyQueue activity);
    void inject(Alerts activity);
}
