package cm.deone.mesextensions;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import cm.deone.mesextensions.models.Agent;
import cm.deone.mesextensions.models.FirebaseDatabaseHelper;

public class DetailsAgentActivity extends AppCompatActivity {

    private EditText mLogin;
    private Spinner mspDomain;
    private EditText mExtension;
    private EditText mNom;
    private EditText mPassword;
    private Spinner mspWeb;
    private Spinner mspCampagne;

    private Button mUpdate_btn;
    private Button mDelete_btn;
    private Button mBack_btn;

    private String key;
    private String campagne;
    private String domain;
    private long extension;
    private String login;
    private String nom;
    private String password;
    private String web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_agent);

        key = getIntent().getStringExtra("key");
        campagne = getIntent().getStringExtra("campagne");
        domain = getIntent().getStringExtra("domain");
        extension = getIntent().getLongExtra("extension", 0);
        login = getIntent().getStringExtra("login");
        nom = getIntent().getStringExtra("nom");
        password = getIntent().getStringExtra("password");
        web = getIntent().getStringExtra("web");

        mLogin = (EditText)findViewById(R.id.tvLogin);
        mLogin.setText(login);

        mspDomain = (Spinner) findViewById(R.id.spDomain);
        mspDomain.setSelection(getIndex_SpinnerItem(mspDomain, domain));
        mExtension = (EditText)findViewById(R.id.tvExtension);
        mExtension.setText("" + extension);
        mNom = (EditText)findViewById(R.id.tvNomAgent);
        mNom.setText(nom);
        mPassword = (EditText)findViewById(R.id.tvPassword);
        mPassword.setText(password);
        mspWeb = (Spinner) findViewById(R.id.spWeb);
        mspWeb.setSelection(getIndex_SpinnerItem(mspWeb, web));

        mspCampagne = (Spinner) findViewById(R.id.spCampagne);
        mspCampagne.setSelection(getIndex_SpinnerItem(mspCampagne, campagne));

        mUpdate_btn = (Button) findViewById(R.id.update_btn);
        mDelete_btn = (Button)findViewById(R.id.delete_btn);
        mBack_btn = (Button)findViewById(R.id.back_btn);

        mUpdate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Agent agent = new Agent();
                agent.setLogin(mLogin.getText().toString());
                agent.setDomain(mspDomain.getSelectedItem().toString());
                agent.setExtension(Long.parseLong(mExtension.getText().toString()));
                agent.setNom(mNom.getText().toString());
                agent.setPassword(mPassword.getText().toString());
                agent.setWeb(mspWeb.getSelectedItem().toString());
                agent.setCampagne(mspCampagne.getSelectedItem().toString());
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                agent.setUpdatedby(user.getEmail());

                new FirebaseDatabaseHelper().updateAgent(key, agent, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoader(List<Agent> agents, List<String> keys) {

                    }

                    @Override
                    public void DataIsInsered() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(DetailsAgentActivity.this, "The agent record has " +
                                "been updated successfully", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }

                    @Override
                    public void DataIsSearched(List<Agent> agents, List<String> keys, String search) {

                    }
                });
            }
        });

        mDelete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deleteAgent(key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoader(List<Agent> agents, List<String> keys) {

                    }

                    @Override
                    public void DataIsInsered() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(DetailsAgentActivity.this, "The agent record has " +
                                "been deleted successfully", Toast.LENGTH_LONG).show();
                        finish(); return;
                    }

                    @Override
                    public void DataIsSearched(List<Agent> agents, List<String> keys, String search) {

                    }
                });
            }
        });

        mBack_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); return;
            }
        });
    }

    private int getIndex_SpinnerItem(Spinner spinner, String item){
        int index = 0;
        for(int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).equals(item)){
                index = i;
                break;
            }
        }
        return index;
    }
}
