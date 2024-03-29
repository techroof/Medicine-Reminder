package pillapp.ViewController;

/**
 * Created by CharlesPK3 on 3/21/15.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.WindowManager.LayoutParams;

import java.io.IOException;

import teamqitalach.pillapp.R;

/**
 * Utilized the link below as a reference guide:
 * http://wptrafficanalyzer.in/blog/setting-up-alarm-using-alarmmanager-and-waking-up-screen-and-unlocking-keypad-on-alarm-goes-off-in-android/
 *
 * This is a dialog box that AlertActivity called when it is triggered.
 * It contains three buttons to let the user respond to an alarm.
 */

public class AlertAlarm extends DialogFragment {
    MediaPlayer mp;
    Context context;
    Uri alarmSound;
    int alarmChosen;
    SharedPreferences sharedPrefs;
    MediaPlayer mpButtonClick1;
    MediaPlayer mpButtonClick2;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mp= MediaPlayer.create(getActivity(), R.raw.alarm);
        mp.start();
        /** Turn Screen On and Unlock the keypad when this alert dialog is displayed */
        getActivity().getWindow().addFlags(LayoutParams.FLAG_TURN_SCREEN_ON | LayoutParams.FLAG_DISMISS_KEYGUARD);

        /** Creating a alert dialog builder */
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        /** Setting title for the alert dialog */
        builder.setTitle("PillApp");

        /** Making it so notification can only go away by pressing the buttons */
        setCancelable(false);

        final String pill_name = getActivity().getIntent().getStringExtra("pill_name");

        builder.setMessage("Did you take your "+ pill_name + " ?");

        builder.setPositiveButton("I took it", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertActivity act = (AlertActivity)getActivity();
                act.doPositiveClick(pill_name);
                mp.stop();
                getActivity().finish();
            }
        });

        builder.setNeutralButton("Snooze", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                /** Exit application on click OK */
                AlertActivity act = (AlertActivity)getActivity();
                act.doNeutralClick(pill_name);
                mp.stop();
                getActivity().finish();
            }
        });

        return builder.create();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}