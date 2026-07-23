package com.orka.myfinances.lib.ui.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    device = "id:pixel_10_pro_xl",
    uiMode = Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(
    device = "id:pixel_10_pro_xl",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
annotation class DefaultPreview
