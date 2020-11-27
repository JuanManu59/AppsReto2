package com.example.reto2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.reto2.modelo.Pokemon;
import com.example.reto2.modelo.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class PokemonActivity extends AppCompatActivity implements View.OnClickListener{

    private Pokemon pokemon;
    private User user;

    private Button soltarBtn;
    private ImageView fotoIv;
    private TextView nombreTv;
    private TextView tipoTv;
    private TextView defensaTv;
    private TextView ataqueTv;
    private TextView velocidadTv;
    private TextView vidaTv;

    private FirebaseFirestore db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        db = FirebaseFirestore.getInstance();

        pokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemon");
        user = (User) getIntent().getExtras().getSerializable("usuario");

        soltarBtn = findViewById(R.id.soltarBtn);
        fotoIv = findViewById(R.id.fotoIv);
        nombreTv = findViewById(R.id.nombreTv);
        tipoTv = findViewById(R.id.tipoTv);
        defensaTv = findViewById(R.id.defensaTv);
        ataqueTv = findViewById(R.id.ataqueTv);
        velocidadTv = findViewById(R.id.velocidadTv);
        vidaTv = findViewById(R.id.vidaTv);

        soltarBtn.setOnClickListener(this);

        Glide.with(PokemonActivity.this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+pokemon.getForms().get(0).getNumber()+".png").into(fotoIv);

        Log.e(">>>",""+pokemon.getForms().get(0).getNumber());

        nombreTv.setText(pokemon.getName().replace("-"," "));

        String tipo = "";
        for (int i = 0; i<pokemon.getTypes().size();i++){
            tipo+=pokemon.getTypes().get(i).getType().getName()+" ";
        }
        tipoTv.setText("( "+tipo+" )");
        defensaTv.setText(pokemon.getStats().get(2).getBase_stat());
        ataqueTv.setText(pokemon.getStats().get(1).getBase_stat());
        velocidadTv.setText(pokemon.getStats().get(5).getBase_stat());
        vidaTv.setText(pokemon.getStats().get(0).getBase_stat());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.soltarBtn:
                db.collection("pokemones").document(user.getUsername()).collection("catch").document(pokemon.getName()).delete();
                runOnUiThread( ()->{
                    Toast.makeText(this,"Has liberado a "+pokemon.getName()+" !",Toast.LENGTH_LONG).show();
                });
                finish();
                break;
        }
    }

}