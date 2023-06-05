package me.taste2plate.app.customer.customviews;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import me.taste2plate.app.customer.R;


public class LanguageChooseDialog extends Dialog {

    private TextView hindi;
    private TextView english;
    private TextView bengali;
    private TextView punjabi;
    private TextView tamil;
    private TextView telugu;
    private TextView malayalam;

    private TextView oriya;
    private TextView marathi;

    public enum Language {
        ENGLISH,
        HINDI,
        BENGALI,
        PUNJABI,
        ORIYA,
        TAMIL,
        TELUGU,
        MALAYALAM,
        MARATHI
    }

    public interface LanguageChangeListener {
        void onLanguageSelected(Language langugae);
    }

    LanguageChangeListener languageChangeListener;

    public LanguageChooseDialog(@NonNull Context context) {
        super(context);
    }

    public LanguageChooseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LanguageChooseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_options);
        hindi = findViewById(R.id.hindi);
        english = findViewById(R.id.english);
        bengali = findViewById(R.id.bengali);
        punjabi = findViewById(R.id.punjabi);
        tamil = findViewById(R.id.tamil);
        telugu = findViewById(R.id.telugu);
        malayalam = findViewById(R.id.malayalam);
        oriya = findViewById(R.id.oriya);
        marathi = findViewById(R.id.marathi);


        setCancelable(true);

        hindi.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.HINDI);
        });

        english.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.ENGLISH);
        });

        bengali.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.BENGALI);
        });

        punjabi.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.PUNJABI);
        });

        tamil.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.TAMIL);
        });

        telugu.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.TELUGU);
        });

        oriya.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.ORIYA);
        });

        malayalam.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.MALAYALAM);
        });

        marathi.setOnClickListener(view -> {
            dismiss();
            languageChangeListener.onLanguageSelected(Language.MARATHI);
        });


    }

    public void setLanguageChangeListener(LanguageChangeListener languageChangeListener) {
        this.languageChangeListener = languageChangeListener;
    }
}
