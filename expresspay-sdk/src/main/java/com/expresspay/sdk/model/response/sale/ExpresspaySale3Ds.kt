/*
 * Property of Expresspay (https://expresspay.sa).
 */

package com.expresspay.sdk.model.response.sale

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.expresspay.sdk.model.api.ExpresspayAction
import com.expresspay.sdk.model.api.ExpresspayRedirectMethod
import com.expresspay.sdk.model.api.ExpresspayResult
import com.expresspay.sdk.model.api.ExpresspayStatus
import com.expresspay.sdk.model.response.base.result.IDetailsExpresspayResult
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

/**
 * The SALE 3DS result of the [ExpresspaySaleResult].
 * @see ExpresspaySaleResponse
 *
 * @property redirectUrl URL to which the Merchant should redirect the Customer.
 * @property redirectParams the [ExpresspaySale3dsRedirectParams].
 * @property redirectMethod the method of transferring parameters (POST/GET).
 */
data class ExpresspaySale3Ds(
    @NonNull
    @SerializedName("action")
    override val action: ExpresspayAction,
    @NonNull
    @SerializedName("result")
    override val result: ExpresspayResult,
    @NonNull
    @SerializedName("status")
    override val status: ExpresspayStatus,
    @NonNull
    @SerializedName("order_id")
    override val orderId: String,
    @NonNull
    @SerializedName("trans_id")
    override val transactionId: String,
    @NonNull
    @SerializedName("trans_date")
    override val transactionDate: Date,
    @Nullable
    @SerializedName("descriptor")
    override val descriptor: String?,
    @NonNull
    @SerializedName("amount")
    override val orderAmount: Double,
    @NonNull
    @SerializedName("currency")
    override val orderCurrency: String,
    @NonNull
    @SerializedName("redirect_url")
    val redirectUrl: String,
    @NonNull
    @SerializedName("redirect_params")
    val redirectParams: ExpresspaySale3dsRedirectParams,
    @NonNull
    @SerializedName("redirect_method")
    val redirectMethod: ExpresspayRedirectMethod,
) : IDetailsExpresspayResult, Serializable
