package com.example.inratingstatistics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class mainActivity extends AppCompatActivity {

    public String postID;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.main_activity);

        Button goButton = (Button) findViewById(R.id.go_button);
        final EditText postIdText = (EditText) findViewById(R.id.postIdEdit);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postID = postIdText.getText().toString();
                if(!postID.isEmpty()){
                    Intent intent = new Intent(v.getContext(), statisticsActivityController.class);
                    intent.putExtra("postId", postID);
                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Введіть id Поста",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
