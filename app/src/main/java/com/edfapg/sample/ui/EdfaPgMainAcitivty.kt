/*
 * Property of EdfaPg (https://edfapay.com).
 */

package com.edfapg.sample.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.edfapg.sample.databinding.ActivityMainBinding
import com.edfapg.sdk.model.request.order.*
import com.edfapg.sdk.model.request.payer.*
import com.edfapg.sdk.toolbox.DesignType
import com.edfapg.sdk.views.edfacardpay.*
import java.util.UUID

class EdfaPgMainAcitivty : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureView()
    }

    private fun configureView() {
        binding.btnSale.setOnClickListener {
            startActivity(Intent(this, EdfaPgSaleActivity::class.java))
        }
        binding.btnRecurringSale.setOnClickListener {
            startActivity(Intent(this, EdfaPgRecurringSaleActivity::class.java))
        }
        binding.btnCapture.setOnClickListener {
            startActivity(Intent(this, EdfaPgCaptureActivity::class.java))
        }
        binding.btnCreditVoid.setOnClickListener {
            startActivity(Intent(this, EdfaPgCreditvoidActivity::class.java))
        }
        binding.btnGetTransStatus.setOnClickListener {
            startActivity(Intent(this, EdfaPgGetTransStatusActivity::class.java))
        }
        binding.btnGetTransDetails.setOnClickListener {
            startActivity(Intent(this, EdfaPgGetTransDetailsActivity::class.java))
        }

        binding.btnSaleWithCardUi.setOnClickListener {
            payWithCard()
        }
    }

    fun payWithCard(){

        val order = EdfaPgSaleOrder(
            id = UUID.randomUUID().toString(),
            amount = 1.00,
            currency = "SAR",
            description = "Test Order"
        )

        val payer = EdfaPgPayer(
            "Zohaib","Kambrani",
            "Riyadh","SA", "Riyadh","123123",
            "a2zzuhaib@gmail.com","966500409598",
            "171.100.100.123"
        )

        val edfaCardPay = EdfaCardPay()
            .setOrder(order)
            .setPayer(payer)
            .onTransactionFailure { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Failure", Toast.LENGTH_LONG).show()
            }.onTransactionSuccess { res, data ->
                print("$res $data")
                Toast.makeText(this, "Transaction Success", Toast.LENGTH_LONG).show()
            }

        /*
        * Precise way to start card payment (ready to use)
        * */
        edfaCardPay.initialize(
            this,
            null,
            onError = {

            },
            onPresent = {

            }
        )
    }
}
