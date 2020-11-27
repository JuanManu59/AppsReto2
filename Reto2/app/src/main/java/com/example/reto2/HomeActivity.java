package com.example.reto2;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reto2.modelo.Pokemon;
import com.example.reto2.modelo.User;

import java.io.Serializable;

import com.example.reto2.util.HTTPSWebUtilDomi;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private User user;
    private EditText addPokemonTX;
    private Button addPokemonBtn;
    private EditText buscarPokemonTX;
    private Button buscarPokemonBtn;
    private HTTPSWebUtilDomi httpsDomi;
    private Gson gson;
    private FirebaseFirestore db ;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private PokeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PokeAdapter();

        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        user = (User) getIntent().getExtras().getSerializable("us");

        addPokemonTX =findViewById(R.id.addPokemonTX);
        addPokemonBtn =findViewById(R.id.addPokemonBtn);
        buscarPokemonTX =findViewById(R.id.buscarPokemonTX);
        buscarPokemonBtn =findViewById(R.id.buscarPokemonBtn);

        addPokemonBtn.setOnClickListener(this);
        buscarPokemonBtn.setOnClickListener(this);

        gson = new Gson();
        String json = gson.toJson(user);
        httpsDomi = new HTTPSWebUtilDomi();

        consultarTodosPokes();
    }

    public void consultarTodosPokes(){
        adapter.clear();
        Query pokemonRef = db.collection("pokemones").document(user.getUsername()).collection("catch");
        if(pokemonRef!=null){
            pokemonRef.get().addOnCompleteListener(
                    task -> {
                        adapter.clear();
                        for (QueryDocumentSnapshot doc : task.getResult()){
                            Pokemon p = doc.toObject(Pokemon.class);
                            adapter.addPokemon(p);
                        }
                    }
            );
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addPokemonBtn:

                new Thread(
                        ()->{
                                String poke = httpsDomi.GETrequest("https://pokeapi.co/api/v2/pokemon/"+addPokemonTX.getText().toString().toLowerCase().replace(" ","-"));
                            if (poke!=null && !poke.equals("") && !addPokemonTX.getText().toString().equals("")){
                                Pokemon pokemon = gson.fromJson(poke,Pokemon.class);
                                pokemon.getForms().get(0).setNumber(pokemon.getForms().get(0).getNumber());

                                CollectionReference pokemonRef = db.collection("pokemones").document(user.getUsername()).collection("catch");

                                Query query = pokemonRef.whereEqualTo("name",addPokemonTX.getText().toString().toLowerCase());
                                query.get().addOnCompleteListener(
                                        task -> {
                                            if(task.isSuccessful()){

                                                if(task.getResult().size()>0){
                                                    for (QueryDocumentSnapshot document : task.getResult()){
                                                        Pokemon dbPoke = document.toObject(Pokemon.class);
                                                        runOnUiThread( ()->{
                                                            Toast.makeText(this,"Ya has capturado a "+dbPoke.getName(),Toast.LENGTH_LONG).show();
                                                        });
                                                    }
                                                }else {
                                                    db.collection("pokemones").document(user.getUsername()).collection("catch").document(pokemon.getName()).set(pokemon);
                                                    runOnUiThread( ()->{
                                                        Toast.makeText(this,"Has capturado a "+ pokemon.getName()+" !",Toast.LENGTH_LONG).show();
                                                    });
                                                    consultarTodosPokes();
                                                    addPokemonTX.setText("");
                                                }
                                            }
                                        }
                                );
                            }else{
                                runOnUiThread( ()->{
                                    Toast.makeText(this,"No se encontró el pokemon con el nombre "+addPokemonTX.getText().toString(),Toast.LENGTH_LONG).show();
                                });
                            }
                        }
                ).start();
                    break;

            case R.id.buscarPokemonBtn:
                if (!buscarPokemonTX.getText().toString().equals("")){
                    CollectionReference pokemonRef = db.collection("pokemones").document(user.getUsername()).collection("catch");
                    Query query = pokemonRef.whereEqualTo("name",buscarPokemonTX.getText().toString().toLowerCase().replace(" ","-"));
                    query.get().addOnCompleteListener(
                            task -> {
                                if(task.isSuccessful()){
                                    if (task.getResult().size()>0){
                                        for (QueryDocumentSnapshot document : task.getResult()){
                                            Pokemon dbPokemon = document.toObject(Pokemon.class);
                                            Log.e(">>>", ""+dbPokemon.getStats().get(0).getBase_stat());
                                            Intent i = new Intent(this, PokemonActivity.class);
                                            i.putExtra("pokemon", (Serializable) dbPokemon);
                                            i.putExtra("usuario", (Serializable) user);
                                            buscarPokemonTX.setText("");
                                            startActivity(i);
                                        }
                                    }else{
                                        runOnUiThread( ()->{
                                            Toast.makeText(this,"Aún no has capturado a "+buscarPokemonTX.getText().toString(),Toast.LENGTH_LONG).show();
                                        });
                                    }
                                }else{
                                    runOnUiThread( ()->{
                                        Toast.makeText(this,"Aún no has capturado a "+buscarPokemonTX.getText().toString(),Toast.LENGTH_LONG).show();
                                    });
                                }
                            }
                    );
                }

                break;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        consultarTodosPokes();
    }



}