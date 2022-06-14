package com.yseko.cryptoconverter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yseko.cryptoconverter.ui.theme.CryptoConverterTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yseko.cryptoconverter.network.Coin
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.yseko.cryptoconverter.network.Price


@Composable
fun CryptoSearchBar(
    input: String,
    updateInput: (String)->Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = input,
        onValueChange = {updateInput(it)},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.Black,
            textColor = Color.White,
            placeholderColor = Color.LightGray,
            leadingIconColor = Color.White,
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White
        ),
        placeholder = {
            Text(stringResource(id = R.string.search_placeholder))
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)

//            .background(Color.Black)

    )
}

//@Composable
//fun CryptoList(
//    modifier: Modifier = Modifier
//){
//    Column(
//        modifier = modifier
//    ) {
//        for(i in 1..3){
//            CryptoItem()
//        }
//    }
//}
@Composable
fun CryptoList(
    results: List<Coin>,
//    prices: List<Double>,
    modifier: Modifier = Modifier
){
    LazyColumn{
//        items(results){result->
//            CryptoItem(result, prices[results.indexOf(result)])
        items(results){result->
            CryptoItem(result = result)


        }
    }
}

@Composable
fun CryptoItem(
    result: Coin,
//    price: Double,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth()
            .height(60.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = Color.Red)

    ){
        Image(
            //need to use coil to get images from internet
//            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            painter = rememberAsyncImagePainter(model = result.thumb),
            contentDescription = "icon",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
        Column (
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 10.dp)
                .weight(2f)
        )
        {
            Text(
                text = result.name,
                color= Color.White,
                modifier = Modifier
            )
            Text(
                text = result.symbol,
                color= Color.LightGray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
            )
        }
        Box(contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .fillMaxHeight()
                .weight(3f)
        ) {
            Text(
//                text = price.toString(),
                text = result.marketCapRank.toString(),
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier
            )
        }
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_star_outline_24),
            contentDescription = "star",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .weight(1f)
                .padding(10.dp)
        )
    }
}

//add parameters to search bar & cryptolist


@Composable
fun SearchScreen(
    viewModel: SearchPageViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    viewModel.getCoins()
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = Color.Black)
            .padding(20.dp)
    ) {
        CryptoSearchBar(
            viewModel.searchInput,
            { input -> viewModel.updateSearch(input) },
            Modifier.padding(bottom = 20.dp)
        )
        CryptoList(viewModel.searchResult)

    }

}


//@Preview(showBackground = true)
//@Composable
//fun SearchbarPreview() {
//    CryptoConverterTheme {
//        CryptoSearchBar()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun CryptoItemPreview() {
//    CryptoConverterTheme {
//        CryptoItem()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun CryptoListPreview() {
//    CryptoConverterTheme {
//        CryptoList()
//    }
//}
//
@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    CryptoConverterTheme {
        SearchScreen()
    }
}