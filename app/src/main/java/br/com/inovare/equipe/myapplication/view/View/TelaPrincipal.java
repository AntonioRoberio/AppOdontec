package br.com.inovare.equipe.myapplication.view.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import br.com.inovare.equipe.myapplication.R;
import br.com.inovare.equipe.myapplication.view.Model.ConfiguracaoFirebase;
import br.com.inovare.equipe.myapplication.view.Model.Usuario;

public class TelaPrincipal extends AppCompatActivity {
    private Button sair;
    private Button deletarConta;
    private FirebaseAuth sairSistema;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        sair=(Button) findViewById(R.id.sair);
        deletarConta=(Button) findViewById(R.id.apagarConta);
        sairSistema= ConfiguracaoFirebase.autenticarDados();
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sairSistema.signOut();
                Intent intent= new Intent(TelaPrincipal.this,MainActivity_Login.class);
                startActivity(intent);
                finish();
            }
        });
        deletarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apagar();
            }
        });
    }

    public void apagar(){

        Usuario us=new Usuario();
        us.deletarConta();
        Intent intent= new Intent(TelaPrincipal.this,MainActivity_Login.class);
        startActivity(intent);
        finish();
    }

}