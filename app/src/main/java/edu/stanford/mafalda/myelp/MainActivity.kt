package edu.stanford.mafalda.myelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
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

        val restaurants = mutableListOf<YelpRestaurant>()
        val adapter = RestaurantsAdapter(this, restaurants)
        rvRestaurants.adapter = adapter
        rvRestaurants.layoutManager = LinearLayoutManager(this)

        // 1. Create the retrofit instance
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        // 2. Define the endpoints (they will go inside of an interface called YelpService)
        // 3. start referencing the YelpService
        val yelpService = retrofit.create(YelpService::class.java)
        // Make the yelpService call the function we created "searchRestaurants" with the 3 terms - header, query and query
        yelpService.searchRestaurants("Bearer $API_KEY", "Avocado", "Palo Alto").enqueue(object: Callback<YelpSearchResult> {

            override fun onResponse(call: Call<YelpSearchResult>, response: Response<YelpSearchResult>) {
                Log.i(TAG, "onResponse $response")
                val body = response.body()
                if (body == null) {
                    Log.w(TAG, "Did not receive valid response body from Yelp API... exiting")
                    return
                }
                restaurants.addAll(body.restaurants)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<YelpSearchResult>, t: Throwable) {
                Log.i(TAG, "onFailure $t")
            }

        })
    }
}
