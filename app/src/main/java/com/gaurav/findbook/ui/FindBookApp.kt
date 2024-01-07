package com.gaurav.findbook.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gaurav.findbook.R
import com.gaurav.findbook.ui.screens.HomeScreen
import com.gaurav.findbook.ui.viewmodel.BookViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindBookApp(){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val viewModel: BookViewModel = viewModel(factory = BookViewModel.Factory)

    var query by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { FindBookTopAppBar(
            query = query,
            onQueryChange = {query = it},
            keyboardAction = {
                if(query.isEmpty()){
                showToast(context)
                }else{
                    viewModel.findBook(query)
                }
            },
            scrollBehavior = scrollBehavior)}
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            HomeScreen(bookUiState = viewModel.bookUiState, retryAction = { viewModel.findBook(query)})
        }
    }
}

private fun showToast(context: Context) {
    Toast.makeText(context,"Search field is empty!", Toast.LENGTH_SHORT)
        .show()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindBookTopAppBar(
    query: String,
    onQueryChange:(String) -> Unit,
    keyboardAction: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
){
    val focusManager = LocalFocusManager.current
    Column {
        TopAppBar(
            scrollBehavior = scrollBehavior,
            title = {
                Text(text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            modifier = modifier
        )
        OutlinedTextField(
            value = query,
            onValueChange = {onQueryChange(it)},
            label = { Text(text = stringResource(R.string.enter_book)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            leadingIcon = { Icon(imageVector = Icons.Filled.Search, contentDescription = "Search") },
            keyboardActions = KeyboardActions(
                onSearch = {keyboardAction.invoke()
                focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }

}


@Preview(showSystemUi = true)
@Composable
fun FindBookAppPreview(){
    FindBookApp()
}