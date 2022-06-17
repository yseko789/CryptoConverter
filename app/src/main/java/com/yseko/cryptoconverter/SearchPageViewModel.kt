package com.yseko.cryptoconverter

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yseko.cryptoconverter.network.*
import kotlinx.coroutines.launch

class SearchPageViewModel: ViewModel() {
//    var searchInput by mutableStateOf("")
//    val searchResult = mutableStateListOf<Coin>()
//    fun updateSearch(inputString: String){
//        searchInput = inputString
//        getCoins()
//    }
//    fun getCoins(){
//        viewModelScope.launch {
//            try {
//                searchResult.clear()
//                searchResult.addAll(GeckoApi.retrofitService.getCoins(searchInput).coins.take(20))
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//    }
    private val apikey = ""

//    private var latest: Array<LatestData> = getLatestCrypto()
    private var latest = mutableStateListOf<LatestData>()

    var searchInput by mutableStateOf("")
    val searchResult = mutableStateListOf<LatestData>()

//    private val latestFiltered = mutableListOf<LatestData>()
//    val infos = mutableListOf<InfoData>()

//    fun startSearch(){
//        latest.addAll(getLatestCrypto())
//        println(latest.size)
//        searchResult.addAll(latest)
//        println(searchResult.size)
////        println(searchResult.size)
////        var idSearchString: String = ""
////        searchResult.forEach{
////            idSearchString+= it.id
////            idSearchString+=","
////        }
////        getInfoCrypto(idSearchString)
//    }

    fun updateSearch(inputString: String){
        // -- leads to too many requests -- //
//        latest.clear()
//        latest.addAll(getLatestCrypto())

        searchInput = inputString
        println(latest.size)
        var tempResult = latest.filter{
            it.name.lowercase().startsWith(inputString) || it.symbol.lowercase().startsWith(inputString)
        }
        println(tempResult)
        println(tempResult.size)
        searchResult.clear()
        searchResult.addAll(tempResult)

//        var idSearchString: String = ""
//        searchResult.forEach{
//            idSearchString+=it.id
//            idSearchString+=","
//        }
//        getInfoCrypto(idSearchString)

    }

    fun getLatestCrypto(){
        viewModelScope.launch {
            try{
                latest.addAll(CoinMarketCapApi.retrofitService.getLatest(apikey).data)
                searchResult.addAll(latest)
            }catch(e: Exception){
                println(e)
            }
        }
    }

//    private fun getLatestCrypto(): Array<LatestData>{
//        var tempLatest = emptyArray<LatestData>()
//        viewModelScope.launch {
//            try{
//                tempLatest = CoinMarketCapApi.retrofitService.getLatest(apikey).data
//                searchResult.addAll(tempLatest.take(20))
//            }catch(e: Exception){
//                println(e)
//            }
//        }
//        return tempLatest
//    }


//    private fun getInfoCrypto(ids: String){
//        viewModelScope.launch {
//            try{
//                infos.clear()
//                infos.addAll(CoinMarketCapApi.retrofitService.getInfo(apikey =apikey,id=ids).data.values)
//                println(infos.size)
//            }catch (e: Exception){
//                println(e)
//            }
//        }
//    }


}