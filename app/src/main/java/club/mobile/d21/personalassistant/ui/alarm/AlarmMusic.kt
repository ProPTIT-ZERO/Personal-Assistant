package club.mobile.d21.personalassistant.ui.alarm

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import club.mobile.d21.personalassistant.R

class AlarmMusic : Service() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.royalty)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if(intent.getStringExtra("Alarm")=="On"){
            mediaPlayer?.start()
        }else{
            mediaPlayer?.stop()
            mediaPlayer?.release()
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        super.onDestroy()
    }
}
