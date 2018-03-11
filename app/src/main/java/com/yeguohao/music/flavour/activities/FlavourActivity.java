package com.yeguohao.music.flavour.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yeguohao.music.R;
import com.yeguohao.music.flavour.adapters.FlavourAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOperator;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class FlavourActivity extends AppCompatActivity {

    @BindView(R.id.flavour_recycler)
    RecyclerView recycler;

    @BindView(R.id.title_flavour)
    TextView flavour;

    @BindView(R.id.title_recently)
    TextView recently;

    private SharedPreferences preferences;

    private List<List<String>> data;
    private FlavourAdapter flavourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavour);
        ButterKnife.bind(this);

        Observable.just("")
                .lift(new ObservableOperator<Integer, String>() {
                    @Override
                    public Observer<? super String> apply(Observer<? super Integer> observer) throws Exception {
                        return null;
                    }
                });

        flavour.setSelected(true);
        flavourAdapter = new FlavourAdapter(R.layout.item_song);
        recycler.setHasFixedSize(true);
        flavourAdapter.bindToRecyclerView(recycler);

        preferences = getSharedPreferences("flavour", MODE_PRIVATE);
        data = readData();

        flavourAdapter.addData(data);
        if (data.isEmpty()) {
            flavourAdapter.setEmptyView(R.layout.flavour_empty);
        }
    }

    private List<List<String>> readData() {
        List<List<String>> data = new ArrayList<>();
        int maxIndex = preferences.getInt("max_index", -1);
        int minIndex = preferences.getInt("min_index", -1);
        if (maxIndex == -1 && minIndex == -1) {
            return data;
        }
        for (int index = maxIndex; index > minIndex; index--) {
            String string = preferences.getString(index + "", "");
            data.add(parse(string));
        }
        return data;
    }

    private List<String> parse(String string) {
        String[] split = string.split("===ygh===");
        List<String> list = new ArrayList<>();
        list.add(split[0]);
        list.add(split[1]);
        list.add(split[2]);
        return list;
    }

    @OnClick({R.id.title_flavour, R.id.title_recently})
    void choose(View view) {
        if (view.isSelected()) {
            return;
        }
        view.setSelected(true);
        if (view.getId() == R.id.title_flavour) {
            flavourAdapter.replaceData(data);
        } else {
            flavourAdapter.setEmptyView(R.layout.flavour_empty);
        }
    }

    @OnClick(R.id.random_play)
    void randomPlay() {

    }
}
