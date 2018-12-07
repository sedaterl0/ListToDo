package com.example.sedater.listtodo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity {

    database dbkullan,db;
    ArrayAdapter<String>arAdapter;
    ListView lstTask;

    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<HashMap<String, String>> tasklar;
    String taskbaslik[];
    int taskid[],taskonem[];
    int []taskid2;

    String date;
    CalendarView calendarView,calendertakvim;
    String nowtime;
    Calendar takvim;
    String zaman,title,gir;
    int tid;
    String secilen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Görevlerim");

        takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        nowtime=sdf.format(takvim.getTime());

        dbkullan=new database(this);
        lstTask = (ListView)findViewById(R.id.lstTask);
        db = new database(getApplicationContext());
        taskYukle(nowtime);
calendertakvim =(CalendarView)findViewById(R.id.calendertakvim);
calendertakvim.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        i1++;
        secilen=i2+"-"+ i1 +"-"+i;
        taskYukle(secilen);
    }
});






      /*  if(taskdate.matches(nowtime)){
            db.deleteTask(id);
            Toast.makeText(getApplicationContext(), "Zamanı doldu Silindi", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(this,MainActivity.class);
            startActivity(intent1);
        }*/


        otomatikBildirim();



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                View mview=getLayoutInflater().inflate(R.layout.alertdialog,null);

                final TextView ustbaslik=(TextView)mview.findViewById(R.id.baslik);
                ustbaslik.setText("Yeni Görev Ekle");
                final TextView altbaslik=(TextView)mview.findViewById(R.id.altbaslik);
                altbaslik.setText("İçeriği Giriniz");
                final EditText edttextust=(EditText) mview.findViewById(R.id.edtbaslik);
                final EditText edttext=(EditText) mview.findViewById(R.id.editgiris);

                final RadioButton az=(RadioButton)mview.findViewById(R.id.azonem);
                final RadioButton orta=(RadioButton)mview.findViewById(R.id.onem);
                final RadioButton cok=(RadioButton)mview.findViewById(R.id.cokonem);
                calendarView=(CalendarView)mview.findViewById(R.id.calendarView);
                final String[] s = {null};
                s[0]="0";
                az.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s[0] ="1";
                    }
                });
                orta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s[0] ="2";
                    }
                });

                cok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s[0]="3";
                    }
                });


                calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                        i1++;
                        date=i2+"-"+ i1 +"-"+i;
                    }
                });
                dialog.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String baslik = String.valueOf(edttextust.getText());
                        String task = String.valueOf(edttext.getText());
                        if(baslik.matches("") || task.matches("") || s[0].matches("")|| date.matches("") ){
                            Toast.makeText(getApplicationContext(), "Tüm Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_SHORT).show();
                        }else{
                        dbkullan.insertTask(baslik,task,s[0],date);
                            dbkullan.close();
                            Toast.makeText(getApplicationContext(), "Göreviniz Başarıyla Eklendi.", Toast.LENGTH_SHORT).show();

                            //taskYukle(date);

                        }
                    }
                });
                dialog.setNegativeButton("İptal",null);


                dialog.setView(mview);
                AlertDialog mdialog=dialog.create();
                mdialog.show();


            }

        });
            }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    public void taskYukle(String girilen){

        tasklar=dbkullan.tasks();
int j=0,k=0,l=0,m=0,n=0,o = 0;

        taskbaslik = new String[tasklar.size()];
        taskid = new int[tasklar.size()];
        taskid2 = new int[tasklar.size()];
        taskonem=new int[tasklar.size()];
        int []uc=new int[tasklar.size()];
        int []iki=new int[tasklar.size()];
        int []bir=new int[tasklar.size()];
        for(int i=0;i<tasklar.size();i++){

            if(girilen.matches(tasklar.get(i).get("taskdate"))){

                taskbaslik[j] = tasklar.get(i).get("taskbaslik").toString();
                taskid[j] = Integer.parseInt(tasklar.get(i).get("taskid"));
                taskonem[j]=Integer.parseInt(tasklar.get(i).get("taskpriority"));
                if(taskonem[j]==3){
                    uc[k]=j+1;
                    k++;
                }
                else if(taskonem[j]==2){
                    iki[l]=j+1;
                    l++;
                }
                else if(taskonem[j]==1){
                    bir[m]=j+1;
                    m++;
                }
                j++;
            }
        }
        ArrayList<String>taskbas=new ArrayList<String>();
        ArrayList<String>taskbas2=new ArrayList<String>();

        j=0;
        if(taskid.length!=0) {
            while (j < taskid.length){
                if (taskid[j] != 0) {
                    taskbas.add(taskbaslik[j]);


                }
                j++;
        }
        if(uc.length!=0) {
            while (n<uc.length) {
                if(uc[n] != 0) {
                    taskbas2.add(taskbas.get(uc[n] - 1));
                    taskid2[o] = taskid[uc[n] - 1];
                    o++;
                }
                n++;
            }
        }
            l=0;
            if (iki.length!=0) {
                while(l<iki.length){
                    if(iki[l]!=0) {
                        taskbas2.add(taskbas.get(iki[l] - 1));
                        taskid2[o] = taskid[iki[l] - 1];
                        o++;
                    }
                    n++;
                    l++;
                }
            }

            m=0;
            if(bir.length!=0) {
                while ( m<bir.length) {
                    if(bir[m] != 0 ) {
                        taskbas2.add(taskbas.get(bir[m] - 1));
                        taskid2[o] = taskid[bir[m] - 1];
                        o++;
                    }
                    n++;
                    m++;
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "Kayıtlı Göreviniz Bulunmamaktadır.", Toast.LENGTH_SHORT).show();
        }





        lv = (ListView) findViewById(R.id.lstTask);
        adapter = new ArrayAdapter<String>(this, R.layout.row, R.id.task_title, taskbas2);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("id", taskid2[i]);
                startActivity(intent);
            }
        });


    }
  /*  public  void otomatikSil(String nowtime){

        tasklar=dbkullan.tasks();
        for(int i=0;i<tasklar.size();i++){

            zaman=tasklar.get(i).get("taskdate");
            if(zaman.matches(nowtime)){
                dbkullan.deleteTask(i);
                Toast.makeText(this, "Zamanı doldu Silindi", Toast.LENGTH_SHORT).show();

               // taskYukle();

            }


        }

    }*/
    public  void otomatikBildirim(){

        tasklar=dbkullan.tasks();
        for(int i=0;i<tasklar.size();i++){

            zaman=tasklar.get(i).get("taskdate");
            title=tasklar.get(i).get("taskbaslik");
            gir=tasklar.get(i).get("taskicerik");
            tid=Integer.parseInt(tasklar.get(i).get("taskid"));
            if(Integer.parseInt(zaman.substring(0,2))-Integer.parseInt(nowtime.substring(0,2))<=1){
                if(sendNotification(zaman,title,gir,tid)){
                    continue;
                }

                // taskYukle();

            }


        }
    }
    public boolean sendNotification(String zaman,String title,String gir,int i) {


        NotificationManager noti=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent intent =new Intent(this,Main2Activity.class);
        intent.putExtra("id",i);
        PendingIntent launchIntent =
                PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notif= new Notification.Builder(this)
                .setSmallIcon(R.drawable.ic_today_black_24dp)
                .setContentTitle(zaman)
                .setContentText(title)
                .setContentIntent(launchIntent)
                .build();
        notif.defaults |= Notification.DEFAULT_VIBRATE;
        notif.defaults |= Notification.DEFAULT_SOUND;

        noti.notify(0,notif);

        return true;

    }
}
