package com.onurmert.qrcodescanner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.onurmert.qrcodescanner.databinding.ActivityQrScanBinding;

public class QrScanActivity extends AppCompatActivity {

    private ActivityQrScanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //we do initialize viewBinding
        binding = ActivityQrScanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(this,
                "Press button for qr code read",
                Toast.LENGTH_LONG)
                .show();

        //By clicking the button, we ask for permission to open the Camera.
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanCode();
            }
        });
    }
    //Camera opens according to permission response
    private void scanCode(){
        ScanOptions scanOptions =  new ScanOptions();
        scanOptions.setBeepEnabled(true);//beep when read
        scanOptions.setOrientationLocked(true);//will work in portrait mode
        scanOptions.setCaptureActivity(CaptureQR.class);
        qrLauncher.launch(scanOptions);
    }

    ActivityResultLauncher<ScanOptions> qrLauncher = registerForActivityResult(
            new ScanContract(), result -> {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage(result.getContents());

                //Clicking OK opens the link.
                dialog.setPositiveButton("Okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        golink(result.getContents());
                    }
                });
                dialog.show();
            });

    //Opens the link.
    private void golink(String link){
        Intent intent =  new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, link);
        startActivity(intent);
    }
}