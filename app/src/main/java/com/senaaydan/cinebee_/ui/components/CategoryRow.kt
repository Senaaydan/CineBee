package com.senaaydan.cinebee_.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.domain.model.Category
import com.senaaydan.cinebee_.domain.model.Movie


 @Composable
 fun CategoryRow(
     category: Category,
     MovieClick : (Movie) -> Unit
 ){
  Column(modifier = Modifier.fillMaxSize().padding(vertical = 8.dp))
  { Text(text = category.title,
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier.padding(horizontal = 16.dp , vertical = 8.dp)
  )
  LazyRow(
      contentPadding = PaddingValues(horizontal = 16.dp),
      horizontalArrangement = Arrangement.spacedBy(12.dp)
  ) {
      items(category.movies){ movie ->
          MovieCard(
              movie = movie,
              MovieClick = MovieClick
          )
      }

  }

  }


 }