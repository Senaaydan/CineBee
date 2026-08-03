package com.senaaydan.cinebee_.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.senaaydan.cinebee_.data.local.DummyData
import com.senaaydan.cinebee_.domain.model.Movie
import com.senaaydan.cinebee_.ui.theme.CineBee_Theme


@Composable
fun FavoriteMovieCard(movie: Movie,
                      onMovieClick: (Movie) -> Unit,
                      onRemoveClick: (Movie) -> Unit)
{


    Card(modifier = Modifier.fillMaxWidth().height(150.dp).clickable { onMovieClick(movie) }){


     Row(modifier= Modifier.fillMaxWidth().background(color = MaterialTheme.colorScheme.background)) {

         Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.primary).width(120.dp).fillMaxHeight()) {

            // film afişi buraya
         }

         Column(modifier = Modifier.fillMaxSize().weight(1f).padding(10.dp,0.dp)) {
              // film bilgileri buraya
             Spacer(modifier = Modifier.size(5.dp))
             Row(modifier = Modifier.align(Alignment.Start)) {
                 Column() { Text(
                     text = movie.genre,
                     style = MaterialTheme.typography.labelSmall,
                     color = Color.LightGray


                     
                 )

                     Text(text = movie.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold) }

                 Spacer(modifier = Modifier.weight(1f))

                 IconButton(onClick = { onRemoveClick(movie) }) {

                     Icon(
                         imageVector = Icons.Default.Delete,
                         contentDescription = "Delete",
                         tint = Color.White,
                         modifier = Modifier.size(25.dp)

                     )
                 }
             }


               Spacer(modifier = Modifier.size(40.dp))
             Row() {
                 Icon(imageVector = Icons.Default.Star, contentDescription = "IMDdb", tint = Color.Yellow)
                 Spacer(modifier = Modifier.size(10.dp))
                 Text(text = "${movie.imdb}")

             }
             Spacer(modifier = Modifier.size(5.dp))
             Text(text = "${movie.duration} • ${movie.year}")

         }


        }


 }

}




@Preview(showBackground = true)
@Composable
fun FavoriteMovieCardPreview() {
    CineBee_Theme {
        FavoriteMovieCard(
            movie = DummyData.categories[0].movies[0],
            onMovieClick = {},
            onRemoveClick = {}
        )
    }
}
