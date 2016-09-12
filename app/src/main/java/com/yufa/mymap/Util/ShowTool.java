package com.yufa.mymap.Util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yufa.mymap.Entity.DataString;
import com.yufa.mymap.R;

/**
 * Created by luyufa on 2016/9/7.
 */
public class ShowTool {

    public void showToast(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
    public  void showSnackbar(View view,String str){
        Snackbar.make(view, str, Snackbar.LENGTH_SHORT).show();
    }
    public  void showDialog(Context context,String str){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(str);
    }
    public Boolean showDialogFromAttrs(Context context,String str,DataString dataString){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.item_dialog,null);
        builder.setView(layout);
        builder.setMessage(str);
        final TextInputLayout textInputLayout1 = (TextInputLayout)layout.findViewById(R.id.textInputLayout1);
        final TextInputLayout textInputLayout2 = (TextInputLayout)layout.findViewById(R.id.textInputLayout2);
        textInputLayout1.setHint(dataString.getStr1());
        textInputLayout2.setHint(dataString.getStr2());
        textInputLayout1.setHintEnabled(true);
        textInputLayout2.setErrorEnabled(true);
        if(dataString.getPhone())
            textInputLayout1.getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    JudgeTool judgeTool = new JudgeTool();
                    if (judgeTool.isPhoneNumber(s.toString())) {
                        textInputLayout1.setErrorEnabled(false);
                    } else {
                        textInputLayout1.setError("您输入的格式不正确！");
                    }
                }
            });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (textInputLayout1.getEditText().equals(textInputLayout2.getEditText())) {

                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
        return false;
    }
}
