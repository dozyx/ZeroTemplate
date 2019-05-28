package com.zerofate.template.justfortest.di;

import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

/**
 * @author dozeboy
 * @date 2019/1/12
 */
@Module
public abstract class NetworkModule {

        @Provides
        static Retrofit provideRetrofit(Gson gson){
            return new Retrofit.Builder().build();
        }

}
