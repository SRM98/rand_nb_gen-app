package com.sergiu.generator


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amazon.device.ads.AdRegistration
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    //private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var mRewardedVideoAd: RewardedVideoAd
    private var random : Int = 0
    private var minimum : Int = 0
    private var maximum : Int = 0
    private var videoOrInter = 0

    /////////////////////////////AMAZON//////////////////////////////////////
    private lateinit var interstitialAdAmazon: com.amazon.device.ads.InterstitialAd


    /////////////////////////////AMAZON/////////////////////////////////////


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /////////////////////////////AMAZON/////////////////////////////////////
        AdRegistration.setAppKey("6b14380a9f4b4889b64b2119290a3c60")
        // For debugging purposes flag all ad requests as tests, but set to false for production builds.
        AdRegistration.enableTesting(false)
        adview_amazon.loadAd()
        interstitialAdAmazon = com.amazon.device.ads.InterstitialAd(this)
        interstitialAdAmazon.loadAd()
        /////////////////////////////AMAZON/////////////////////////////////////
//
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-4429075658629144/3026629533"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())

        MobileAds.initialize(this, "ca-app-pub-4429075658629144/2193627651")
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        loadRewardedVideoAd()

        adButton.setOnClickListener {
            videoOrInter++

            if(videoOrInter == 2) {
                if(mRewardedVideoAd.isLoaded) {
                    mRewardedVideoAd.show()
                } else {
                    loadRewardedVideoAd()
                }
                loadAmazonInterstitialAd()
            }
            if(videoOrInter == 4) {
                interstitialAdAmazon.showAd()
                loadRewardedVideoAd()
                videoOrInter = 0
            }

            if(minimumZone.text.toString() != "" && maximumZone.text.toString() != "") {
                minimum = Integer.parseInt(minimumZone.text.toString())
                maximum = Integer.parseInt(maximumZone.text.toString())
            }
            if(minimum <= 9999999 && maximum <= 9999999 && minimum < maximum) {
                getRandomNumber(minimum, maximum)
                numberDisplay.text = random.toString()
            } else {
                numberDisplay.text = "NaN"
            }
            adview_amazon.showAd()
        }

        loadBannerAd()
    }

    private fun getRandomNumber(minimum : Int ,maximum : Int) {
        random = (minimum..maximum).random()
    }

    private fun loadBannerAd() {
        //AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
        adTop.loadAd(AdRequest.Builder().build())
        adview_amazon.loadAd()
        adview_amazon.showAd()
    }

    private fun loadAmazonInterstitialAd() {
        interstitialAdAmazon.loadAd()
    }

    private fun loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-4429075658629144/2193627651",
            AdRequest.Builder().build())
    }

//    private fun loadInterstitialAd() {
//        mInterstitialAd = InterstitialAd(this)
//        mInterstitialAd.adUnitId = "ca-app-pub-4429075658629144/3026629533"
//        mInterstitialAd.loadAd(AdRequest.Builder().build())
//    }

}
