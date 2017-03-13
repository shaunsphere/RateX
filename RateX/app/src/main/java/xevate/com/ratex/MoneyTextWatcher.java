package xevate.com.ratex;


import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;


    public MoneyTextWatcher( EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        String s = editable.toString();
        s = s.trim().replaceAll("[^\\d.]", "");

        editText.removeTextChangedListener(this);
        String cleanString = s.toString().replaceAll("[$,.]", "");

        String formatted = "";
        try {
            BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
            formatted = NumberFormat.getCurrencyInstance().format(parsed);
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        } catch (Exception e) {
//            Toast.makeText(ct, "Money Input Invalid",Toast.LENGTH_LONG).show();
            formatted = "Invalid";
            e.printStackTrace();
        }
        finally {

        }

    }
}