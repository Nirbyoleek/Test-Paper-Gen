package lj.srm.qgen.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lj.srm.qgen.adapters.QuestionsAdapter
import lj.srm.qgen.api.BaseQuestionsRepository
import lj.srm.qgen.api.QuestionsAPI
import lj.srm.qgen.api.QuestionsRepository
import lj.srm.qgen.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideQuestionsAPI() : QuestionsAPI {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .baseUrl(BASE_URL)
                .build()
                .create(QuestionsAPI::class.java)
    }
    @Singleton
    @Provides
    fun provideQuestionsRepository(
            api: QuestionsAPI
    ) = QuestionsRepository(api) as BaseQuestionsRepository

    @Singleton
    @Provides
    fun provideQuestionsAdapter()
    = QuestionsAdapter()


    @Singleton
    @Provides
    fun provideGlideInstance(
            @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
    )

}

