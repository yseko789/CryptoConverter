package com.yseko.cryptoconverter

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yseko.cryptoconverter.network.*
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class SearchPageViewModel: ViewModel() {

    private val apikey = ""
    var searchInput by mutableStateOf("")
    val totalResult = mutableStateListOf<TotalData>()

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
        }
    }
}