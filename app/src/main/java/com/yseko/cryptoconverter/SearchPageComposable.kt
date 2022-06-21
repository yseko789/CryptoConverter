package com.yseko.cryptoconverter

import android.inputmethodservice.Keyboard
import android.media.MediaCodec
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.yseko.cryptoconverter.network.InfoData

import com.yseko.cryptoconverter.network.LatestData
import com.yseko.cryptoconverter.network.Price
import com.yseko.cryptoconverter.network.TotalData


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
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            backgroundColor = Color.Black,
//            textColor = Color.White,
//            placeholderColor = Color.LightGray,
//            leadingIconColor = Color.White,
//            focusedBorderColor = Color.White,
//            unfocusedBorderColor = Color.White
//        ),
        placeholder = {
            Text(stringResource(id = R.string.search_placeholder))
        },
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
    )
}


@Composable
fun CryptoList(
    totalResult: List<TotalData>,
    modifier: Modifier = Modifier
){
    LazyColumn{
        items(totalResult.take(30)){data->
            CryptoItem(
                totalData = data
            )
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )


        }
    }
}

@Composable
fun CryptoItem(
    totalData: TotalData,
    modifier: Modifier = Modifier
){
    var expandedState by remember { mutableStateOf(false)}

    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
                .height(60.dp)
//                .clip(RoundedCornerShape(10.dp))
//            .background(color = Color.Red)

        ) {
            Image(
                painter =
                if(expandedState){
                    painterResource(id = R.drawable.ic_baseline_expand_less_24)
                }else{
                    painterResource(id = R.drawable.ic_baseline_expand_more_24)
                     },
                contentDescription = "expand",
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        expandedState = !expandedState
                    }
            )
            Image(
                painter = rememberAsyncImagePainter(model = totalData.logo),
                contentDescription = "icon",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            )
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 10.dp)
                    .weight(2f)
            )
            {
                Text(
                    text = totalData.name,
//                color= Color.White,
                    modifier = Modifier
                )
                Text(
                    text = totalData.symbol,
//                color= Color.LightGray,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
            }
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(3f)
            ) {
                Text(
                    text = String.format("%.3f", totalData.quote.USD.price),
                    fontSize = 20.sp,
//                color = Color.White,
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
                    .clickable {
                        expandedState = !expandedState
                    }
            )
        }
    }
    if(expandedState){
        Text(
            text = totalData.description,
//            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
//                .border(BorderStroke(1.dp, Color.White))
                .padding(5.dp)
        )
    }
}



@Composable
fun SearchScreen(
    viewModel: SearchPageViewModel = viewModel(),
    modifier: Modifier = Modifier
){

    viewModel.getTotalInfo()
    Column(
        modifier = Modifier
            .fillMaxHeight()
//            .background(color = Color.Black)
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        CryptoSearchBar(
            viewModel.searchInput,
            { input -> viewModel.updateSearch(input) },
            Modifier.padding(bottom = 20.dp)
        )
        CryptoList(
            viewModel.totalResult
        )
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