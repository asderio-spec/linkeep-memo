package com.linkeep.memo.di

import android.content.Context
import androidx.room.Room
import com.linkeep.memo.data.api.OpenAIService
import com.linkeep.memo.data.db.MemoDatabase
import com.linkeep.memo.data.db.MemoDao
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideMemoDatabase(
        @ApplicationContext context: Context
    ): MemoDatabase {
        val migration1to2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE memos ADD COLUMN thumbnailUrl TEXT")
            }
        }
        return Room.databaseBuilder(
            context,
            MemoDatabase::class.java,
            "memo_database"
        ).addMigrations(migration1to2).build()
    }

    @Provides
    @Singleton
    fun provideMemoDao(database: MemoDatabase): MemoDao {
        return database.memoDao()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideOpenAIService(okHttpClient: OkHttpClient): OpenAIService {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIService::class.java)
    }
} 