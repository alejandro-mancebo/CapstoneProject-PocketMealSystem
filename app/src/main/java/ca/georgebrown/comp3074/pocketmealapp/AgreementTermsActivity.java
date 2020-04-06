package ca.georgebrown.comp3074.pocketmealapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.georgebrown.comp3074.pocketmealapp.ui.about.AboutFragment;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AgreementTermsActivity extends AppCompatActivity {


    // UI Navigation Code Variables
    private TextView txtAgreementTerms;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement_terms);

        txtAgreementTerms =(TextView) findViewById(R.id.textAgreementTerms);
        txtAgreementTerms.setText(Html.fromHtml(getString(R.string.htmlAgreementTerms)));
        btnBack =  findViewById(R.id.buttonBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });


    }
}
