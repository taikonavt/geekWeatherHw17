package com.geekbrains.weather;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherFragment extends BaseFragment implements CreateActionFragment.OnHeadlineSelectedListener {

    private static final String ARG_COUNTRY = "ARG_COUNTRY";
    private String country;
    private TextView textView;
    private TextView tvTemp;

    public WeatherFragment() {
//        Особенностью поведения android-а состоит в том, что в любой момент
//        он может убить конкретный фрагмент (с случаи нехватки памяти например)
//        и потом попытаться восстановить его, используя конструктор без параметров,
//                следовательно передача параметров через конструкторы черевата
//        крэшами приложения в произвольный момент времени.
    }

    public static WeatherFragment newInstance(String country) {
//        Для того что бы положить требуемые значения во фрагмент,
//        нужно обернуть их в Bundle и передать через метод setArguments.
//        Стандартным способом передачи параметров считается создание статического
//        метода newInstance (...),
//        а для восстановление параметров используется метод getArguments(...),вызываемый в
//        методе жизненного цикла onCreate (...) .
        Bundle args = new Bundle();
        args.putString(ARG_COUNTRY, country);
        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            country = getArguments().getString(ARG_COUNTRY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.weather_layout, container, false);
    }

    @Override
    protected void initLayout(View view, Bundle savedInstanceState) {
        textView = view.findViewById(R.id.tv_country);
        tvTemp = view.findViewById(R.id.bigTemp);

        //проверяем нашу переменную если она не пустая показываем город, если наоборот - ничего не показываем
        if (country != null && country.length() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(country);
        } else {
            textView.setVisibility(View.GONE);
        }

        ((TextView) getBaseActivity().findViewById(R.id.tv_humidity)).setText(getText(R.string.humidity));
        ((TextView) getBaseActivity().findViewById(R.id.tv_pressure)).setText(getText(R.string.pressure));

        tvTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBaseActivity().onTvTempClick();
            }
        });
    }

    interface OnTvTempClickListener{
        void onTvTempClick();
    }

    @Override
    public void onArticleSelected(ArrayList<String> citiesList) {
        textView.setVisibility(View.VISIBLE);
        String cities = citiesList.toString();
        textView.setText(cities.substring(cities.indexOf("[") + 1, cities.indexOf("]")));
    }
}
