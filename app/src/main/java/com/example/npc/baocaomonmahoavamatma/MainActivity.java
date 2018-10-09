package com.example.npc.baocaomonmahoavamatma;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.npc.baocaomonmahoavamatma.model.PlayfairEncryption;
import com.example.npc.baocaomonmahoavamatma.model.VigenereEncryption;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TabHost tabHost;
    private ClipboardManager clipboard;
    private ClipData clip;

    private EditText txtVPlainText,txtVkey,txtVResult;

    private EditText txtPPlainText,txtPKey,txtPResult;
    private TextView lblKey;
    private ArrayList<Character> arrKey;
    private ArrayAdapter<Character> adapterKey;
    private GridView gridKeyPlayfair;
    private PlayfairEncryption pe;

    private TextView txtHPlainText,txtHKey,txtHResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();
        addControls();
        addEvents();
    }

    private void addEvents() {

    }

    private void addControls() {
        clipboard = (ClipboardManager) getSystemService(MainActivity.CLIPBOARD_SERVICE);

        txtVPlainText = findViewById(R.id.txtVPlainText);
        txtVkey = findViewById(R.id.txtVKey);
        txtVResult = findViewById(R.id.txtVResult);

        txtPKey = findViewById(R.id.txtPKey);
        txtPPlainText = findViewById(R.id.txtPPlainText);
        txtPResult= findViewById(R.id.txtPResult);
        lblKey = findViewById(R.id.lblKeyTable);
        gridKeyPlayfair = findViewById(R.id.gridKey);

        arrKey = new ArrayList<>();
        adapterKey = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,arrKey);
        gridKeyPlayfair.setAdapter(adapterKey);
    }
    private boolean checkV() {
        if(txtVPlainText.getText().length()<=0){
            Toast.makeText(MainActivity.this,"Enter Plain Text, Please",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtVkey.getText().length()<=0){
            Toast.makeText(MainActivity.this,"Enter Key, Please",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean checkP(){
        if(txtPPlainText.getText().length()<=0){
            Toast.makeText(MainActivity.this,"Enter Plain Text, Please",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initTab() {

        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("tab1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("Hill");
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("tab2");
        tab2.setIndicator("Playfair");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("tab3");
        tab3.setIndicator("Vigenère");
        tab3.setContent(R.id.tab3);
        tabHost.addTab(tab3);
    }

    public void VEncrypt(View view) {
        if(checkV()){
            txtVResult.setText(VigenereEncryption.Encypt(txtVPlainText.getText().toString(),
                    txtVkey.getText().toString()));
        }
    }
    public void VDecrypt(View view) {
        if(checkV()){
            txtVResult.setText(VigenereEncryption.Decrypt(txtVPlainText.getText().toString(),
                    txtVkey.getText().toString()));
        }

    }
    public void VClear(View view) {
        txtVResult.setText("");
        txtVkey.setText("");
        txtVPlainText.setText("");
    }

    public void PEncrypt(View view) {
        if(checkP()){
            pe = new PlayfairEncryption(txtPKey.getText().toString());
            txtPResult.setText(pe.Encrypt(txtPPlainText.getText().toString()));
            char[][] temp = pe.getKey();
            showInvisableViewandGridView(temp);
        }
    }

    private void showInvisableViewandGridView(char [][] key) {
        lblKey.setVisibility(View.VISIBLE);
        gridKeyPlayfair.setVisibility(View.VISIBLE);
        arrKey.clear();
        for(int i = 0;i<key.length;i++ )
            for(int j=0;j<key[i].length;j++){
                arrKey.add(key[i][j]);
        }
        adapterKey.notifyDataSetChanged();

    }

    public void PDecrypt(View view) {
        if(checkP()){
            pe = new PlayfairEncryption(txtPKey.getText().toString());
            txtPResult.setText(pe.Decrypt(txtPPlainText.getText().toString()).toLowerCase());
            char[][] temp = pe.getKey();
            showInvisableViewandGridView(temp);
        }
    }

    public void PClear(View view) {
        txtPResult.setText("");
        txtPPlainText.setText("");
        txtPKey.setText("");
        lblKey.setVisibility(View.INVISIBLE);
        gridKeyPlayfair.setVisibility(View.VISIBLE);
        arrKey.clear();
        adapterKey.notifyDataSetChanged();
    }

    public void Pcopy(View view) {
        clip = ClipData.newPlainText("copy",txtPResult.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this,"Copied",Toast.LENGTH_SHORT).show();
    }

    public void Vcopy(View view) {
        clip = ClipData.newPlainText("copy",txtVResult.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this,"Copied",Toast.LENGTH_SHORT).show();
    }
}
