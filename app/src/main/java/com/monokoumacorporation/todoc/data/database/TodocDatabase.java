package com.monokoumacorporation.todoc.data.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.entity.ProjectEntity;
import com.monokoumacorporation.todoc.data.entity.TaskEntity;

import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.internal.managers.ApplicationComponentManager;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
@Database(entities = {TaskEntity.class, ProjectEntity.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    @Binds
    public abstract TaskDao getTaskDao();
    @Binds
    public abstract ProjectDao getProjectDao();

    private static volatile TodocDatabase INSTANCE;

    private final static String DATABASE_NAME = "bla";

    public static TodocDatabase getDatabase(@NonNull Context context, @NonNull Executor ioExecutor) {
        if (INSTANCE == null) {
            synchronized (TodocDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = create(context, ioExecutor);
                }
            }
        }
        return INSTANCE;
    }

    private static TodocDatabase create(
        @NonNull Context context,
        @NonNull Executor ioExecutor
    ) {
        Builder<TodocDatabase> builder = Room.databaseBuilder(
            context.getApplicationContext(),
            TodocDatabase.class,
            DATABASE_NAME
        );

        builder.addCallback(new Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                ioExecutor.execute(() -> {
                    ProjectDao projectDao = TodocDatabase.getDatabase(context, ioExecutor).getProjectDao();
                    Log.i("Monokouma", "HERE");
                    projectDao.insert(
                        new ProjectEntity(
                            context.getResources().getString(R.string.projet_tartampion),
                            ResourcesCompat.getColor(context.getResources(), R.color.dogwood_rose, null)
                        )
                    );

                    projectDao.insert(
                        new ProjectEntity(
                            context.getResources().getString(R.string.projet_lucidia),
                            ResourcesCompat.getColor(context.getResources(), R.color.green_munsell, null)
                        )
                    );

                    projectDao.insert(
                        new ProjectEntity(
                            context.getResources().getString(R.string.projet_circus),
                            ResourcesCompat.getColor(context.getResources(), R.color.marigold, null)
                        )
                    );
                });
            }
        });

        return builder.build();
    }


}
