package com.yseko.cryptoconverter

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.yseko.cryptoconverter.network.Coin
import androidx.lifecycle.viewModelScope
import com.yseko.cryptoconverter.network.GeckoApi
import com.yseko.cryptoconverter.network.GeckoApi.retrofitService
import com.yseko.cryptoconverter.network.Price
import kotlinx.coroutines.launch

class SearchPageViewModel: ViewModel() {
    var searchInput by mutableStateOf("")
//    private val _searchPrices = MutableList(15){0.0}.toMutableStateList()
//    val searchPrices: SnapshotStateList<Double> get() = _searchPrices

    val searchResult = mutableStateListOf<Coin>()
//    val searchPrices = mutableStateListOf<Double>()



    fun updateSearch(inputString: String){
        searchInput = inputString
        getCoins()
    }

//    fun getCoins(): List<Coin>{
//        var results: List<Coin> = emptyList()
//        viewModelScope.launch {
//            try {
//                results = GeckoApi.retrofitService.getCoins(searchInput).coins.take(20)
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//        return results
//
//    }

    fun getCoins(){
        viewModelScope.launch {
            try {
                searchResult.clear()
                searchResult.addAll(GeckoApi.retrofitService.getCoins(searchInput).coins.take(20))
            }catch (e: Exception){
                println(e)
            }
        }
    }
//
//    fun getCoinPrice(id: String){
//        viewModelScope.launch {
//            try{
//                searchPrices.clear()
//                searchPrices.addAll(GeckoApi.retrofitService.getPrice(id, "usd").prices.values)
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//    }

//    fun getInfo(inputString: String){
//        searchInput = inputString
//        viewModelScope.launch {
//            try{
//                searchResult.clear()
//                searchResult.addAll(GeckoApi.retrofitService.getCoins(searchInput).coins.take(15))
//                println(searchResult.size)
//                var priceSearch = ""
//                for(i in searchResult.indices){
//                    priceSearch += searchResult[i].id
//                    priceSearch +=","
//                }
//                println(priceSearch)
////                searchPrices.clear()
//                searchPrices.addAll(GeckoApi.retrofitService.getPrice(priceSearch, "usd").values.map{it.usd})
////                for(i in searchPrices.size..15){
////                    searchPrices.add(0.0)
////                }
////                println(searchPrices.size)
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//    }




}