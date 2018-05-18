package com.example.tcc.tcc;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends Activity implements GoogleApiClient.OnConnectionFailedListener {
    private Button c_email;
    private GoogleApiClient googleApiClient;
    private SignInButton c_google;
    public static final int SIGN_IN_CODE=777;
    private Button dados, c_facebook;
    private LoginButton loginButton;

    private CallbackManager mCallbackManager;

    private final FacebookCallback<LoginResult> mFacebookLoginCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            Intent intent = new Intent(MainActivity.this, TelaInicialActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onCancel() {}

        @Override
        public void onError(FacebookException error) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**intent - enviar uma ação, new é para instanciar uma classe e o this
         * se referente que estamos na classe atual */
        //c_email irá chamar a tela para entrar com email e senha
        c_email = (Button) findViewById(R.id.conta_email);
        c_email.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
// Configuração para solicitar ID, email do usuário, perfil básico que estão incluidos no DEFAULT_SIGN_IN
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestEmail()
                .build();
//irá fazer a conexão com a API.
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        //c_google irá chamar a tela bem vindo que será logado com a conta do google
        c_google = (SignInButton) findViewById(R.id.entrar_google);
        c_google.setSize(SignInButton.SIZE_WIDE);
        c_google.setColorScheme(SignInButton.COLOR_LIGHT);

        c_google.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_CODE);
            }
        });

        //------------Cadastro-----------

        dados = (Button) findViewById(R.id.cadastro);
        dados.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CadastroActivity.class));
            }
        });

        ///facebook///
        mCallbackManager = CallbackManager.Factory.create();

        /* Facebook login button */
        LoginButton mLoginButton = (LoginButton) findViewById(R.id.entrar_facebook);
        mLoginButton.registerCallback(mCallbackManager, mFacebookLoginCallback);


        //Manter logado
        /*SharedPreferences prefs = getSharedPreferences("meu_arquivo_de_preferencias", 0);
        boolean jaLogou = prefs.getBoolean("estaLogado", false);

        if(jaLogou) {
            // chama a tela inicial
            startActivity(new Intent(MainActivity.this, TelaInicialActivity.class));
        }
        //else {
            // chama a tela de login
            //startActivity(new Intent(MainActivity.this,LoginActivity.class));
        //}*/


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult (result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            goMainScreen();
        }else {
            Toast.makeText(this, "Não pode iniciar sessão", Toast.LENGTH_SHORT).show();
        }
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, GoogleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public Object getActivity() {
        return 0 ;
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // não chame o super desse método, assim a tecla voltar fica inutil nesta tela
    }

}
