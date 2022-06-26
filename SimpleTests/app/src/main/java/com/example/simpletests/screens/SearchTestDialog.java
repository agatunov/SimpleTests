package com.example.simpletests.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.simpletests.R;

public class SearchTestDialog extends AppCompatDialogFragment {
    private EditText titleThemeET;
    private SearchTestDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_test_dialog, null);
        builder.setView(view)
                .setTitle("Поиск теста")
                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Искать", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String code = titleThemeET.getText().toString();
                        listener.applyText(code);
                    }
                });
        titleThemeET = view.findViewById(R.id.titleTestEditText);
        return  builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            listener = (SearchTestDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must");
        }
    }

    public interface  SearchTestDialogListener{
        void applyText(String code);
    }
}
