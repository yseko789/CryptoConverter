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
    val apikey = "18c453c6-f8d6-40f6-8d61-384c413727fa"

    var numInput by mutableStateOf("0")
    var numOutput by mutableStateOf("0")

    var searchInputFrom by mutableStateOf("")
    var searchInputTo by mutableStateOf("")
    var searchInputFromSymbol by mutableStateOf("")
    var searchInputToSymbol by mutableStateOf("")

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
            getPrice(searchInputFromSymbol, searchInputToSymbol)
        }
        println(searchInputFrom)
        println(searchInputTo)
    }

    fun deleteInput(){
        numInput = if(numInput.length>1) {
            numInput.substring(0, numInput.length - 1)
        }else{
            "0"
        }
        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
            getPrice(searchInputFromSymbol, searchInputToSymbol)
        }else if(numInput.toDouble()== 0.0){
            numOutput = "0"
        }
    }

    fun updateSearchFromList(inputString: String) {
        searchInputFrom = inputString
        searchFrom.clear()
        searchFrom.addAll(
            searchLatest.filter {
                it.name.lowercase().startsWith(inputString)
            }
        )
    }

    fun selectSearchFromList(latest: LatestData){
        searchInputFrom = latest.name
        searchInputFromSymbol = latest.symbol

        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
            getPrice(searchInputFromSymbol, searchInputToSymbol)
        }
    }



    fun updateSearchToList(inputString: String) {
        searchInputTo = inputString
        searchTo.clear()
        searchTo.addAll(
            searchLatest.filter {
                it.name.lowercase().startsWith(inputString)
            }
        )
    }

    fun selectSearchToList(latest:LatestData){
        searchInputTo = latest.name
        searchInputToSymbol = latest.symbol
        if(numInput.toDouble() >0 && searchInputFrom.trim() != "" && searchInputTo.trim()!=""){
            getPrice(searchInputFromSymbol, searchInputToSymbol)
        }
    }

    fun getLatest(){
        viewModelScope.launch {
            try{
                searchLatest.clear()
                searchLatest.addAll(CoinMarketCapApi.retrofitService.getLatest(apikey).data)
                searchTo.addAll(searchLatest)
                searchFrom.addAll(searchLatest)
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