package com.senaaydan.cinebee_.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.domain.model.Movie


@Composable
fun MovieCard(
    movie : Movie,
    MovieClick : (Movie) -> Unit,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier.width(130.dp).height(190.dp).clickable{MovieClick(movie)},
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        //colors = CardColors(containerColor = Color.Yellow, contentColor = Color.Black, disabledContainerColor = Color.Gray, disabledContentColor = Color.Black)

    ) {
              Box(contentAlignment = Alignment.BottomStart){
                  Box(modifier = Modifier.fillMaxSize().background(Color.LightGray))
                  Text(
                      text = movie.title,
                      color = Color.White,
                      style = MaterialTheme.typography.labelMedium,
                      modifier = Modifier.fillMaxSize().background(Color.Black).padding(8.dp)
                  )
              }
        }


}

