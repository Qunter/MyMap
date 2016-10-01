package com.yufa.mymap.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yufa.mymap.R;
import com.yufa.mymap.Util.JudgeTool;

/**
 * Created by luyufa on 2016/9/29.
 */
public class SendActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void initViews() {
        super.initViews();
        setContentView(R.layout.activity_contact);
        final EditText email = (EditText)findViewById(R.id.contact_email);
        final TextInputLayout emailLayout = (TextInputLayout)findViewById(R.id.contact_emaillayout);
        final EditText data = (EditText)findViewById(R.id.contact_data);
        Button send = (Button)findViewById(R.id.contact_send);
        emailLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!new JudgeTool().isEmail(s.toString())) {
                    emailLayout.setError("这不是一个合法的邮箱地址");
                } else {
                    emailLayout.setErrorEnabled(false);
                }
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datas = data.getText().toString();
                Intent data = new Intent(Intent.ACTION_SENDTO);
                data.setData(Uri.parse("mailto:way.ping.nullyufa@gmail.com"));
                data.putExtra(Intent.EXTRA_SUBJECT, email.getText().toString());
                data.putExtra(Intent.EXTRA_TEXT, datas);
                startActivity(data);
            }
        });
    }
}
