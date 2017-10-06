package com.ahmadrosid.dompetku;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ahmadrosid.dompetku.data.Ballance;

import io.realm.Realm;

/**
 * Created by ocittwo on 1/27/17.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */
public class EditBalance extends BottomSheetDialogFragment {

    private EditText input_ballance;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_balance, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        input_ballance = (EditText) view.findViewById(R.id.input_ballance);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        long count = realm.where(Ballance.class).count();
        if (count > 0){
            Ballance ballance = realm.where(Ballance.class).findFirst();
            input_ballance.setText("" + ballance.getAmount());
        }else{
            Ballance ballance = realm.createObject(Ballance.class);
            ballance.setId(System.currentTimeMillis());
            realm.copyFromRealm(ballance);
        }
        realm.commitTransaction();
        realm.close();

        view.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                dismiss();
            }
        });

        view.findViewById(R.id.img_done).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                done();
            }
        });
    }

    public void done(){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Ballance ballance = realm.where(Ballance.class).findFirst();
        ballance.setAmount(Integer.parseInt(input_ballance.getText().toString()));
        realm.commitTransaction();
        realm.close();
        dismiss();
    }
}
