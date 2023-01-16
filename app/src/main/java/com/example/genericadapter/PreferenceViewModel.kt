package com.example.genericadapter

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.genericadapter.model.Category
import com.example.genericadapter.model.Language
import com.example.genericadapter.model.State
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PreferenceViewModel(
    @NonNull  application:Application
) : AndroidViewModel(application){

    lateinit var preferenceExceptionHandler: CoroutineExceptionHandler
    var context:Context =application.applicationContext

    //UI event for Language Preference
    private val languagePrefUIEventChannel = Channel<UIEvent>(Channel.UNLIMITED)
    val languagePrefeneceEvents = languagePrefUIEventChannel.receiveAsFlow()
    //UI event for State Preference
    private val staePrefUIEventChannel = Channel<UIEvent>(Channel.UNLIMITED)
    val statePrefeneceEvents = staePrefUIEventChannel.receiveAsFlow()

    //UI event for Category Perefence
    private val categoryPrefUIEventChannel = Channel<UIEvent>(Channel.UNLIMITED)
    val categoryPrefeneceEvents = categoryPrefUIEventChannel.receiveAsFlow()


    //Language Preference Success Live data
    private var _languagePreferenceList = MutableLiveData(emptyList<Language>())
    val languagePreferenceList: LiveData<List<Language>> get() = _languagePreferenceList


    // State Preference Success Live data
    private var _statePreferenceList = MutableLiveData(emptyList<State>())
    val statePreferenceList: LiveData<List<State>> get() = _statePreferenceList

    // Category Preference Success Live data
    private var _categoryPreferenceList = MutableLiveData(emptyList<Category>())
    val categoryPreferenceList: LiveData<List<Category>> get() = _categoryPreferenceList


init {
    inilizeExceptionHandlers()
}

    fun callPreferenceApi() {
        viewModelScope.launch(preferenceExceptionHandler) {
            sendLoadingStateToUIs(UIEvent.onLoading,UIEvent.onLoading,UIEvent.onLoading)
           var webservice=RetrofitFactory.getInstance(context)


                var response = webservice.API_USER_LOGIN_AUTHENTICATION("shreya.tandon12@gmail.com", "12345678", "0", "", "", "1", "28.6289812%2C77.376912%2C10.0.2.15%2C1%2C1.1.9%2CGoogle%2C%2C33%2C1673372548769", "a0fcf9a1a5d76add13", "edddsaLw60QWi4npCI4OhLcx%3AAPA91bGMunZeWm3kr7I5R_FFTvZ55-mJsssmBWWeAkoWBwb1aiCaeZHOkYy_jFeTR0SycDaYqClICqyps2DWlFaWJgKqIMi99QbHaXKzeqflX8kH_tAaSkSUon04qsc97LrswCkmB6xL")
            BaseApiClient.processResponse(response) { status, message, responseModel ->
                when(status){
                    true ->{
                        responseModel?.data?.let {datalist->
                            if(datalist.size>1){

                            }else{
                                //
                                /*_categoryPreferenceList.value=getCate()
                                _statePreferenceList.value=getState()
                                _languagePreferenceList.value=getLang()*/
                                sendSameEvents(UIEvent.OnError(responseModel.message!!))
                            }
                        }?: kotlin.run {
                            sendSameEvents(UIEvent.OnError("Data is not found"))
                        }
                    }
                    false->{
                        sendSameEvents(UIEvent.OnError(message))
                    }
                }

            }

        }

    }


    private fun getLang()=listOf(
        Language("English"),
        Language("Marathi"),
        Language("Bhojpuri"),
        Language("Malyalam"),
        Language("Tamil"),
        Language("English"),
        Language("Marathi"),
        Language("Bhojpuri"),
        Language("Malyalam"),
        Language("Tamil"),Language(),
        Language("English"),
        Language("Marathi"),
        Language("Bhojpuri"),
        Language("Malyalam"),
        Language("Tamil"),Language(),
        Language("English"),
        Language("Marathi"),
        Language("Bhojpuri"),
        Language("Malyalam"),
        Language("Tamil"),
    ).toMutableList()
    private fun getState()= listOf<State>(
        State("ABC"),
        State("ABC"),
        State("ABC"),
        State("ABC"),
        State("ABC"),
        State("ABC"),
        State("ABC")
    ).toMutableList()
    private fun getCate()=listOf<Category>(
        Category("स्पोर्टस"),
        Category("बिजनेस"),
        Category("हेल्थ"),
        Category("लाइफस्टाइल"),
        Category("ऑटो"),
        Category("मनोरंजन"),
        Category("टेक"),
        Category("ओपिनियन"),
        Category("दुनिया"),
        Category("राज्य"),

        ).toMutableList()



     fun inilizeExceptionHandlers(){
        preferenceExceptionHandler = CoroutineExceptionHandler { _, exception ->
            sendSameEvents(UIEvent.OnError(exception.message!!))
        }
    }

    sealed class UIEvent {
        class OnError(val message:String): UIEvent()
        class Success(val message:String): UIEvent()
        object onLoading: UIEvent()
        object NONE: UIEvent()
    }

    fun sendSameEvents(event:UIEvent){
        sendLoadingStateToUIs(event,event,event)
    }

    fun sendLoadingStateToUIs(
        languageEvent: UIEvent = UIEvent.NONE,
        stateEvent: UIEvent = UIEvent.NONE,
        category: UIEvent = UIEvent.NONE
    ) {
        viewModelScope.launch {
            languagePrefUIEventChannel.send(languageEvent)
            staePrefUIEventChannel.send(stateEvent)
            categoryPrefUIEventChannel.send(category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        // save user preference in local.
    }



}