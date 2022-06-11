package com.yseko.cryptoconverter

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.yseko.cryptoconverter.network.Coin
import androidx.lifecycle.viewModelScope
import com.yseko.cryptoconverter.network.GeckoApi
import com.yseko.cryptoconverter.network.GeckoApi.retrofitService
import com.yseko.cryptoconverter.network.Price
import kotlinx.coroutines.launch

class SearchPageViewModel: ViewModel() {
    var searchInput by mutableStateOf("")
//    private val _searchResult = getCoins().toMutableStateList()
//    val searchResult: List<Coin> get() = _searchResult

    val searchResult = mutableStateListOf<Coin>()
    val searchPrices = mutableStateListOf<Price>()

    fun updateSearch(inputString: String){
        searchInput = inputString
        println(searchInput)
        getCoins()
        var priceSearch = ""
        for(i in searchResult){
            priceSearch+=i.id
            priceSearch+="%2C"
        }
        println(priceSearch)
        getCoinPrice(priceSearch)
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
//        var results: List<Coin> = emptyList()
        viewModelScope.launch {
            try {
                searchResult.clear()
                searchResult.addAll(GeckoApi.retrofitService.getCoins(searchInput).coins.take(20))
            }catch (e: Exception){
                println(e)
            }
        }
    }

    fun getCoinPrice(id: String){
        viewModelScope.launch {
            try{
                searchPrices.clear()
                searchPrices.addAll(GeckoApi.retrofitService.getPrice(id, "usd").prices.values)
            }catch (e: Exception){
                println(e)
            }
        }
    }


}