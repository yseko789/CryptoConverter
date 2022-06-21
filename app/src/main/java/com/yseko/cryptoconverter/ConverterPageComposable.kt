package com.yseko.cryptoconverter

import android.view.Display
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yseko.cryptoconverter.ui.theme.CryptoConverterTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yseko.cryptoconverter.network.LatestData
import androidx.compose.foundation.lazy.items
import kotlin.math.exp


@Composable
fun ConverterScreen(
    viewModel: ConverterPageViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    viewModel.getLatest()
    Column(
        modifier = modifier
            .fillMaxHeight()
//            .background(Color.Black)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Display(
            viewModel.numInput,
            viewModel.numOutput,
            viewModel.searchInputFrom,
            viewModel.searchInputTo,
            {input->viewModel.updateSearchFromList(input)},
            {input->viewModel.updateSearchToList(input)},
            { latest -> viewModel.selectSearchFromList(latest) },
            {latest ->viewModel.selectSearchToList(latest)},
            viewModel.searchFrom,
            viewModel.searchTo,
            modifier = Modifier
                .weight(7f)
        )
        NumPad(
            onDeleteClick = {viewModel.deleteInput()},
            onNumClick = {num->viewModel.updateInput(num)},
            modifier = Modifier
                .weight(1f)
        )
        Spacer(
            modifier = Modifier
                .weight(1f)
        )

    }
}

@Composable
fun Display(
    inputNum: String,
    outputNum: String,
    inputCryptoFrom: String,
    inputCryptoTo: String,
    updateSearchFromList: (String)->Unit,
    updateSearchToList: (String)->Unit,
    selectSearchFromList: (LatestData)->Unit,
    selectSearchToList:(LatestData)->Unit,
    searchFrom: List<LatestData>,
    searchTo: List<LatestData>,
    modifier: Modifier = Modifier
//        .padding(50.dp)
//        .fillMaxWidth()
){
    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        CryptoPicker(
            label = "From",
            input = inputCryptoFrom,
            updateInput =updateSearchFromList,
            selectInput = selectSearchFromList,
            searchResults = searchFrom,
            modifier = Modifier
                .weight(1f)
        )
        CryptoAmount(
            input = inputNum,
            modifier = Modifier
                .weight(1f)
        )
        CryptoPicker(
            label = "To",
            input = inputCryptoTo,
            updateInput = updateSearchToList,
            selectInput = selectSearchToList,
            searchResults = searchTo,
            modifier = Modifier
                .weight(1f)
        )
        CryptoAmount(
            input = outputNum,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun CryptoAmount(
    input: String,
    modifier: Modifier = Modifier
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(
            text = input,
//            color = Color.White,
            fontSize = 40.sp,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
//                .padding(10.dp)
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CryptoPicker(
    label: String,
    input: String,
    updateInput: (String)->Unit,
    selectInput: (LatestData)->Unit,
    searchResults: List<LatestData>,
    modifier: Modifier = Modifier
){

    var expanded by remember{ mutableStateOf(false)}
//    var selectedOptionText by remember { mutableStateOf("")}

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
//            value = selectedOptionText,
            value = input,
            onValueChange = {
                updateInput(it)
                expanded = true },
            label = {Text(label)},
//            colors = TextFieldDefaults.outlinedTextFieldColors(
//                backgroundColor = Color.Black,
//                textColor = Color.White,
//                placeholderColor = Color.LightGray,
//                focusedBorderColor = Color.White,
//                unfocusedBorderColor = Color.White,
//                focusedLabelColor = Color.White,
//                unfocusedLabelColor = Color.White
//            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false},
            modifier= Modifier
                .fillMaxWidth()
        ) {
            searchResults.take(5).forEach{
                DropdownMenuItem(
                    onClick = {
                        selectInput(it)
                        expanded = false
                    }
                ) {
                    Text(
                        text = it.name,
//                        color = Color.White,
                        modifier = Modifier
//                            .background(Color.Black)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Composable
fun NumPad(
    onNumClick: (String)->Unit,
    onDeleteClick: (String)->Unit,
    modifier: Modifier = Modifier
){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Row(
                modifier = Modifier
            ) {
                NumKey(
                    num = "1",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "2",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "3",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
            ) {
                NumKey(
                    num = "4",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "5",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "6",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
            ) {
                NumKey(
                    num = "7",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "8",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "9",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
            }
            Row(
                modifier = Modifier
            ) {
                NumKey(
                    num = ".",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "0",
                    onClick = onNumClick,
                    modifier
                        .weight(1f)
                )
                NumKey(
                    num = "DLT",
                    onClick = onDeleteClick,
                    modifier
                        .weight(1f)
                )
            }

        }


}

@Composable
fun NumKey(
    num: String,
    onClick: (String)->Unit,
    modifier: Modifier = Modifier
){

    Box(
        contentAlignment= Alignment.Center,
        modifier = modifier
            .aspectRatio(1f)
            .clickable { onClick(num) },
    ) {
        Text(
            text = num,
//            color = colorResource(id = R.color.moss_green),
            fontSize = 20.sp
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun NumKeyPreview() {
//    NumKey(num = "8")
//}
//
//@Preview(showBackground = true)
//@Composable
//fun NumPadPreview() {
//    NumPad()
//}

@Preview(showBackground = true)
@Composable
fun ConverterScreenPreview(){
    ConverterScreen()
}