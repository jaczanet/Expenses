package net.jacza.expenses;

import android.app.Application;
import android.content.Context;

/**
 * Class used to access application context.
 */
public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
