package com.example.vishistvarugeese.dietassistant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Vishist Varugeese on 18-03-2018.
 */

public interface HerokuService {
    @GET("hello")
    Call<ResponseBody> hello();
}
