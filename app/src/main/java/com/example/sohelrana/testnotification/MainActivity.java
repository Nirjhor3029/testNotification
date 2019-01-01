package com.example.sohelrana.testnotification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    //    1. notification Channel
    //          a.channel id
    //          a.channel id
    //          a.channel id
    //    1. notification Builder
    //    1. notification Manager

    private static final String CHANNEL_ID =  "nirjhor_coding";
    private static final String CHANNEL_NAME =  "nirjhor coding";
    private static final String CHANNEL_DESC =  "nirjhor coding notifications";

    private TextView tv_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        tv_token = findViewById(R.id.tv_Token);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            mChannel.setDescription(CHANNEL_DESC);

            //bujhlam na
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(mChannel);
        }


        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if(task.isSuccessful()){
                            String token = task.getResult().getToken();
                            tv_token.setText("Token: "+token);
                        }else {
                            Toast.makeText(MainActivity.this, "Token is not generated", Toast.LENGTH_SHORT).show();
                            tv_token.setText(task.getException().getMessage());
                        }
                    }
                });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void displayNotification(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Hurray! it is working !!!")
                .setContentText("your first notification")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        /*create notification manager*/
        NotificationManagerCompat mNotificationManagerCompat = NotificationManagerCompat.from(this);
        mNotificationManagerCompat.notify(1,mBuilder.build());
    }

    public void clickButton1(View view) {
        Toast.makeText(this, "Clicked !!", Toast.LENGTH_SHORT).show();
        displayNotification();
    }
}
