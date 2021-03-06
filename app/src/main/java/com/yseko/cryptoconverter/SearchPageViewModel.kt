package com.yseko.cryptoconverter

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yseko.cryptoconverter.data.CryptoDao
import com.yseko.cryptoconverter.network.*
import kotlinx.coroutines.launch

class SearchPageViewModel(): ViewModel() {

    private val apikey = ""
    var searchInput by mutableStateOf("")
    val totalResult = mutableStateListOf<TotalData>()

//    val allTimers: LiveData<List<Int>> = cryptoDao.getAllCryptoId().asLiveData()

    fun updateSearch(inputString: String) {
        searchInput = inputString
        var tempResult = totalResult.filter {
            it.name.lowercase().startsWith(inputString) || it.symbol.lowercase()
                .startsWith(inputString)
        }
        println(tempResult)
        println(tempResult.size)
        totalResult.clear()
        totalResult.addAll(tempResult)
    }

    fun getTotalInfo(){
        viewModelScope.launch {
            var tempLatest = CoinMarketCapApi.retrofitService.getLatest(apikey).data
            var searchString = ""
            tempLatest.forEach {
                searchString+=it.id
                if(it!=tempLatest.last()) {
                    searchString += ","
                }
            }
            var tempInfo = CoinMarketCapApi.retrofitService.getInfo(apikey, searchString).data
            tempLatest.forEach {
                if(tempInfo.containsKey(it.id)){
                    var temp = tempInfo[it.id]
                    if (temp != null) {
                        totalResult.add(TotalData(it.id, it.name, it.symbol, it.quote, temp.logo, temp.description))
                    }
                }
            }
//            totalResult.sortBy{allTimers.value.contains(it.id)}
        }
    }

//    fun addFavorite(id: Int){
//        viewModelScope.launch {
//            cryptoDao.insert(id)
//        }
//    }
}