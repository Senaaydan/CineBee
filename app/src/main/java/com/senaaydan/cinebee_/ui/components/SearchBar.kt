package com.senaaydan.cinebee_.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme
import androidx.compose.material3.Text


@Composable
fun SearchBar(query: String, onQueryChange: (String) -> Unit, placeholder: String)
{
    Row(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary)) {

        IconButton(onClick = { /*TODO*/ })   {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        }


        OutlinedTextField(query, onValueChange = onQueryChange, placeholder = { Text(text = "Film Ara")}, modifier = Modifier.background(color = MaterialTheme.colorScheme.background))

       IconButton(onClick = {onQueryChange("")}, modifier = Modifier.background(color = MaterialTheme.colorScheme.background ).height(55.dp)) {
           Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", modifier = Modifier)
       }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    CineBee_Theme {
        SearchBar(query = "", onQueryChange = {}, placeholder = "Film Ara...")
    }
}

