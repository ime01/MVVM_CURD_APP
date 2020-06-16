package com.flowz.mvvmcurdapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.mvvmcurdapp.databinding.ActivityMainBinding
import com.flowz.mvvmcurdapp.db.SubcriberDatabase
import com.flowz.mvvmcurdapp.db.Subscriber
import com.flowz.mvvmcurdapp.db.SubscriberDAO
import com.flowz.mvvmcurdapp.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var subscriberViewModel: SubscriberViewModel

    private lateinit var adapter: MyRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val dao: SubscriberDAO = SubcriberDatabase.getInstance(application).subcriberDAO

        val repository = SubscriberRepository(dao)

        val factory = SubscriberViewModelFactory(repository)

        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)

        binding.myViewModel = subscriberViewModel

        binding.lifecycleOwner = this


        initRecyclerView()

        subscriberViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun displaySubscribersList(){

        subscriberViewModel.subscribers.observe(this, Observer {

            Log.i("MYTAG", it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()


        })
    }

    private fun initRecyclerView(){

        binding.subscriberRecylerView.layoutManager =LinearLayoutManager(this)

        adapter = MyRecyclerViewAdapter( {selectedItem:Subscriber->listItemCliked(selectedItem)})


        binding.subscriberRecylerView.adapter = adapter

        displaySubscribersList()
    }

    private fun listItemCliked(subscriber: Subscriber){
//        Toast.makeText(this,"selected name is ${subscriber.name}", Toast.LENGTH_LONG).show()
        subscriberViewModel.initUpdateAndDelete(subscriber)
    }


}
