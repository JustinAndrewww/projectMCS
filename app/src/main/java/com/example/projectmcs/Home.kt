package com.example.projectmcs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Home : Fragment() {
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.layoutManager = gridLayoutManager

        requestQueue = Volley.newRequestQueue(requireContext())

        val url = "https://api.npoint.io/9d7f4f02be5d5631a664"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    val dollList = parseJSON(response)
                    val adapter = DollAdapter(dollList, object: DollAdapter.OnItemClickListener{
                        override fun onItemClick(item: Doll) {
                            val intent = Intent(requireContext(), DollDetail::class.java)
                            intent.putExtra("name", item.dollName)
                            intent.putExtra("desc", item.desc)
                            intent.putExtra("price", item.price)
                            intent.putExtra("rating", item.rating)
                            intent.putExtra("image", item.imageLink)
                            intent.putExtra("size", item.size)

                            startActivity(intent)
                        }
                    })
                    recyclerView.adapter = adapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Log.e("Volley Error", error.toString())
            }
        )

        // Add the request to the RequestQueue
        requestQueue.add(request)
    }

    private fun parseJSON(jsonObject: JSONObject): ArrayList<Doll> {
        val dollList = ArrayList<Doll>()
        try {
            val dollArray = jsonObject.getJSONArray("dolls")
            for (i in 0 until dollArray.length()) {
                val dollObject = dollArray.getJSONObject(i)
                val name = dollObject.getString("name")
                val desc = dollObject.getString("desc")
                val size = dollObject.getString("size")
                val price = dollObject.getString("price")
                val rating = dollObject.getString("rating")
                val image = dollObject.getString("imageLink")  // Changed to getString
                dollList.add(Doll(name,desc, size, price, rating, image))
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return dollList
    }
}
