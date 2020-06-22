package com.example.assignment.view.activity;

import android.app.Service;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assignment.R;

import com.example.assignment.database.ModuleEdited;
import com.example.assignment.module.Modules;
import com.example.assignment.presenter.ModulePresenter;
import com.example.assignment.utilities.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ModuleDetailsActivity extends AppCompatActivity implements
        ModulePresenter.moduleListListener {

    private Realm realm;
    private String text;
    private int module_id;

    @BindView(R.id.edt_description)
    EditText edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_details);

        ButterKnife.bind(this);
        Realm.init(this);
        realm = Realm.getDefaultInstance();
        initialization();
    }

    private void initialization() {

        if (getIntent() != null) {

            ModuleEdited edited = realm.where(ModuleEdited.class).equalTo("id", module_id).findFirst();
            if ( edited == null){

                int id = getIntent().getIntExtra("id", 0);
                Log.d("TAG", "initialization: " + id);
                ModulePresenter presenter = new ModulePresenter(this, this);
                presenter.getModuleListDetails(id);
            }else {
                ModuleEdited setting = realm.where(ModuleEdited.class).findFirst();
                realm.beginTransaction();
               // edtDescription.setText(setting.getText());
                Log.d("TAG", "getSettingInstance: "+setting.getText());
                realm.commitTransaction();
            }
        }
    }


    @OnClick({R.id.btn_edit, R.id.btn_save})
    void onClick(View view) {
        switch (view.getId()){

            case R.id.btn_edit:
                edtDescription.setCursorVisible(true);
                edtDescription.setFocusableInTouchMode(true);
                edtDescription.requestFocus();
                edtDescription.setSelection(edtDescription.getText().length());
                InputMethodManager imm = (InputMethodManager)this.getSystemService(Service.INPUT_METHOD_SERVICE);
                imm.showSoftInput( edtDescription, 0);
                break;
            case R.id.btn_save:
                realm.beginTransaction();
                if (isValidate())
                    if (realm.where(ModuleEdited.class).equalTo("id", module_id).findFirst()!=null) {
                        ModuleEdited edited = new ModuleEdited();
                        edited.setId(module_id);
                        edited.setText(edtDescription.getText().toString());
                        realm.insertOrUpdate(edited);
                        Log.d("TAG", "onClick: added");

                    }else {
                        ModuleEdited moduleEdited = realm.createObject(ModuleEdited.class);
                        moduleEdited.setId(module_id);
                        moduleEdited.setText(edtDescription.getText().toString());
                        Utils.showAlert(this, getString(R.string.save_db_msg));

                    }
                realm.commitTransaction();
                break;
        }
    }

    private boolean isValidate() {
        //Check empty fields
        if (edtDescription.getText().toString().isEmpty()){
            edtDescription.setError("Field should not be empty");
            return false;
        }

        return true;
    }

    @Override
    public void success(List<Modules> modulesList) {
        //No use here
    }

    @Override
    public void error(String tag) {
        Utils.showAlert(this, tag);
    }

    @Override
    public void failure(String tag) {
        Utils.showAlert(this, tag);
    }

    @Override
    public void success(int id, String modules) {
        Log.d("TAG", "success: " + modules);
        text = modules;
        module_id = id;
        edtDescription.setText(modules);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
