package com.flowz.mvvmcurdapp

import android.util.Patterns
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flowz.mvvmcurdapp.db.Subscriber
import com.flowz.mvvmcurdapp.db.SubscriberRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel(),Observable {


    val subscribers = repository.subscribers
    private var isUpdateOrDelete = false
    private lateinit var subscriberToUpdateOrDelete : Subscriber

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputEmail = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButton = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>> get() = statusMessage



    init {
        saveOrUpdateButtonText.value = "SAVE"
        clearAllOrDeleteButton.value = "CLEAR All"
    }

    fun saveOrUpdate(){

        if(inputName.value==null){
            statusMessage.value = Event("Enter Subscriber'name")

        }else if(inputEmail.value==null){
            statusMessage.value = Event("Enter Subscriber'email")

        }else if (!Patterns.EMAIL_ADDRESS.matcher(inputEmail.value!!).matches()){
            statusMessage.value = Event("Enter a Correct Email Address")

        }else{
            if (isUpdateOrDelete){

                subscriberToUpdateOrDelete.name = inputName.value!!
                subscriberToUpdateOrDelete.email = inputEmail.value!!

                update(subscriberToUpdateOrDelete)
            } else {

                val name: String = inputName.value!!
                val email: String = inputEmail.value!!

                insert(Subscriber(0, name, email))

                inputName.value = null
                inputEmail.value = null}
        }
    }

    fun clearAllOrDelete(){

        if (isUpdateOrDelete){

            delete(subscriberToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun insert(subscriber: Subscriber): Job = viewModelScope.launch {

       val newRowId:Long = repository.insert(subscriber)

        if (newRowId>-1){
            statusMessage.value = Event("Subscriber Inserted Successfully $newRowId")
        }else{
            statusMessage.value = Event("Error Occurred")
        }

    }

    fun update(subscriber: Subscriber): Job = viewModelScope.launch {

        val noOfRows : Int = repository.update(subscriber)
        if (noOfRows>0){
            inputName.value = null
            inputEmail.value = null

            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "SAVE"
            clearAllOrDeleteButton.value = "CLEAR ALL"

            statusMessage.value = Event("$noOfRows Row Updated Successfully")
        }else{

            statusMessage.value = Event("Error Occurred")

        }


    }

    fun delete(subscriber: Subscriber): Job = viewModelScope.launch {
       val noOfRowsDeleted: Int = repository.delete(subscriber)

        if (noOfRowsDeleted>0){

            inputName.value = null
            inputEmail.value = null

            isUpdateOrDelete = false

            saveOrUpdateButtonText.value = "SAVE"
            clearAllOrDeleteButton.value = "CLEAR ALL"

            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        }else{
            statusMessage.value = Event("Error Occurred")
        }


    }

    fun clearAll(): Job = viewModelScope.launch {
        val noOfRowsDeleted: Int = repository.deleteAll()

        if(noOfRowsDeleted>0) {
            statusMessage.value = Event("$noOfRowsDeleted Subscribers Deleted Successfully")
        }else{
            statusMessage.value = Event("Error Ocurred")
        }
    }

    fun initUpdateAndDelete(subscriber: Subscriber){
        inputName.value = subscriber.name
        inputEmail.value = subscriber.email

        isUpdateOrDelete = true
        subscriberToUpdateOrDelete = subscriber

        saveOrUpdateButtonText.value = "UPDATE"
        clearAllOrDeleteButton.value = "DELETE"

    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }



}