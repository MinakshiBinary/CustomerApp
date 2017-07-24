package com.binaryic.customerapp.fashionic.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.binaryic.customerapp.fashionic.R;
import com.binaryic.customerapp.fashionic.adapters.Adapter;
import com.binaryic.customerapp.fashionic.controller.PaymentController;
import com.binaryic.customerapp.fashionic.fragments.FragmentNetBanking;
import com.binaryic.customerapp.fashionic.fragments.FragmentPaymentCards;
import com.payu.india.Interfaces.PaymentRelatedDetailsListener;
import com.payu.india.Model.MerchantWebService;
import com.payu.india.Model.PaymentParams;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Model.PayuHashes;
import com.payu.india.Model.PayuResponse;
import com.payu.india.Model.PostData;
import com.payu.india.Payu.Payu;
import com.payu.india.Payu.PayuConstants;
import com.payu.india.Payu.PayuErrors;
import com.payu.india.PostParams.MerchantWebServicePostParams;
import com.payu.india.Tasks.GetPaymentRelatedDetailsTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import static com.binaryic.customerapp.fashionic.fragments.FragmentPayment.payment_Success;
import static com.binaryic.customerapp.fashionic.fragments.FragmentPayment.transaction_ID;

public class PaymentActivity extends AppCompatActivity implements PaymentController.CloseListener, PaymentRelatedDetailsListener {
    TextView tvHeader;
    TabLayout sliding_tabs;
    ViewPager viewpager;
    ProgressDialog progressDialog;
    String name = "";
    Double amount = 0.0;
    String emailID = "";
    String description = "";
    String phone_Number = "";
    public PayuConfig payuConfig;
    public PaymentParams mPaymentParams;
    public PayuHashes payuHashes;
    public PayuResponse mPayuResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_payment);
            getExtras();
            Init();
            Payu.setInstance(this);
        } catch (Exception ex) {
        }
    }

    private void getExtras() {
        name = getIntent().getStringExtra("name");
        amount = Double.parseDouble(getIntent().getStringExtra("amount"));
        Log.e("PaymentActivity", "amount==" + amount);
        description = getIntent().getStringExtra("description");
        emailID = getIntent().getStringExtra("emailID");
        phone_Number = getIntent().getStringExtra("phone_Number");
    }

    private void Init() {
        try {

            sliding_tabs = (TabLayout) findViewById(R.id.sliding_tabs);
            viewpager = (ViewPager) findViewById(R.id.viewpager);
            GetPayuHash();
            //SetFragment();
        } catch (Exception ex) {
        }
    }

    private void SetFragment() {
        try {
            if (viewpager != null) {
                Adapter adapter = new Adapter(getSupportFragmentManager());
                adapter.addFragment(new FragmentPaymentCards(), "DEBIT/CREDIT CARDS");
                adapter.addFragment(new FragmentNetBanking(), "Net Banking");
                viewpager.setAdapter(adapter);
            }
            sliding_tabs.setupWithViewPager(viewpager);
        } catch (Exception ex) {
        }
    }

    private void GetPayuHash() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
            PaymentController paymentController = new PaymentController();
            paymentController.setCloseListener(this);
            Random rand = new Random();
            String randomString = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
            String mTXNId = hashCal("SHA-256", randomString).substring(0, 20);
            paymentController.GetPaymentObject("tr6gBX", amount, description, name, emailID, mTXNId, phone_Number);
        } catch (Exception ex) {
        }
    }

    @Override
    public void Close(PayuHashes payuHashes, PaymentParams mPaymentParams) {
        progressDialog.dismiss();
        this.mPaymentParams = mPaymentParams;
        this.payuHashes = payuHashes;
        Log.e("PaymentActivity", "payuHashes ==" + payuHashes);
        //SetFragment();
        SetPaymentObjects();
    }

    public String hashCal(String type, String str) {
        byte[] hashSequence = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashSequence);
            byte messageDigest[] = algorithm.digest();

            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1)
                    hexString.append("0");
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException NSAE) {
        }
        return hexString.toString();
    }

    private void SetPaymentObjects() {
        try {
            payuConfig = new PayuConfig();
            payuConfig.setEnvironment(PayuConstants.PRODUCTION_ENV);
            MerchantWebService merchantWebService = new MerchantWebService();
            merchantWebService.setKey(mPaymentParams.getKey());
            merchantWebService.setCommand(PayuConstants.PAYMENT_RELATED_DETAILS_FOR_MOBILE_SDK);
            merchantWebService.setVar1(mPaymentParams.getUserCredentials() == null ? "default" : mPaymentParams.getUserCredentials());
            merchantWebService.setHash(payuHashes.getPaymentRelatedDetailsForMobileSdkHash());

            PostData postData = new MerchantWebServicePostParams(merchantWebService).getMerchantWebServicePostParams();
            if (postData.getCode() == PayuErrors.NO_ERROR) {
                // ok we got the post params, let make an api call to payu to fetch the payment related details
                payuConfig.setData(postData.getResult());

                // lets set the visibility of progress bar
                GetPaymentRelatedDetailsTask paymentRelatedDetailsForMobileSdkTask = new GetPaymentRelatedDetailsTask(this);
                paymentRelatedDetailsForMobileSdkTask.execute(payuConfig);
            } else {
                //Toast.makeText(this, postData.getResult(), Toast.LENGTH_LONG).show();
                // close the progress bar
            }

        } catch (Exception ex) {
        }
    }

    @Override
    public void onPaymentRelatedDetailsResponse(PayuResponse payuResponse) {
        mPayuResponse = payuResponse;
        //Cloase Progessbar
        if (payuResponse.isResponseAvailable() && payuResponse.getResponseStatus().getCode() == PayuErrors.NO_ERROR) { // ok we are good to go
            //Toast.makeText(this, payuResponse.getResponseStatus().getResult(), Toast.LENGTH_LONG).show();
            if (payuResponse.isStoredCardsAvailable()) {
            }
            if (payuResponse.isNetBanksAvailable()) { // okay we have net banks now.
            }
            if (payuResponse.isCashCardAvailable()) { // we have cash card too
            }
            if (payuResponse.isCreditCardAvailable() || payuResponse.isDebitCardAvailable()) {
            }
            if (payuResponse.isEmiAvailable()) {
            }
            if (payuResponse.isPaisaWalletAvailable() && payuResponse.getPaisaWallet().get(0).getBankCode().contains(PayuConstants.PAYUW)) {
            }
        } else {
            Toast.makeText(this, "Something went wrong : " + payuResponse.getResponseStatus().getResult(), Toast.LENGTH_LONG).show();
        }
        SetFragment();
        // no mater what response i get just show this button, so that we can go further.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //  Toast.makeText(this, "Patmenet activity resume", Toast.LENGTH_SHORT).show();

        Log.e("requestCode", "==" + requestCode);
        Log.e("resultCode", "==" + resultCode);
        if (resultCode == RESULT_OK) {
            Intent intent = new Intent();
            payment_Success = "payment_Success";
            intent.putExtra(getString(R.string.cb_payu_response), data);

            setResult(RESULT_OK, intent);
            Log.e("success", "" + data.getStringExtra(getString(R.string.cb_payu_response)));

            try {
                JSONObject jsonObject = new JSONObject(data.getStringExtra(getString(R.string.cb_payu_response)));
                transaction_ID = jsonObject.getString("txnid");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finish();
        } else if (resultCode == RESULT_CANCELED) {
            Intent intent = new Intent();
            payment_Success = "payment_Failed";

            intent.putExtra(getString(R.string.cb_payu_response), data);

            setResult(RESULT_CANCELED, intent);
            //  Log.e("failed", ""+data.getStringExtra(getString(R.string.cb_payu_response)));
            finish();
        }

        if (requestCode == PayuConstants.PAYU_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent();

                intent.putExtra(getString(R.string.cb_payu_response), data);

                setResult(RESULT_OK, intent);
                Log.e("result", "" + data.getStringExtra(getString(R.string.cb_payu_response)));
                finish();
            }
        }
    }
}
