package com.yseko.cryptoconverter.network

class ResponseLatest(
    val data: Array<LatestData>
)

class LatestData (
    val id: Int,
    val name: String,
    val symbol: String,
    val quote: LatestQuote
)

class LatestQuote(
    val USD: LatestUsd
)

class LatestUsd(
    val price: Double
)

class ResponseInfo(
    val data: Map<Int, InfoData>
)

class InfoData(
    val logo: String,
    val id: Int,
    val description: String,
)

class TotalData(
    val id: Int,
    val name: String,
    val symbol: String,
    val quote: LatestQuote,
    val logo: String,
    val description: String
)





class ResponseConversion(
    val data: Array<PriceData>
)

class PriceData(
    val quote: Map<String, Price>
)

class Price(
    val price: Double
)
