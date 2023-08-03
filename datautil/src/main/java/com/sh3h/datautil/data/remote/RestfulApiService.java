package com.sh3h.datautil.data.remote;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sh3h.datautil.data.entity.DUCoor;
import com.sh3h.datautil.data.entity.DUMediaResponse;
import com.squareup.otto.Bus;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

//import com.sh3h.datautil.BuildConfig;

public interface RestfulApiService {
    @Multipart
    @POST("fs/upload")
    Call<List<DUMediaResponse>> uploadImage(@Part("description") RequestBody description,
                                            @Part MultipartBody.Part file);

    @Multipart
    @POST("fs/upload")
    Call<List<DUMediaResponse>> uploadImages(@Part("description") RequestBody description,
                                             @Part MultipartBody.Part file1,
                                             @Part MultipartBody.Part file2,
                                             @Part MultipartBody.Part file3,
                                             @Part MultipartBody.Part file4,
                                             @Part MultipartBody.Part file5,
                                             @Part MultipartBody.Part file6);

    @Multipart
    @POST("fs/upload")
    Call<List<DUMediaResponse>> uploadImages(@PartMap Map<String, RequestBody> params);

    @GET("/ServiceEngine/rest/services/SearchServer/transform?")
    Observable<DUCoor> transformCoordinate(@Query("f") String json, @Query("x") double x, @Query("y") double y);

    /********
     * Helper class that sets up a new services
     *******/
    class Factory {
        public static RestfulApiService newInstance(Bus bus, String baseUrl) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS); // HttpLoggingInterceptor.Level.NONE
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new UnauthorisedInterceptor(bus))
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RestfulApiService.class);
        }
    }
}
