package com.auribises.voicerecognitiondemo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements RecognitionListener,View.OnClickListener{


    // Speech To Text Conversion
    // Text To Speech

    HashMap<String,String> queAns;

    TextView txtWords;
    Button btnSpeak;

    ProgressDialog progressDialog;

    SpeechRecognizer speechRecognizer;

    TextToSpeech tts;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    void initViews(){
        txtWords = (TextView)findViewById(R.id.textView);
        btnSpeak = (Button)findViewById(R.id.button);

        btnSpeak.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Listening...");
        progressDialog.setCancelable(false);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    Toast.makeText(MainActivity.this,"TextToSpeech Initialized",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"TextToSpeech Not Initialized",Toast.LENGTH_LONG).show();
                }
            }
        });

        queAns = new HashMap<>();
        queAns.put("how","I am Fine, Thank You");
        queAns.put("duniya","Duniya Theeka Thaak Hai");

        preferences = getSharedPreferences("voice",MODE_PRIVATE);
        editor = preferences.edit();

        String lastQue = preferences.getString("lastQue","Ask Any Que");
        txtWords.setText(lastQue);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {
        progressDialog.show();
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        progressDialog.dismiss();
    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> resultList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if(resultList!=null && resultList.size()>0){
            String sentence = resultList.get(0);
            txtWords.setText(sentence);

            /*if(sentence.toLowerCase().contains("sms") && sentence.toLowerCase().contains("father")){

            }else if(sentence.contains("call") && sentence.contains("mother")){

            }else{

            }*/

            if(sentence.toLowerCase().contains("how") && sentence.toLowerCase().contains("you")){
                tts.speak("I am Fine, Thank You Very Much",TextToSpeech.QUEUE_FLUSH,null);
            } else if(sentence.toLowerCase().contains("duniya")){
                tts.speak("Duniya, theek thak hai",TextToSpeech.QUEUE_FLUSH,null);
            }else {
                tts.speak("Sorry, Kuch samajh nahi aaya",TextToSpeech.QUEUE_FLUSH,null);
            }


            editor.putString("lastQue",sentence);
            editor.commit();

        }else{
            txtWords.setText("Sorry Please Try Again");
        }
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button){
            speechRecognizer.startListening(RecognizerIntent.getVoiceDetailsIntent(this));
        }
    }
}
