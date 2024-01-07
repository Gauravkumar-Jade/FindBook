package com.gaurav.findbook.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gaurav.findbook.R
import com.gaurav.findbook.model.ImageLinks
import com.gaurav.findbook.model.Item
import com.gaurav.findbook.ui.BookUiState
import com.gaurav.findbook.ui.theme.FindBookTheme

@Composable
fun HomeScreen(bookUiState: BookUiState, retryAction: () -> Unit, modifier: Modifier = Modifier){

    when(bookUiState){

        is BookUiState.Welcome -> {
            WelcomeScreen()
        }
        is BookUiState.Loading -> {
            LoadingScreen(modifier = modifier.fillMaxSize())
        }
        is BookUiState.Success -> {
            BookGridScreen(item = bookUiState.bookList)
        }
        is BookUiState.Error -> {
            ErrorScreen(code = null, message = bookUiState.message, retryAction =  retryAction )
        }
        is BookUiState.HttpError -> {
            ErrorScreen(code = bookUiState.code.toString(), message = bookUiState.message, retryAction = retryAction )
        }
    }

}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier){

    Box(modifier = modifier
        .fillMaxSize()
        .padding(8.dp),
        contentAlignment = Alignment.Center) {
        Text(
            text = "Welcome!\n Enter Book's Detail In Above Search Field.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
fun ErrorScreen(code: String?,
                message: String?,
                retryAction: () -> Unit,
                modifier: Modifier = Modifier
) {
    val errorMessage = "$code-$message"
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = errorMessage, modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun BookCard(imageLink: ImageLinks?,modifier: Modifier = Modifier){
    Card(modifier = modifier
        .padding(8.dp)
        .fillMaxHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageLink?.smallThumbnail)
                .crossfade(true)
                .build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_broken_image),
            placeholder = painterResource(id = R.drawable.loading_img),
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun BookGridScreen(item:List<Item>,modifier: Modifier = Modifier){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ){
        items(items = item, key = {item -> item.id}){
            val imageLink = it.volumeInfo.imageLinks
            BookCard(imageLink = imageLink)
        }
    }
}



@Preview(showSystemUi = true,
    showBackground = true)
@Composable
fun HomeScreenPreview(){
    FindBookTheme {
        BookCard( imageLink = ImageLinks("",""))
    }
}