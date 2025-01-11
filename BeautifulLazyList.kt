import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.pow

@Composable
private fun BeautifulLazyList() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        val density = LocalDensity.current
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState
        ) {
            items(
                items = List(100) { it },
                key = { it }
            ) { ind ->
                val layoutInfo by remember { derivedStateOf { listState.layoutInfo } }
                val info = layoutInfo.visibleItemsInfo.find { it.index == ind }
                val viewportCenter = with(density) {
                    (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset).toDp() / 2
                }
                val multipler = info?.let {with(density) { 1 - (viewportCenter - (info.offset + info.size / 2).coerceIn(layoutInfo.viewportStartOffset, layoutInfo.viewportEndOffset).toDp()).value.absoluteValue / viewportCenter.value } }
                val minHeight = 64.dp
                val minAlpha = 0.5f
                val height = minHeight + 256.dp * (multipler?.pow(1.1f) ?: 0f)
                val alpha = multipler?.coerceAtLeast(minAlpha) ?: minAlpha

                Box(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth()
                        .height(height)
                        .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                        .alpha(alpha),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Item $ind")
                }
            }
        }
    }
}
