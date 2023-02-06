package com.eavesdropprivacy.aitylgames

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.android.billingclient.api.*
import com.eavesdropprivacy.aitylgames.GooglePlayBillingPreferences.isPurchased
import com.eavesdropprivacy.aitylgames.GooglePlayBillingPreferences.savePurchaseValueToPref
import com.eavesdropprivacy.aitylgames.databinding.ActivityMainBinding
import com.eavesdropprivacy.aitylgames.db.AppDatabase
import com.eavesdropprivacy.aitylgames.fragments.MoreFiledsFragment
import com.eavesdropprivacy.aitylgames.fragments.SimpleFieldsFragment
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

// TODO: HELP - BILLING LIBRARY LINK
// https://programtown.com/how-to-make-in-app-purchase-in-android-kotlin-using-google-play-billing-library/

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var factory: AppFactory
    lateinit var viewModel: AppViewModel
    var isMore: Boolean = false
    private lateinit var fragmentManager: FragmentManager
    private var isAddSimpleFields: Boolean = false
    private var isAddMoreFields: Boolean = false
    var interstitialAd: InterstitialAd? = null
    lateinit var adRequest: AdRequest
    private lateinit var billingClient: BillingClient
    lateinit var skulList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        GooglePlayBillingPreferences.init(this)
        if (isPurchased()) {
            binding.btnProVersion.text = "Remove\nAds"
            binding.btnProVersion.isClickable = false
        } else {
            binding.btnProVersion.text = "Pro Version"
        }

        supportActionBar!!.title = "Personal Details"
        MobileAds.initialize(this)
        adRequest = AdRequest.Builder().build()
//        interstitialAd = InterstitialAd(this)
//        interstitialAd.adUnitId = getString(R.string.ad_unit_id)
//        interstitialAd.loadAd(adRequest)
        InterstitialAd.load(this, getString(R.string.ad_unit_id), adRequest,
        object : InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                Toast.makeText(this@MainActivity, "p0.message ${p0.code}", Toast.LENGTH_SHORT).show()
                interstitialAd = null
            }

            override fun onAdLoaded(ia: InterstitialAd) {
                super.onAdLoaded(ia)
                interstitialAd = ia
            }
        })

        val dao = AppDatabase.getInstance(this).dao
        factory = AppFactory(AppRepository(dao))
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        fragmentManager = supportFragmentManager
        var fragmetnTransaction = fragmentManager.beginTransaction()
        fragmetnTransaction.add(R.id.fragment_container, SimpleFieldsFragment())
        fragmetnTransaction.commit()

        binding.btnMoreFileds.setBackgroundColor(getColor(R.color.btn_non_selected_color))
        binding.btnSimpleFileds.setBackgroundColor(getColor(R.color.btn_selected_color))

        binding.btnSimpleFileds.setOnClickListener(this)
        binding.btnMoreFileds.setOnClickListener(this)
        binding.btnProVersion.setOnClickListener(this)

        val purchasesUpdatedListener =
            PurchasesUpdatedListener { billingResult, purchases ->
                if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){
                    if (purchases != null) {
                        handlePurchases(purchases)
                    }
                    savePurchaseValueToPref(true)
                    binding.btnProVersion.text = "Remove\nAds"
                }else if(billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED){
                    savePurchaseValueToPref(false)
                    binding.btnProVersion.text = "Pro Version"
                }
            }

        billingClient = BillingClient.newBuilder(this)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        skulList = ArrayList<String>()
        skulList.add("eavesdroppingnoads") // android.test.purchased

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                }
            }
            override fun onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        })
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.btn_simple_fileds -> {
                binding.btnMoreFileds.setBackgroundColor(getColor(R.color.btn_non_selected_color))
                binding.btnSimpleFileds.setBackgroundColor(getColor(R.color.btn_selected_color))

                var fragmetnTransaction = fragmentManager.beginTransaction()
                for (fragment in fragmentManager.fragments) {
                    fragmetnTransaction.remove(fragment)
                }
                fragmetnTransaction.add(R.id.fragment_container, SimpleFieldsFragment())
                fragmetnTransaction.commit()
                isMore = false
            }

            R.id.btn_more_fileds -> {
                binding.btnMoreFileds.setBackgroundColor(getColor(R.color.btn_selected_color))
                binding.btnSimpleFileds.setBackgroundColor(getColor(R.color.btn_non_selected_color))
                var fragmetnTransaction1 = fragmentManager.beginTransaction()
                for (fragment in fragmentManager.fragments) {
                    fragmetnTransaction1.remove(fragment)
                }
                fragmetnTransaction1.add(R.id.fragment_container, MoreFiledsFragment())
                fragmetnTransaction1.commit()
                isMore = true
            }

            R.id.btn_pro_version -> {
                billingClient.startConnection(object : BillingClientStateListener {
                    override fun onBillingSetupFinished(billingResult: BillingResult) {
                        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                            val params = SkuDetailsParams.newBuilder()
                            params.setSkusList(skulList)
                                .setType(BillingClient.SkuType.INAPP)

                            billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailList ->

                                for (skuDetail in skuDetailList!!) {
                                    val flowPurchase = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skuDetail)
                                        .build()

                                    val responseCode = billingClient.launchBillingFlow(
                                        this@MainActivity,
                                        flowPurchase
                                    ).responseCode
                                    if (responseCode == 0) {
                                        savePurchaseValueToPref(true)
                                        binding.btnProVersion.text = "Remove\nAds"
                                    }
                                }
                            }
                        }
                    }

                    override fun onBillingServiceDisconnected() {
                        // Try to restart the connection on the next request to
                        // Google Play by calling the startConnection() method.
                        savePurchaseValueToPref(false)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (billingClient != null) {
            billingClient!!.endConnection()
        }
    }

    private fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient!!.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase)
            }
        }
    }
    var ackPurchase = AcknowledgePurchaseResponseListener { billingResult ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            savePurchaseValueToPref(true)
            Toast.makeText(applicationContext, "Item Purchased", Toast.LENGTH_SHORT).show()
            recreate()
        }
    }
}