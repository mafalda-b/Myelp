package edu.stanford.mafalda.myelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "MainActivity"
private const val BASE_URL = "https://api.yelp.com/v3/"
private const val API_KEY = "JpoR4n70HeDQ7SLiHJrpN9EnI1Afw9iQgqJL0fe8fbj0U2aONfKjauE-PrHCQkSuaxNcWUKMfIo_xIfA8CODngfnnv6jIn-83QP6uEWWOdr_ANamkpQMyQXE5ZXJXnYx"
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Create the retrofit instance
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        // 2. Define the endpoints (they will go inside of an interface called YelpService)
        // 3. start referencing the YeloService
        val yelpService = retrofit.create(YelpService::class.java)
        // Make the yelpService call the function we created "searchRestaurants" with the 3 terms - header, query and query
        yelpService.searchRestaurants("Bearer $API_KEY","AvocadoToast", "New York").enqueue(object: Callback<Any> {
            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG, "onResponse $response")
            }

        })
        //

    }
}
