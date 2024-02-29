package com.example.deloitte_android_test.di.module

import android.content.Context
import androidx.room.Room
import com.example.deloitte_android_test.data.database.ItemRoomDatabase
import com.example.deloitte_android_test.data.database.dao.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideItemDao(itemRoomDatabase: ItemRoomDatabase): ItemDao {
        return itemRoomDatabase.itemDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): ItemRoomDatabase {
        return Room.databaseBuilder(
            appContext,
            ItemRoomDatabase::class.java,
            "item_database",
        ).build()
    }
}
