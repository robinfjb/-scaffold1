package robin.scaffold.sample1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.util.SparseArray;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gyf.immersionbar.ImmersionBar;

import butterknife.BindView;
import robin.scaffold.baisc.base.BaseActivity;
import robin.scaffold.sample1.fragment.HomeFragment;
import robin.scaffold.sample1.fragment.ListShowFragment;
import robin.scaffold.sample1.widget.BottomTabEntity;
import robin.scaffold.sample1.widget.BottomTabLayout;
import robin.scaffold.sample1.widget.BottomTabView;
import robin.scaffold.sample1.widget.NoScrollViewPager;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {
    protected SparseArray<Fragment> mFragments;
    private BottomTabEntity homeTab;
    private BottomTabEntity secondTab;
    private static final String[] PERMISSONS = new String[]{
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.bottomTabLayout)
    BottomTabLayout mBottomBar;

    @BindView(R.id.viewPager)
    NoScrollViewPager mViewPager;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        titleManagerBuidler.setTitleTxt("Main Page");
        ImmersionBar.with(this).navigationBarWithKitkatEnable(false)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(android.R.color.white).init();

        initBottom();

        mFragments = new SparseArray<>();
        mFragments.put(0, new HomeFragment());
        mFragments.put(1, new ListShowFragment());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.getItem(mBottomBar.getCurrentItemPosition()).setSelected(false);
                mBottomBar.getItem(position).setSelected(true);
                mBottomBar.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.setCurrentItem(0,false);
    }

    @Override
    protected void initData() {
        if(!checkPermission(PERMISSONS)){
            requestPermissions(PERMISSONS, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!checkPermission(permissions)) {
            int index = -1;
            for(int i = 0; i < grantResults.length; i++) {
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    index = i;
                    break;
                }
            }
            if(index >= 0) {
                String permisson = permissions[index];
                String permissonName = "";
                String permissionDesc = "";
                showNoPermissionDialog(permissonName, permissionDesc);
            }
        }
    }

    @Override
    protected MainPresenter initPresenter() {
        MainPresenter presenter = new MainPresenter();
        presenter.attachView(this);
        return presenter;
    }

    private void initBottom(){
        homeTab = createTab("Home", null,
                null, R.drawable.ic_access_alarm_normal_24dp, R.drawable.ic_access_alarm_select_24dp);
        secondTab = createTab("Second", null,
                null, R.drawable.ic_accessible_normal_24dp, R.drawable.ic_accessible_select_24dp);

        mBottomBar.removeall();
        mBottomBar.addItem(new BottomTabView(this, homeTab))
                .addItem(new BottomTabView(this, secondTab))
                .setOnTabSelectedListener(new BottomTabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position, int prePosition) {
                        mViewPager.setCurrentItem(position, false);
                    }

                    @Override
                    public void onTabUnselected(int position) {

                    }

                    @Override
                    public void onTabReselected(int position) {

                    }
                });
        mBottomBar.reDraw();

    }

    private BottomTabEntity createTab(String defaultTitle,
                                      String unselectedFilePath,
                                      String selectedPath,
                                      int defaultNormalId,
                                      int defaultSelectedId) {
        BottomTabEntity bottomTabEntity = new BottomTabEntity();
        bottomTabEntity.setDefaultTitle(defaultTitle);
        bottomTabEntity.setDefaultNormalId(defaultNormalId);
        bottomTabEntity.setDefaultSelectedId(defaultSelectedId);
        bottomTabEntity.setNormalPath(unselectedFilePath);
        bottomTabEntity.setSelectPath(selectedPath);
        bottomTabEntity.setNormalTextColor(Color.parseColor("#666666"));
        bottomTabEntity.setSelectedTextColor(Color.parseColor("#000000"));
        return bottomTabEntity;
    }
}
