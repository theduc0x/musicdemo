package theduc.dmt.appmusicdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView tvName, tvStart, tvTimeTotal;
    private SeekBar sbTime;
    private ImageButton ibPrev, ibPlay, ibNext;
    private ImageView ivImage;
    ArrayList<Song> listSong;
    int position = 0;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        addSong();

        animation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        createMediaPlayer();

        ibPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.mediaPlayer.isPlaying()) {
                    Util.mediaPlayer.pause();
                    ibPlay.setImageResource(R.drawable.ic_play);
                } else {
                    Util.mediaPlayer.start();
                    ibPlay.setImageResource(R.drawable.ic_pause);
                }
                setTimeTotal();
                updateTimeSong();
                ivImage.startAnimation(animation);
            }
        });

        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSong();
            }
        });

        ibPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0 ) {
                    position = listSong.size() - 1;
                }
                if (Util.mediaPlayer.isPlaying()) {
                    Util.mediaPlayer.stop();
                }
                createMediaPlayer();
                Util.mediaPlayer.start();
                ibPlay.setImageResource(R.drawable.ic_pause);
                setTimeTotal();
            }
        });

        sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Util.mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void setTimeTotal() {
        SimpleDateFormat dinhdangGio = new SimpleDateFormat("mm:ss");
        tvTimeTotal.setText(dinhdangGio.format(Util.mediaPlayer.getDuration()));
        sbTime.setMax(Util.mediaPlayer.getDuration());
    }

    private void updateTimeSong() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhdangGio = new SimpleDateFormat("mm:ss");
                tvStart.setText(dinhdangGio.format(Util.mediaPlayer.getCurrentPosition()));
                sbTime.setProgress(Util.mediaPlayer.getCurrentPosition());
                Util.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        nextSong();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }

    private void anhXa() {
        tvTimeTotal = findViewById(R.id.tv_time_end);
        tvName = findViewById(R.id.tv_name);
        tvStart = findViewById(R.id.tv_time_start);
        sbTime = findViewById(R.id.sb_time_song);
        ibPrev = findViewById(R.id.ib_prev);
        ibPlay = findViewById(R.id.ib_play);
        ibNext = findViewById(R.id.ib_next);
        ivImage = findViewById(R.id.iv_image);
    }
    private void addSong() {
        listSong = new ArrayList<>();
        listSong.add(new Song("Chạy về nơi phía anh",
                R.raw.chay_ve_noi_phia_anh,
                R.drawable.chayvenoiphiaanh));
        listSong.add(new Song("Đâu còn gì trong nhau",
                R.raw.daucongitrongnhau,
                R.drawable.daucongitrongnhau));
        listSong.add(new Song("Dù mua thôi rơi",
                R.raw.dumuathoiroi,
                R.drawable.dumuathoiroi));
        listSong.add(new Song("Giữ em đi",
                R.raw.giuemdi,
                R.drawable.giuemdi));
    }
    private void nextSong() {
        position++;
        if (position > listSong.size() - 1) {
            position = 0;
        }
        if (Util.mediaPlayer.isPlaying()) {
            Util.mediaPlayer.stop();
        }
        createMediaPlayer();
        Util.mediaPlayer.start();
        ibPlay.setImageResource(R.drawable.ic_pause);
        setTimeTotal();
    }

    private void createMediaPlayer() {
        Util.mediaPlayer
                = MediaPlayer.create(MainActivity.this,
                listSong.get(position).getFile());
        tvName.setText(listSong.get(position).getName());
        ivImage.setImageResource(listSong.get(position).getImage());
    }
}