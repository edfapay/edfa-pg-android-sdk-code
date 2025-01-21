package com.example.paymentgatewaynew.payment3

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.example.paymentgatewaynew.common.CardInputForm

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Payment3Screen(navController: NavController,xpressCardPay: EdfaCardPay?,activity: Activity,sale3dsRedirectLauncher: ActivityResultLauncher<Intent>) {
    var bottomSheetVisible by remember { mutableStateOf(true) }
    var cardNumber by remember { mutableStateOf(TextFieldValue("")) }
    var cardHolderName by remember { mutableStateOf(TextFieldValue("")) }
    var expiryDate by remember { mutableStateOf(TextFieldValue("")) }
    var cvc by remember { mutableStateOf(TextFieldValue("")) }

    val scrollState = rememberScrollState()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { newState ->
            newState != SheetValue.Expanded
        })
    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.9f),
        containerColor = Color.White,
        sheetState = bottomSheetState,
        onDismissRequest = { bottomSheetVisible = false }
    ) {
        Column (modifier = Modifier.verticalScroll(scrollState)){
            Card3UI(
                navController,
                cardNumber = cardNumber.text,
                cardHolderName = cardHolderName.text,
                expiryDate = expiryDate.text,
                cvc = cvc.text,
                xpressCardPay = xpressCardPay,
            )

            CardInputForm(
                cardHolderName = cardHolderName,
                onCardHolderNameChange = { cardHolderName = it },
                cardNumber = cardNumber,
                onCardNumberChange = { cardNumber = it },
                cvc = cvc,
                onCvcChange = { cvc = it },
                expiryDate = expiryDate,
                onExpiryDateChange = { expiryDate = it },
                xpressCardPay = xpressCardPay,
                activity = activity,
                sale3dsRedirectLauncher = sale3dsRedirectLauncher
            )
        }

    }
}
