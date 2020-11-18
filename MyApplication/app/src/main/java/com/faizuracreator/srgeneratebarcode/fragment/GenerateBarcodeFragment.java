package com.faizuracreator.srgeneratebarcode.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.faizuracreator.srgeneratebarcode.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class GenerateBarcodeFragment extends Fragment {
    private EditText mInputText;
    private ImageView mImageView;
    private FloatingActionButton mSave;
    private Activity mActivity;
    private Bitmap generateBitmap;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_generate_barcode, container, false);
        mActivity = getActivity();

        mInputText = view.findViewById(R.id.inputText);
        mImageView = view.findViewById(R.id.outputBitmap);
        mSave = view.findViewById(R.id.save);

        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    mImageView.setImageResource(R.drawable.ic_placeholder);
                }else{
                    generateBarcode(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputTeks = mInputText.getText().toString();

                boolean isEmptyFields = false;

                if (TextUtils.isEmpty(inputTeks)){
                    isEmptyFields = true;
                    mInputText.setError("Field ini tidak boleh kosong");
                }

                if (!isEmptyFields){
                    saveImage(generateBitmap);
                }

            }
        });


        return view;
    }

    private void saveImage(Bitmap generateBitmap){
        FileOutputStream outputStream = null;
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "QRCodeBarcode");
        if (!file.exists()){
            file.mkdirs();
        }
        if (fileName.contains("/")){
            fileName = fileName.replace("/", "\\");
        }
        String filePath = (file.getAbsolutePath() + "/" + fileName + ".png");
        try {
            outputStream = new FileOutputStream(filePath);
            generateBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Toast.makeText(mActivity, "File saved at\n" + filePath, Toast.LENGTH_SHORT).show();
    }

    private void generateBarcode(String s){
        fileName = s;
        MultiFormatWriter writer = new MultiFormatWriter();
        String finalData = Uri.encode(s);

        BitMatrix bm = null;
        try {
            bm = writer.encode(finalData, BarcodeFormat.CODE_128, 1080, 1);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int bmWidth = bm.getWidth();

        Bitmap imageBitmap = Bitmap.createBitmap(bmWidth, 640, Bitmap.Config.ARGB_8888);

        for (int i = 0; i<bmWidth;i++){
            int[] column = new int[640];
            Arrays.fill(column, bm.get(i, 0) ? Color.BLACK : Color.WHITE);
            imageBitmap.setPixels(column, 0, 1, i, 0, 1, 640);
        }

        generateBitmap = imageBitmap;

        mImageView.setImageBitmap(imageBitmap);
    }
}
