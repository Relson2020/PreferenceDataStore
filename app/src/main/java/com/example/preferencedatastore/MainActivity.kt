package com.example.preferencedatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import com.example.preferencedatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private lateinit var dataStore : DataStore<androidx.datastore.preferences.core.Preferences>

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        dataStore = createDataStore(name = "dataStoreName")

        binding.saveKeyButton.setOnClickListener{
            lifecycleScope.launch {
                save(binding.saveKeyEditText.text.toString() , binding.saveValueEditText.text.toString())
            }
        }

        binding.readKeyButton.setOnClickListener{
            lifecycleScope.launch {
                val value = read(binding.readKeyEditText.text.toString())
                binding.displayValue.setText(value)
            }
        }
    }

}
suspend fun save( key:String , value:String){
    val dataStoreKey = preferencesKey<String>(key)
    dataStore.edit { dataStore ->
        dataStore[dataStoreKey] = value
    }
}

suspend fun read( key:String ): String? {
    val dataStoreKey = preferencesKey<String>(key)
    val dataPreference = dataStore.data.first()
    return dataPreference[dataStoreKey]
}