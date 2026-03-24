package com.orka.myfinances.lib.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.ui.viewmodel.extensions.isInitial

@Composable
fun <T> LazyVerticalGridContentWithStickyHeader(
    modifier: Modifier = Modifier,
    columns: GridCells = GridCells.Fixed(2),
    reverseLayout: Boolean = false,
    verticalArrangement: Arrangement.Vertical = if (!reverseLayout) Arrangement.Top else Arrangement.Bottom,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    contentPadding: PaddingValues = PaddingValues(horizontal = 4.dp),
    state: State<ChunkMapState<T>>,
    viewModel: ChunkViewModel<T>,
    item: @Composable (modifier: Modifier, item: T) -> Unit
) {
    if (state.value != null) {
        LazyVerticalGridWithStickHeader(
            modifier = modifier,
            contentPadding = contentPadding,
            columns = columns,
            reverseLayout = reverseLayout,
            verticalArrangement = verticalArrangement,
            horizontalArrangement = horizontalArrangement,
            map = state.value!!.content,
            footer = {
                if (state.value!!.nextPageIndex != null)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(onClick = viewModel::loadMore) {
                            Text(text = stringResource(R.string.load_more))
                        }
                    }
            },
            item = item
        )
    } else {
        if (state is State.Loading) {
            LoadingScreen(
                modifier = modifier,
                message = state.message.str(),
                action = if (state.isInitial()) viewModel::initialize else null
            )
        } else {
            FailureScreen(
                modifier = modifier,
                message = if (state is State.Failure) state.error.str() else stringResource(R.string.unresolved_error),
                retry = if (state is State.Failure) viewModel::initialize else null
            )
        }
    }
}