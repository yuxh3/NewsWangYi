package com.example.admin.newswangyi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.admin.newswangyi.R;
import com.example.admin.newswangyi.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/5/30.
 */
public class MenuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu,null);
        lv = (ListView) view.findViewById(R.id.lv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.test_list_item, android.R.id.text1, initData());

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        return view;
    }

    private List<String> initData() {
        List<String> datas = new ArrayList<String>();
        datas.add("页面1");
        datas.add("页面2");
        datas.add("页面3");
        return datas;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            default:
                break;
        }
        if (getActivity() instanceof MainActivity){
            ((MainActivity)getActivity()).switchFragment(fragment);
        }

    }
}
