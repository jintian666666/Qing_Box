package cn.gdust.qing_box.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import cn.gdust.qing_box.RegisterActivity;
import cn.gdust.qing_box.R;

public class MeFragment extends Fragment {

    EditText et_acc;
    TextInputLayout textInputLayout;
    MaterialButton bt_register,bt_login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_me, container,false);
        et_acc = view.findViewById(R.id.input_acc);
        textInputLayout = view.findViewById(R.id.til);
        bt_register = view.findViewById(R.id.bt_register);
        bt_login = view.findViewById(R.id.bt_login);

        et_acc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (textInputLayout.getEditText().getText().toString().length() > 3){
                    textInputLayout.setError("长度不能大于3");
                }else {
                    textInputLayout.setError(null);
                }
            }
        });

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_container,new AccountFragment(),null)
                        .addToBackStack(null).commit();
            }
        });

        return view;
    }
}