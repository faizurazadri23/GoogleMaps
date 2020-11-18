package com.faizuracreator.srgeneratebarcode.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
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
import androidx.fragment.app.Fragment;

import com.faizuracreator.srgeneratebarcode.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class GenerateQRcodeFragment extends Fragment {
    private EditText mInputText;
    private ImageView mImageView;
    private FloatingActionButton mSave;
    private Activity mActivity;
    private Bitmap generatedBitmap;
    private String fileName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_generate_qrcode, container, false);

        mActivity = getActivity();

        mInputText = view.findViewById(R.id.inputText);
        mImageView = view.findViewById(R.id.outputBitmap);
        mSave = view.findViewById(R.id.saveQrcode);

        mInputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0){
                    mImageView.setImageResource(R.drawable.ic_placeholder);
                }else {
                    try {
                        generateQRcode(s.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    saveImage(generatedBitmap);
                }

            }
        });
        return view;
    }

    public void generateQRcode(String s) throws WriterException{
        fileName = s;
        BitMatrix result;
        result = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 1080, 1080, null);
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y<h; y++){
            int offset = y*w;
            for (int x = 0; x < w; x++){
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }


        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 1080, 0, 0, w, h);
        generatedBitmap = bitmap;
        mImageView.setImageBitmap(bitmap);
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
}
