package com.example.todolist.presentation.di

import android.content.Context
import androidx.room.Room
import com.example.todolist.data.LocalDataSource
import com.example.todolist.data.TaskDao
import com.example.todolist.data.TasksDatabase
import com.example.todolist.domain.RoomLocalDataSource
import com.example.todolist.domain.TaskRepository
import com.example.todolist.domain.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @javax.inject.Singleton
    fun provideDatabase(@ApplicationContext context: Context): TasksDatabase {
        return Room.databaseBuilder(
            context,
            TasksDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TasksDatabase): TaskDao {
        return database.dao
    }

    @Provides
    fun provideLocalDataSource(dao: TaskDao): LocalDataSource {
        return RoomLocalDataSource(dao)
    }

    @Provides
    fun provideItemRepository(
        localDataSource: LocalDataSource,
    ): TaskRepository {
        return TaskRepositoryImpl(localDataSource)
    }

}