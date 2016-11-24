package com.primafx.client.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.primafx.client.R;
import com.primafx.client.database.GData;
import com.primafx.client.dialog.ShowDialog;
import com.primafx.client.model.SquareFragment;
import com.primafx.client.retrofit.ParseAccountDetail;
import com.primafx.client.retrofit.ParseAddAccount;
import com.primafx.client.retrofit.ParseCalendar;
import com.primafx.client.retrofit.ParseCallMeBack;
import com.primafx.client.retrofit.ParseCheckRebate;
import com.primafx.client.retrofit.ParseHistory;
import com.primafx.client.retrofit.RequestLibrary;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestingApiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_api);

        retrofitDetailAccount(
                "3652724",
                "88f200b77cccee4a6e95c383d33e0f22");

        //DatabaseSQL.removeAllAccount(this);
        //DatabaseSQL.addAccount(this, "123456", "test123");
        //DatabaseSQL.deleteAccount(this, "123456");

        //List<String> my_accounts = DatabaseSQL.getAccountData(this);

        //for(int i = 0; i<my_accounts.size(); i++){
        //    Log.i("Account", my_accounts.get(i));
        //}
    }

    private void retrofitDetailAccount(String akun, String login_hash ) {
        final Dialog loading = new ShowDialog().loading(this);
        loading.show();

        String host = GData.API_ADDRESS;

        //ParseAddAccount jsonSend = new ParseAddAccount("3652724",
        //        "e1b3b0121ba60319ab4b6ec5fd9e52e0f1080de9882e02526b9e572b8939d930bb0f707b2818f4f909e2bc8a3b7a5177c964a3bdc7c376cd4b1ef97573b5ba30",
        //        "123456a");

        ParseAccountDetail jsonSend = new ParseAccountDetail(akun, login_hash);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create()).build();
        RequestLibrary requestLibrary = retrofit.create(RequestLibrary.class);
        Call<ParseAccountDetail> callData = requestLibrary.accountDetail(jsonSend);

        callData.enqueue(new Callback<ParseAccountDetail>() {
            @Override
            public void onResponse(Call<ParseAccountDetail> call, Response<ParseAccountDetail> response) {
                loading.dismiss();
                if (response.isSuccessful()) {
                    ParseAccountDetail response_body = response.body();
                    if (response_body.getError()) {
                        new ShowDialog().error(TestingApiActivity.this, response_body.getMessage()).setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });
                    } else {
                        setData(response_body);
                    }
                } else {
                    Log.e("Server Problem", "Server Responding but error callback : " + response.message());
                    //new ShowDialog().error(TestingApiActivity.this, response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ParseAccountDetail> call, Throwable t) {
                loading.dismiss();
                Log.e("Network", "ParseCallMeBack" + t.getMessage());
                new ShowDialog().error(TestingApiActivity.this, "Tidak dapat terhubung, terjadi masalah jaringan.");
            }
        });
    }

    public void setData(ParseAccountDetail data) {
        Log.i("Sep ", "Akun -------------------------------------------");
        Log.i("tgl_input", data.getData().getdAkun().getTgl_input());
        Log.i("Date_Register", data.getData().getdAkun().getDate_Register());
        Log.i("nama", data.getData().getdAkun().getNama());
        Log.i("phone", data.getData().getdAkun().getPhone());
        Log.i("email", data.getData().getdAkun().getEmail());
        Log.i("kode_agen", data.getData().getdAkun().getKode_agen());
        Log.i("pay_to", data.getData().getdAkun().getPay_to());
        Log.i("pay_number", data.getData().getdAkun().getPay_number());
        Log.i("pay_name", data.getData().getdAkun().getPay_name());
        Log.i("Address", data.getData().getdAkun().getAddress());
        Log.i("City", data.getData().getdAkun().getCity());
        Log.i("State", data.getData().getdAkun().getState());
        Log.i("Country", data.getData().getdAkun().getCountry());
        Log.i("Currency", data.getData().getdAkun().getCurrency());
        Log.i("sisa_rebate", data.getData().getdAkun().getSisa_rebate());
        Log.i("Sep ", "Akun Rebate -------------------------------------------");
        if (data.getData().getdAkunRbt() != null) {
            Log.i("tgl_input", data.getData().getdAkunRbt().getTgl_input());
            Log.i("pay_to", data.getData().getdAkunRbt().getPay_to());
            Log.i("pay_number", data.getData().getdAkunRbt().getPay_number());
            Log.i("pay_name", data.getData().getdAkunRbt().getPay_name());
            Log.i("email", data.getData().getdAkunRbt().getEmail());
            Log.i("phone", data.getData().getdAkunRbt().getPhone());
        }

        TextView textAccount = (TextView)findViewById(R.id.textAccount);
        TextView textName = (TextView)findViewById(R.id.textName);
        TextView textPhone = (TextView)findViewById(R.id.textPhone);
        TextView textEmail = (TextView)findViewById(R.id.textEmail);
        TextView textBank = (TextView)findViewById(R.id.textBank);
        TextView textRekening = (TextView)findViewById(R.id.textRekening);
        TextView textAtasNama = (TextView)findViewById(R.id.textAtasNama);
        TextView textRegistrationDate = (TextView)findViewById(R.id.textRegistrationDate);
        TextView textValidationDate = (TextView)findViewById(R.id.textValidationDate);
        TextView textAgen = (TextView)findViewById(R.id.textAgen);
        TextView textProvinsi = (TextView)findViewById(R.id.textProvinsi);
        TextView textCity = (TextView)findViewById(R.id.textCity);
        TextView textAddress = (TextView)findViewById(R.id.textAddress);

        TextView textRbtBank = (TextView)findViewById(R.id.textRbtBank);
        TextView textRbtRekening = (TextView)findViewById(R.id.textRbtRekening);
        TextView textRbtAtasNama = (TextView)findViewById(R.id.textRbtAtasNama);
        TextView textRbtEmail = (TextView)findViewById(R.id.textRbtEmail);
        TextView textRbtPhone = (TextView)findViewById(R.id.textRbtPhone);

        TextView textLabelRbtBank = (TextView)findViewById(R.id.textRbtView31);
        TextView textLabelRbtRekening = (TextView)findViewById(R.id.textRbtView34);
        TextView textLabelRbtAtasNama = (TextView)findViewById(R.id.textRbtView46);
        TextView textLabelRbtEmail = (TextView)findViewById(R.id.textLabelEmail);
        TextView textLabelRbtPhone = (TextView)findViewById(R.id.textRbtView24);

        Button buttonKlaimRebate = (Button)findViewById(R.id.buttonKlaimRebate);
        TextView textWarningRebate = (TextView)findViewById(R.id.textWarningRebate);

        textAccount.setText(GData.CURRENT_ACCOUNT + " / " + data.getData().getdAkun().getCurrency() + " / " + data.getData().getdAkun().getServer());
        textName.setText(data.getData().getdAkun().getNama());
        textPhone.setText(data.getData().getdAkun().getPhone());
        textEmail.setText(data.getData().getdAkun().getEmail());
        textBank.setText(data.getData().getdAkun().getPay_to());
        textRekening.setText(data.getData().getdAkun().getPay_number());
        textAtasNama.setText(data.getData().getdAkun().getPay_name());
        textRegistrationDate.setText(data.getData().getdAkun().getDate_Register());
        textValidationDate.setText(data.getData().getdAkun().getTgl_input());
        textAgen.setText(data.getData().getdAkun().getKode_agen());
        textProvinsi.setText(data.getData().getdAkun().getState());
        textCity.setText(data.getData().getdAkun().getCity());
        textAddress.setText(data.getData().getdAkun().getAddress());

        if (data.getData().getdAkunRbt() == null) {
            textRbtBank.setVisibility(View.GONE);
            textRbtRekening.setVisibility(View.GONE);
            textRbtAtasNama.setVisibility(View.GONE);
            textRbtEmail.setVisibility(View.GONE);
            textRbtPhone.setVisibility(View.GONE);
            textLabelRbtBank.setVisibility(View.GONE);
            textLabelRbtRekening.setVisibility(View.GONE);
            textLabelRbtAtasNama.setVisibility(View.GONE);
            textLabelRbtEmail.setVisibility(View.GONE);
            textLabelRbtPhone.setVisibility(View.GONE);

            buttonKlaimRebate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TestingApiActivity.this, ClaimRebateActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            buttonKlaimRebate.setVisibility(View.GONE);
            textWarningRebate.setVisibility(View.GONE);

            textRbtBank.setText(data.getData().getdAkunRbt().getPay_to());
            textRbtRekening.setText(data.getData().getdAkunRbt().getPay_number());
            textRbtAtasNama.setText(data.getData().getdAkunRbt().getPay_name());
            textRbtEmail.setText(data.getData().getdAkunRbt().getEmail());
            textRbtPhone.setText(data.getData().getdAkunRbt().getPhone());
        }




        /*for (int i = 0; i < data.getData().getDetail().size(); i++) {
            Log.i("id", data.getData().getDetail().get(i).getId());
            Log.i("ticket", data.getData().getDetail().get(i).getTicket());
            Log.i("Date", data.getData().getDetail().get(i).getDate());
            Log.i("InputDateTime", data.getData().getDetail().get(i).getInputDateTime());
            Log.i("InstaDateTime", data.getData().getDetail().get(i).getInstaDateTime());

            Log.i("akun", data.getData().getDetail().get(i).getAkun());
            Log.i("profit", data.getData().getDetail().get(i).getProfit());
            Log.i("detail", data.getData().getDetail().get(i).getDetail());
            Log.i("info", data.getData().getDetail().get(i).getInfo());

            Log.i("Sep ", "-------------------------------------------");
        }


        Log.i("nama", data.getData().getNama());
        Log.i("phone", data.getData().getPhone());
        Log.i("email", data.getData().getEmail());
        Log.i("support", data.getData().getSupport());
        Log.i("time", data.getData().getTime());
        Log.i("date", data.getData().getDate());
*/

    }

}