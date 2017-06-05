package br.org.multimidia.multimidiavoz.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import br.org.multimidia.multimidiavoz.R;

/**
 * Created by jheimesilveira on 27/02/2016.
 */
public class DialogLigarContato extends Dialog {

    private CallBack callBack;
    private Button btnPositive;
    private Button btnNegative;
    private EditText etFistName;

    /**
     * Construtor
     *
     * @param context
     */
    public DialogLigarContato(Context context) {

        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_full_name);
        initR();
    }


    private void initBtnNegative() {
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initBtnPositive() {
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callBack.call(etFistName.getText().toString());
            }
        });
    }

    public void show(CallBack callBack) {
        this.callBack = callBack;
        initBtnPositive();
        initBtnNegative();
        super.show();
    }



    private void initR() {
        etFistName = (EditText) findViewById(R.id.etFistName);
        btnNegative = (Button) findViewById(R.id.btn_negative);
        btnPositive = (Button) findViewById(R.id.btn_positive);
    }

    public interface CallBack {
        void call(String profile);
    }
}
