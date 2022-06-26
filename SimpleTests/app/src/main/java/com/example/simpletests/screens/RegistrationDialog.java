package com.example.simpletests.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class RegistrationDialog extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Правила безопасности данных")
                .setMessage("1. Логин состоит из буквенно-цифровых символов, строчных и прописных букв. Может содержать точки, подчёркивания, дефисы. Длина логина от 5 до 20 символов\n" +
                         "2. Пароль должен содержать хотя бы одну цифру, одну строчную и прописную букву. Один спец.символ: ! @ # & ( ) и содержать не менее 8 символов.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
