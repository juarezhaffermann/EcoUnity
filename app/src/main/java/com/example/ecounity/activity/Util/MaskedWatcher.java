package com.example.ecounity.activity.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MaskedWatcher implements TextWatcher {
    private String mask;
    private EditText editText;
    private boolean isUpdating;
    private String oldString = "";

    public MaskedWatcher(String mask, EditText editText) {
        this.mask = mask;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String str = unmask(s.toString());
        String maskedStr = "";

        if (isUpdating) {
            oldString = str;
            isUpdating = false;
            return;
        }

        int i = 0;
        for (char m : mask.toCharArray()) {
            if (m != '#') {
                maskedStr += m;
                continue;
            }

            try {
                maskedStr += str.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }

        isUpdating = true;
        editText.setText(maskedStr);
        editText.setSelection(maskedStr.length());
    }

    @Override
    public void afterTextChanged(Editable s) {}

    private String unmask(String s) {
        return s.replaceAll("[^\\d]", "");
    }
}
