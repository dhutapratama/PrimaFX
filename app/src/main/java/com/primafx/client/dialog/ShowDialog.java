package com.primafx.client.dialog;

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

    public static Dialog rebateTransfer(Context context, String message) {
        final Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_rebate_transfer);
        dialog.setCancelable(true);

        TextView editMessage = (TextView)dialog.findViewById(R.id.textMessage);
        editMessage.setText(message);

        dialog.show();
        return dialog;
    }

    public static Dialog rebateWithdrawal(Context context, String message) {
        final Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_rebate_withdrawal);
        dialog.setCancelable(true);

        TextView editMessage = (TextView)dialog.findViewById(R.id.textMessage);
        editMessage.setText(message);

        dialog.show();
        return dialog;
    }

    public static Dialog loading(Context context) {
        final Dialog dialog = new Dialog(context, R.style.Loading);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.setCancelable(false);
        return dialog;
    }

    public static Dialog deposit(Context context, String akun, String usd, String kurs, String rupiah) {
        final Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_deposit_inquiry);
        dialog.setCancelable(true);

        TextView textAccountNumber = (TextView) dialog.findViewById(R.id.textAccountNumber);
        textAccountNumber.setText(akun);

        TextView textUsd = (TextView) dialog.findViewById(R.id.textUsd);
        textUsd.setText(usd);

        TextView textKurs = (TextView) dialog.findViewById(R.id.textKurs);
        textKurs.setText(kurs);

        TextView textRupiah = (TextView) dialog.findViewById(R.id.textRupiah);
        textRupiah.setText(rupiah);

        Button button = (Button) dialog.findViewById(R.id.buttonLanjutkan);


        dialog.show();
        return dialog;
    }

    public static Dialog detailRebate(Context context,
                                      String rebate_id,
                                      String ticket,
                                      String tanggal,
                                      String detail,
                                      String account,
                                      String jumlah,
                                      String waktu_insta,
                                      String info) {
        Dialog dialog = new Dialog(context, R.style.IMMEDialog);
        dialog.setContentView(R.layout.dialog_detail_rebate);
        dialog.setCancelable(true);

        TextView textRebateID = (TextView)dialog.findViewById(R.id.textRebateID);
        TextView textTicket = (TextView)dialog.findViewById(R.id.textTicket);
        TextView textTanggal = (TextView)dialog.findViewById(R.id.textTanggal);
        TextView textDetail = (TextView)dialog.findViewById(R.id.textDetail);
        TextView textAccount = (TextView)dialog.findViewById(R.id.textAccount);
        TextView textJumlah = (TextView)dialog.findViewById(R.id.textJumlah);
        TextView textWaktuInsta = (TextView)dialog.findViewById(R.id.textInstaTime);
        TextView textInfo = (TextView)dialog.findViewById(R.id.textInfo);

        textRebateID.setText("ID Rebate : "+rebate_id);
        textTicket.setText("TICKET : "+ticket);
        textTanggal.setText("Tanggal : "+tanggal);
        textDetail.setText("Detail : "+detail);
        textAccount.setText("Account : "+account);
        textJumlah.setText("USD : "+jumlah);
        textWaktuInsta.setText("InstaForex Time : "+waktu_insta);
        textInfo.setText("Info : "+info);

        dialog.show();
        return dialog;
    }

}
