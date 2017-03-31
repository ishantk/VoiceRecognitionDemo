package com.auribises.voicerecognitiondemo;

import android.app.ProgressDialog;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecognitionListener,View.OnClickListener{


    // Speech To Text Conversion
    // Text To Speech

    TextView txtWords;
    Button btnSpeak;

    ProgressDialog progressDialog;

    SpeechRecognizer speechRecognizer;


    void initViews(){
        txtWords = (TextView)findViewById(R.id.textView);
        btnSpeak = (Button)findViewById(R.id.button);

        btnSpeak.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Listening...");
        progressDialog.setCancelable(false);

        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(this);
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

            if(sentence.contains("sms") && sentence.contains("father")){

            }else if(sentence.contains("call") && sentence.contains("mother")){

            }else{

            }

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
