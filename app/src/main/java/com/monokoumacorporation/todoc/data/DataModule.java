package com.monokoumacorporation.todoc.data;

import android.app.Application;
import android.content.res.Resources;

import com.monokoumacorporation.todoc.data.dao.ProjectDao;
import com.monokoumacorporation.todoc.data.dao.TaskDao;
import com.monokoumacorporation.todoc.data.database.TodocDatabase;
import com.monokoumacorporation.todoc.data.di.IoExecutor;
import com.monokoumacorporation.todoc.data.di.MainExecutor;
import com.monokoumacorporation.todoc.utils.MainThreadExecutor;

import java.time.Clock;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DataModule {

    @Provides
    @Singleton
    public Resources provideResources(Application application) {
        return application.getResources();
    }

    @Provides
    @Singleton
    @MainExecutor
    public Executor provideMainExecutor() {
        return new MainThreadExecutor();
    }

    @Provides
    @Singleton
    @IoExecutor
    public Executor provideIoExecutor() {
        return Executors.newFixedThreadPool(4);
    }

    @Provides
    @Singleton
    public Clock provideClock() {
        return Clock.systemDefaultZone();
    }

    @Provides
    @Singleton
    public TodocDatabase provideTodocDatabase(Application application, @IoExecutor Executor ioExecutor, Provider<ProjectDao> provider) {
        return TodocDatabase.create(application, ioExecutor, provider);
    }

    @Provides
    @Singleton
    public ProjectDao provideProjectDao(TodocDatabase todocDatabase) {
        return todocDatabase.getProjectDao();
    }

    @Provides
    @Singleton
    public TaskDao provideTaskDao(TodocDatabase todocDatabase) {
        return todocDatabase.getTaskDao();
    }
}
