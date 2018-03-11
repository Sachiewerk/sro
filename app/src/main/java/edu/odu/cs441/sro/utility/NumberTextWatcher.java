package edu.odu.cs441.sro.utility;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Created by michael on 3/10/18.
 * This simplifies the validation of currency input
 * Courtesy of https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext
 */
public class NumberTextWatcher implements TextWatcher {

    private final WeakReference<EditText> editTextWeakReference;

    public NumberTextWatcher(EditText editText) {
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
        if (s.isEmpty()) return;
        editText.removeTextChangedListener(this);
        String cleanString = s.replaceAll("[$,.]", "");
        BigDecimal parsed =
                new BigDecimal(cleanString).setScale(
                        2,
                        BigDecimal.ROUND_FLOOR
                ).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);

        String formatted = NumberFormat.getCurrencyInstance().format(parsed);
        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}