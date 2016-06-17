package com.primafx.client.model;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.primafx.client.R;

public class ShowDialog {

    public static Dialog error(Context context, String message) {
        final Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_error);
        dialog.setCancelable(true);

        TextView editMessage = (TextView)dialog.findViewById(R.id.textMessage);
        editMessage.setText(message);

        Button btnOk = (Button) dialog.findViewById(R.id.buttonDismiss);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog success(Context context, String message) {
        final Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_success);
        dialog.setCancelable(true);

        TextView editMessage = (TextView)dialog.findViewById(R.id.textMessage);
        editMessage.setText(message);

        Button btnOk = (Button) dialog.findViewById(R.id.buttonDismiss);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;
    }

    public static Dialog progress(Context context) {
        final Dialog dialog = new Dialog(context, R.style.IMMEProgress);
        dialog.setContentView(R.layout.dialog_progress_bar);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }

    public static Dialog rebate(Context context, String message) {
        final Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_rebate_destination);
        dialog.setCancelable(true);

        TextView editMessage = (TextView)dialog.findViewById(R.id.textMessage);
        editMessage.setText(message);

        dialog.show();
        return dialog;
    }

}
