package com.yseko.cryptoconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yseko.cryptoconverter.network.CoinMarketCapApi
import com.yseko.cryptoconverter.network.LatestData
import kotlinx.coroutines.launch

class ConverterPageViewModel: ViewModel() {
    val apikey = ""

    var numInput by mutableStateOf("0")
    var numOutput by mutableStateOf("0")

    var searchInputFrom by mutableStateOf("")
    var searchInputTo by mutableStateOf("")

    var searchLatest = mutableStateListOf<LatestData>()
    var searchFrom = mutableStateListOf<LatestData>()
    var searchTo = mutableStateListOf<LatestData>()

    fun updateInput(input: String){
        if(numInput == "0"){
            if(input == "."){
                numInput +="."
            }else {
                numInput = input
            }
        }else {
            numInput += input
        }
        println(numInput)
        println(searchInputFrom)
        println(searchInputTo)
        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
            getPrice(searchInputFrom, searchInputTo)
        }
        println("here")
    }

    fun deleteInput(){
        numInput = if(numInput.length>1) {
            numInput.substring(0, numInput.length - 1)
        }else{
            "0"
        }
        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
            getPrice(searchInputFrom, searchInputTo)
        }else if(numInput.toDouble()== 0.0){
            numOutput = "0"
        }
    }

    fun updateSearchFrom(inputString: String){
        searchFrom.clear()
        searchFrom.addAll(
            searchLatest.filter{
                it.name.lowercase().startsWith(inputString)
            }
        )


//        searchInputFrom = inputString
//        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
//            getPrice(searchInputFrom, searchInputTo)
//        }
//        getCoins(inputString)
    }



    fun updateSearchTo(inputString: String){
        searchTo.clear()
        searchTo.addAll(
            searchLatest.filter{
                it.name.lowercase().startsWith(inputString)
            }
        )



//        searchInputTo = inputString
//        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
//            getPrice(searchInputFrom, searchInputTo)
//        }
//        getCoins(inputString)
    }

    fun getLatest(){
        viewModelScope.launch {
            try{
                searchLatest.clear()
                searchLatest.addAll(CoinMarketCapApi.retrofitService.getLatest(apikey).data)
            }catch (e:Exception){
                println(e)
            }
        }
    }
//
//    fun getConvertedPrice(id: String, vs: String){
//        viewModelScope.launch {
//            try{
//                numOutput = GeckoApi.retrofitService.getPrice(id, vs)[id]?.get(vs)?.toString()!!
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//    }

//    fun getPrice(id: String, convertId: String){
//        viewModelScope.launch {
//            try{
//                numOutput = CoinMarketCapApi.retrofitService.getPrice(apikey, id, convertId).data["1"]?.quote?.USD?.price.toString()
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//    }
private fun getPrice(symbol: String, convert: String){
        viewModelScope.launch {
            try{
//                numOutput = CoinMarketCapApi.retrofitService.getPrice(apikey, numInput.toDouble(), symbol, convert).data[symbol]?.get(0)?.quote?.get(convert)?.price.toString()
                numOutput = String.format("%.4f",CoinMarketCapApi.retrofitService.getPrice(apikey, numInput.toDouble(), symbol, convert).data[0].quote[convert]?.price)
                println(numOutput)
            }catch (e: Exception){
                println(e)
            }
        }
    }



}