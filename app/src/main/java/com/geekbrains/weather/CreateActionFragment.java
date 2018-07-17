package com.geekbrains.weather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by shkryaba on 24/06/2018.
 */

public class CreateActionFragment extends BaseFragment {

    //объявление переменных
    private TextInputEditText editTextCountry;
    private RecyclerView recyclerView;
    OnHeadlineSelectedListener mCallback;
    private LinearLayout linearLayout;
    private ArrayList<String> cityList;

    Pattern checkNum = Pattern.compile("\\D+");
    Pattern checkSym = Pattern.compile("\\w+");

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(ArrayList<String> position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(), "onAttachAction", Toast.LENGTH_SHORT).show();

        try {
            mCallback = (OnHeadlineSelectedListener) getBaseActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getBaseActivity().getAnotherFragment().toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //обращаемся к layout который будет содержать наш фрагмент
        return inflater.inflate(R.layout.create_action_fragment, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        initCountryList();

        recyclerView = view.findViewById(R.id.list_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        CustomAdapter customAdapter = new CustomAdapter(getContext(), cityList, mCallback);
        recyclerView.setAdapter(customAdapter);

        //инициализация edittext и листенер на ключи при взаимодействии с ним, когда мы нашимаем enter у нас опускается клавиатура и запускается WeatherFragment
        editTextCountry = (TextInputEditText) view.findViewById(R.id.tiet_country);

        editTextCountry.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    String country = editTextCountry.getText().toString().trim();
                    if (checkNum.matcher(country).matches() && checkSym.matcher(country).matches()) {
                        editTextCountry.setError(null);
                        ArrayList<String> arrayList = new ArrayList<>();
                        arrayList.add(country);
                        mCallback.onArticleSelected(arrayList);
                        return true;
                    } else {
                        editTextCountry.setError("Wrong city");
                    }
                }
                return false;
            }
        });
    }

    private void initCountryList() {
        cityList = new ArrayList<>();
        cityList.add("Moscow");
        cityList.add("St. Peterburg");
        cityList.add("Kazan");
    }
}
