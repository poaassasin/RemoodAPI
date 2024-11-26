package za.mobile.remoodver2;

import androidx.room.Room;
import android.content.Context;

import za.mobile.remoodver2.AppDatabase;

public class DatabaseClient {

    private Context context;
    private static DatabaseClient instance;

    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        // Membuat instance database
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "remood-db")
                .allowMainThreadQueries() // Jangan gunakan di produksi
                .build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
