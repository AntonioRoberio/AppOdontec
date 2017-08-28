package br.com.inovare.equipe.myapplication.view.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import br.com.inovare.equipe.myapplication.R;
import br.com.inovare.equipe.myapplication.view.Model.ConfiguracaoFirebase;
import br.com.inovare.equipe.myapplication.view.Model.Usuario;

public class AtualizarSenha extends AppCompatActivity {
    private String pegarSenhaAtual="";
    private EditText atualSenha;
    private EditText senhaNova;
    private EditText confirmeNovasenha;
    private Button enviarSenhaNov;
    private DatabaseReference reference;
    private FirebaseAuth aut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_senha);
        atualSenha=(EditText) findViewById(R.id.senhaAtual);
        senhaNova=(EditText) findViewById(R.id.novaSenha);
        confirmeNovasenha=(EditText) findViewById(R.id.confirmeSenhaNova);
        enviarSenhaNov=(Button) findViewById(R.id.enviarSenhaNova);
        enviarSenhaNov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(senhaNova.getText().toString().equals(confirmeNovasenha.getText().toString())){
                    atualizarSenha();
                }else{
                    Toast.makeText(AtualizarSenha.this,"As senhas são divergentes",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void pegarSenha(){
        reference=ConfiguracaoFirebase.refernciaBancoFirebase();
        aut= ConfiguracaoFirebase.autenticarDados();
        FirebaseUser userAtual=aut.getCurrentUser();
        String id=userAtual.getUid();

        reference.child("user").child(id.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario u=dataSnapshot.getValue(Usuario.class);
                pegarSenhaAtual=u.getSenha().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    public void atualizarSenha(){
        if(atualSenha.getText().equals(pegarSenhaAtual.toString())){
            Usuario usuario=new Usuario();
            usuario.setSenha(senhaNova.getText().toString());
            aut= ConfiguracaoFirebase.autenticarDados();
            FirebaseUser user=aut.getCurrentUser();
            user.updatePassword(usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AtualizarSenha.this,"Senha Alterada com sucesso",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AtualizarSenha.this,TelaPrincipal.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(AtualizarSenha.this,"Erro ao cadastrar a nova senha",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            Toast.makeText(AtualizarSenha.this,"Sua senha está incorreta",Toast.LENGTH_LONG).show();
        }
    }
}
