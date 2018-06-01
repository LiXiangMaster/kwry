package hwd.kuworuye.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/4.
 */
public class ModifierBillListAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private  ArrayList<String> tableData;

    public ModifierBillListAdapter(FragmentManager fm, List<Fragment> fragmentList, ArrayList<String> tableData) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tableData = tableData;

    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tableData.get(position);
    }
}
