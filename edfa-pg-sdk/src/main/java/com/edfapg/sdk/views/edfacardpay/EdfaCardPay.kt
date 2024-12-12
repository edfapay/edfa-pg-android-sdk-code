package com.edfapg.sdk.views.edfacardpay

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.edfapg.sdk.model.request.order.EdfaPgSaleOrder
import com.edfapg.sdk.model.request.payer.EdfaPgPayer
import com.edfapg.sdk.model.response.sale.EdfaPgSaleResponse

internal var instance:EdfaCardPay? = null
class EdfaCardPay {
    constructor(){
        instance = this
    }

    var _order:EdfaPgSaleOrder? = null
    var _payer:EdfaPgPayer? = null
    var _onTransactionFailure:((EdfaPgSaleResponse?, Any?) -> Unit)? = null
    var _onTransactionSuccess:((EdfaPgSaleResponse?, Any?) -> Unit)? = null
    var _onError:((Any) -> Unit)? = null
    var _onPresent:((Activity) -> Unit)? = null

    fun setOrder(order:EdfaPgSaleOrder) : EdfaCardPay{
        _order = order
        return this
    }

    fun setPayer(payer:EdfaPgPayer) : EdfaCardPay{
        _payer = payer
        return this
    }

    fun onTransactionFailure(callback:(EdfaPgSaleResponse?, Any?) -> Unit) : EdfaCardPay{
        _onTransactionFailure = callback
        return this
    }

    fun onTransactionSuccess(callback:(EdfaPgSaleResponse?, Any?) -> Unit) : EdfaCardPay{
        _onTransactionSuccess = callback
        return this
    }

    fun initialize(context:Context, onError:(Any) -> Unit, onPresent:(Activity) -> Unit){
        Log.e("initialize","initialize")
        _onError = onError
        _onPresent = onPresent

        context.startActivity(intent(context, onError, onPresent))
    }

    fun intent(context:Context, onError:(Any) -> Unit, onPresent:(Activity) -> Unit) : Intent {
        Log.e("intent","intent")
        _onError = onError
        _onPresent = onPresent

        val intent = Intent(context, EdfaCardPayActivity::class.java)
        return  intent
    }

    fun fragment(onError:(Any) -> Unit, onPresent:(Activity) -> Unit) : Fragment {
        _onError = onError
        _onPresent = onPresent

        return  EdfaCardPayFragment()
    }

    companion object{
        internal fun shared() : EdfaCardPay?{
            return instance
        }
    }
}