package com.edfapg.sdk.core

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.edfapg.sdk.PaymentActivity
import com.edfapg.sdk.model.api.EdfaPgResult.*
import com.edfapg.sdk.model.api.EdfaPgStatus
import com.edfapg.sdk.model.response.sale.EdfaPgSaleCallback
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResult
import com.edfapg.sdk.model.response.base.error.EdfaPgError
import com.edfapg.sdk.model.response.gettransactiondetails.EdfaPgGetTransactionDetailsSuccess
import com.edfapg.sdk.views.edfacardpay.CardTransactionData
import com.edfapg.sdk.views.edfacardpay.EdfaCardPay
import com.edfapg.sdk.views.edfacardpay.EdfaPgSaleWebRedirectActivity

fun handleSaleResponse(cardTransactionData: CardTransactionData,activity: Activity,sale3dsRedirectLauncher: ActivityResultLauncher<Intent>
): EdfaPgSaleCallback {
    return object : EdfaPgSaleCallback {
        override fun onResponse(response: EdfaPgSaleResponse) {
            println("Sale response received: $response")
            PaymentActivity.saleResponse = response
            super.onResponse(response)
        }

        override fun onResult(result: EdfaPgSaleResult) {
            when (result) {
                is EdfaPgSaleResult.Recurring -> println("Recurring payment: $result")
                is EdfaPgSaleResult.Secure3d -> println("3D Secure: $result")
                is EdfaPgSaleResult.Redirect -> {
                    println("Redirect: $result")
                    cardTransactionData.response = result.result
                    val intent = EdfaPgSaleWebRedirectActivity.intent(context = activity, cardTransactionData)
                    sale3dsRedirectLauncher.launch(intent)

                }
                is EdfaPgSaleResult.Decline -> println("Payment declined: $result")
                is EdfaPgSaleResult.Success -> {
                    val successResult = result.result
                    when (successResult.result) {
                        SUCCESS -> println("Payment success: $successResult")
                        ACCEPTED -> println("Payment accepted: $successResult")
                        DECLINED -> println("Payment declined: $successResult")
                        ERROR -> println("Payment error: $successResult")
                        REDIRECT -> TODO()
                    }
                }
            }
        }

        override fun onError(error: EdfaPgError) {
            println("Error during sale: ${error.message}")
            EdfaCardPay.shared()?._onTransactionFailure?.invoke(null, error)
        }

        override fun onFailure(throwable: Throwable) {
            println("Failure during sale: ${throwable.message}")
            EdfaCardPay.shared()?._onTransactionFailure?.invoke(null, throwable)
        }
    }
}

 fun transactionCompleted(
     data: EdfaPgGetTransactionDetailsSuccess?,
     error: EdfaPgError?,
     activity: Activity,
     saleResponse: EdfaPgSaleResponse?
 ) {

     activity.finish()
    if(error != null)
        EdfaCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, error) }

    else if(data == null)
        EdfaCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, error) }

    else
        with(data) {
            when (status) {
                EdfaPgStatus.SETTLED -> EdfaCardPay.shared()!!._onTransactionSuccess?.let { success -> success(saleResponse, data) }
                else -> EdfaCardPay.shared()!!._onTransactionFailure?.let { failure -> failure(saleResponse, data) }
//                EdfaPgStatus.SECURE_3D -> TODO()
//                EdfaPgStatus.REDIRECT -> TODO()
//                EdfaPgStatus.PENDING -> TODO()
//                EdfaPgStatus.REVERSAL -> TODO()
//                EdfaPgStatus.REFUND -> TODO()
//                EdfaPgStatus.CHARGEBACK -> TODO()
//                EdfaPgStatus.DECLINED -> TODO()
            }
        }


}

