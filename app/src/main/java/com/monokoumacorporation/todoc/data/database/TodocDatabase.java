package com.monokoumacorporation.todoc.data.database;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.monokoumacorporation.todoc.R;
import com.monokoumacorporation.todoc.data.dao.ProjectDAO;
import com.monokoumacorporation.todoc.data.dao.TaskDAO;
import com.monokoumacorporation.todoc.data.model.Project;
import com.monokoumacorporation.todoc.data.model.Task;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Task.class, Project.class}, version = 1, exportSchema = false)
public abstract class TodocDatabase extends RoomDatabase {

    public abstract TaskDAO getTaskDAO();

    public abstract ProjectDAO getProjectDAO();

    private static volatile TodocDatabase INSTANCE;

    private final static String DATABASE_NAME = "todoc_database6";

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
                    ProjectDAO projectDAO = TodocDatabase.getDatabase(context, ioExecutor).getProjectDAO();


                    projectDAO.insert(
                        new Project(
                            1,
                            context.getResources().getString(R.string.projet_tartampion),
                            ResourcesCompat.getColor(context.getResources(), R.color.dogwood_rose, null)
                        )
                    );


                    projectDAO.insert(
                        new Project(
                            2,
                            context.getResources().getString(R.string.projet_lucidia),
                            ResourcesCompat.getColor(context.getResources(), R.color.green_munsell, null)
                        )
                    );

                    projectDAO.insert(
                        new Project(
                            3,
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
