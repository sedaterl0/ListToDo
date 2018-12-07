package com.example.sedater.listtodo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    int id;
    TextView txt1,txt2,txt3,txt4;

    AlertDialog.Builder alertDialog;
    database db;
    RelativeLayout relative;
    CalendarView calendarView;
    String date;
    Calendar takvim;
    String nowtime;
    ImageButton smsyolla;
    SmsManager sms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

setTitle("Görev");
        relative=(RelativeLayout)findViewById(R.id.relative);
        smsyolla=(ImageButton)findViewById(R.id.smsbtn);


        txt1=(TextView)findViewById(R.id.textView4);
        txt2=(TextView)findViewById(R.id.textView2);
        txt3=(TextView)findViewById(R.id.txtpriority);
        txt4=(TextView)findViewById(R.id.txtdate);

      /*  takvim = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        nowtime=sdf.format(takvim.getTime());*/


        Intent intent=getIntent();
        id = intent.getIntExtra("id", 0);//Burdaki 0 eğer değer alınmazsa default olarak verilecek değer
        db = new database(getApplicationContext());
        HashMap<String, String> map = db.taskDetay(id);




        //String taskdate=String.valueOf(map.get("taskdate"));


        txt2.setText(map.get("taskbaslik"));
        txt1.setText(map.get("taskicerik").toString());
        txt3.setText(map.get("taskpriority").toString());
        txt4.setText(map.get("taskdate"));
        String priority=map.get("taskpriority").toString();

        smsyolla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              sendsms(txt2.getText().toString()+"\n"+txt1.getText().toString()  );

            }
        });

      /*  if(priority.matches("1")){
            relative.setBackgroundColor(Color.YELLOW);
        }
        else if(priority.matches("2")){
            relative.setBackgroundColor(Color.GREEN);
        }
        else if(priority.matches("3")){
            relative.setBackgroundColor(Color.RED);
        }*/

       /*if(taskdate.matches(nowtime)){
            db.deleteTask(id);
            Toast.makeText(getApplicationContext(), "Zamanı doldu Silindi", Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(this,MainActivity.class);
            startActivity(intent1);
        }*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(Main2Activity.this);
                alertDialog.setTitle("Uyarı");
                alertDialog.setMessage("Görev Silinsin mi?");
                alertDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        db = new database(getApplicationContext());
                        db.deleteTask(id);
                        Toast.makeText(getApplicationContext(), "Görev Başarıyla Silindi", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });
                alertDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                    }
                });
                alertDialog.show();
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = db.taskDetay(id);

                 alertDialog = new AlertDialog.Builder(Main2Activity.this);
                View mview=getLayoutInflater().inflate(R.layout.alertdialog,null);
                calendarView =(CalendarView)mview.findViewById(R.id.calendarView);
                final TextView ustbaslik=(TextView)mview.findViewById(R.id.baslik);
                ustbaslik.setText("Görevi Düzenle");
                final TextView altbaslik=(TextView)mview.findViewById(R.id.altbaslik);
                altbaslik.setText("Metni Düzenleyiniz");
                final EditText edttextust=(EditText) mview.findViewById(R.id.edtbaslik);
                final EditText edttext=(EditText) mview.findViewById(R.id.editgiris);


                edttextust.setText(map.get("taskbaslik"));
                edttext.setText(map.get("taskicerik").toString());
                String bas=map.get("taskpriority").toString();
              //  calendarView.setDate(Long.getLong(map.get("taskdate").toString()));

                final RadioButton az=(RadioButton)mview.findViewById(R.id.azonem);
                final RadioButton orta=(RadioButton)mview.findViewById(R.id.onem);
                final RadioButton cok=(RadioButton)mview.findViewById(R.id.cokonem);

                final String[] s = {null};
                s[0]="0";
                if(bas.matches("0")){
                    az.setChecked(false);
                    orta.setChecked(false);
                    cok.setChecked(false);

                }
                else if(bas.matches("1")){

                    az.setChecked(true);
                    orta.setChecked(false);
                    cok.setChecked(false);
                }
                else if (bas.matches("2")){
                    az.setChecked(false);
                    orta.setChecked(true);
                    cok.setChecked(false);
                }
                else if (bas.matches("3")){
                    az.setChecked(false);
                    orta.setChecked(false);
                    cok.setChecked(true);
                }
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

                alertDialog.setPositiveButton("Kaydet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String baslik=String.valueOf(edttextust.getText());
                        String icerik = String.valueOf(edttext.getText());

                        if(baslik.matches("") || icerik.matches("") || s[0].matches("") || date.matches("")  ){
                            Toast.makeText(getApplicationContext(), "Tüm Bilgileri Eksiksiz Doldurunuz", Toast.LENGTH_SHORT).show();
                        }else{

                            db.taskDuzenle(baslik, icerik, s[0],date,id);
                            db.close();
                            Toast.makeText(getApplicationContext(), "Göreviniz Başarıyla Düzenlendi.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                alertDialog.setNegativeButton("İptal",null);


                alertDialog.setView(mview);
                AlertDialog mdialog=alertDialog.create();
                mdialog.show();
            }
        });





    }

    public void sendsms(String text){

      /*  sms=SmsManager.getDefault();
        sms.sendTextMessage("5434839637",null,text,null,null);*/
       Intent mesajGonder = new Intent(Intent.ACTION_VIEW);
        mesajGonder.setData(Uri.parse("sms:"+ ""));
        mesajGonder.putExtra("sms_body", txt2.getText()+"\n"+txt1.getText());
        startActivity(mesajGonder);

    }

}
