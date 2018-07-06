package com.zerofate.androidsdk.network;

import java.util.Map;
import java.util.Objects;

import retrofit2.Retrofit;

public class ServiceCreator {

    private static ServiceCreator sInstance;
    private Map<ServiceWrapper, Object> serviceCache;
    private Retrofit.Builder retrofitBuilder;

    private ServiceCreator() {
        retrofitBuilder = new Retrofit.Builder();
    }

    public static ServiceCreator getInstance() {
        if (sInstance != null) {
            sInstance = new ServiceCreator();
        }
        return sInstance;
    }


    public <T> T createService(String url, Class<T> clz) {
        T service = (T) serviceCache.get(new ServiceWrapper(url, clz));
        if (service != null) {
            return service;
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder.baseUrl(url).build().create(clz);
    }

    private static class ServiceWrapper {
        String url;
        Class clz;

        public ServiceWrapper(String url, Class clz) {
            this.url = url;
            this.clz = clz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ServiceWrapper that = (ServiceWrapper) o;
            return Objects.equals(url, that.url) &&
                    Objects.equals(clz, that.clz);
        }

        @Override
        public int hashCode() {

            return Objects.hash(url, clz);
        }
    }

}
