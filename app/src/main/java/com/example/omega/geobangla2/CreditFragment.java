package com.example.omega.geobangla2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CreditFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_credit, container,false);

        TextView text = v.findViewById(R.id.credit_text);

        String unicode_heart = getEmojiFromUnicode(0x2764);
        String line = "Coded with " + unicode_heart + " by Tanqir";
        text.setText(line);

        return v;
    }

    private String getEmojiFromUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
