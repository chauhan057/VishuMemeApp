package com.vishal.vishumemeshareapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    lateinit var imgMemeImage:ImageView
    lateinit var progressBar: ProgressBar


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imgMemeImage =findViewById(R.id.imgMemeImage)
        progressBar= findViewById( R.id.progressBar)
//        progressBar.visibility=View.VISIBLE
        loadMeme()


    }

    private fun loadMeme(){
                                                                                  //    val url = "https://meme-api.com/gimme"
             progressBar.visibility=View.VISIBLE                                                                 //    val url ="https://api.imgflip.com/get_memes" //error link not useful
        val url ="https://meme-api.com/gimme"
                                                                             // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this@MainActivity)

                                                                          // Request a string response from the provided URL.
        val jsonObjectRequest= JsonObjectRequest (

            Request.Method.GET, url,null,
            { response ->
                Log.d("result",response.toString())
                val url =response.getString("url")
                Picasso.get().load(url).into(imgMemeImage);
                progressBar.visibility=View.GONE
                                                                            //                Glide.with(this@MainActivity).load(url).into(imgMemeImage)
            },
            {
                Log.d("error",it.toString())
                Toast.makeText(this@MainActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
                })

                                                                                            // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest)

    }
    fun nextMeme(view: View) {
        this.loadMeme()
    }
    fun shareMeme(view: View) {
        val bitmapDrawable =imgMemeImage.drawable as BitmapDrawable
        val bitmap =bitmapDrawable.bitmap
        val bitmapPath =MediaStore.Images.Media.insertImage(contentResolver,bitmap,"Title",null)

        val bitmapUrl = Uri.parse(bitmapPath)
        val intent =Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM,bitmapUrl)
        startActivity(Intent.createChooser(intent,"Share To:",))

    }


}