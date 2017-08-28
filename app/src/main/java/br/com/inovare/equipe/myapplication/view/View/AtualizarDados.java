package br.com.inovare.equipe.myapplication.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.inovare.equipe.myapplication.R;
import br.com.inovare.equipe.myapplication.view.Model.ConfiguracaoFirebase;
import br.com.inovare.equipe.myapplication.view.Model.Usuario;

public class AtualizarDados extends AppCompatActivity {
    private EditText estado;
    private EditText nome;
    private EditText cidade;
    private DatabaseReference reference;
    private Button enviar;
    private FirebaseAuth aut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_dados);
        enviar=(Button) findViewById(R.id.atualizarDados);
        nome=(EditText) findViewById(R.id.atualizarNome);
        estado=(EditText) findViewById(R.id.atualizarEstado);
        cidade=(EditText) findViewById(R.id.atualizarCidade);
        pegarDados();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                envair();
            }
        });
    }

    private void pegarDados(){
        reference=ConfiguracaoFirebase.refernciaBancoFirebase();
        aut=ConfiguracaoFirebase.autenticarDados();
        FirebaseUser idAtual=aut.getCurrentUser();
        String id=idAtual.getUid();
        reference.child("user").child(id.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                    Usuario u=dataSnapshot.getValue(Usuario.class);
                    nome.setText(u.getNome().toString());
                    estado.setText(u.getEstado().toString());
                    cidade.setText(u.getCidade().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
   public void envair(){
       Usuario usu=new Usuario();
       usu.setNome(nome.getText().toString());
       usu.setEstado(estado.getText().toString());
       usu.setCidade(cidade.getText().toString());
       usu.atualizarDados();
   }

}
