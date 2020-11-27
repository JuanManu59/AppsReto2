package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.reto2.modelo.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.io.Serializable;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText usernET;
    private Button ingresarBtn;
    private FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        usernET = findViewById(R.id.usernET);
        ingresarBtn = findViewById(R.id.ingresarBtn);

        ingresarBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ingresarBtn:
                String username = usernET.getText().toString();
                User user = new User(UUID.randomUUID().toString(),username);

                //Saber si el usuario ya está registrado
                CollectionReference usersRef = db.collection("users");
                Query query = usersRef.whereEqualTo("username",username);
                query.get().addOnCompleteListener(
                        task -> {
                            if(task.isSuccessful()){

                                if(task.getResult().size()>0){
                                    for (QueryDocumentSnapshot document : task.getResult()){
                                        User dbUser = document.toObject(User.class);
                                        vistaHome(dbUser);
                                    }
                                }else {
                                    db.collection("users").document(user.getId()).set(user);
                                    vistaHome(user);
                                }

                            }
                        }
                );


          //

                break;
        }
    }

    public void vistaHome(User user){
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra("us", (Serializable) user);
        startActivity(i);
    }
}