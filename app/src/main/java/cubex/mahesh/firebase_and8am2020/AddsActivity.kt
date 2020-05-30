package cubex.mahesh.firebase_and8am2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardedVideoAd
import kotlinx.android.synthetic.main.activity_adds.*

class AddsActivity : AppCompatActivity() {
    var mInterstitialAd : InterstitialAd? = null
    var mRewardedVideoAd: RewardedVideoAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adds)
        var test_app_id = "ca-app-pub-2588097597326728~7808154010"
        MobileAds.initialize(this,test_app_id)
        var adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd?.adUnitId = "ca-app-pub-2588097597326728/3863177754"
        mInterstitialAd?.loadAd(AdRequest.Builder().build())

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this)
        mRewardedVideoAd?.loadAd("ca-app-pub-2588097597326728/5836596793",
            AdRequest.Builder().build())
    }

    fun interestial(view: View) {
        mInterstitialAd?.show()
    }
    fun video(view: View) {

       // mRewardedVideoAd.rewardedVideoAdListener = this
        mRewardedVideoAd?.show()
    }
}
