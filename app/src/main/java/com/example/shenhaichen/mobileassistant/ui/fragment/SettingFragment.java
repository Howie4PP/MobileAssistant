package com.example.shenhaichen.mobileassistant.ui.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.example.shenhaichen.mobileassistant.R;
import com.example.shenhaichen.mobileassistant.common.Constant;
import com.example.shenhaichen.mobileassistant.common.rx.RxSchedulers;
import com.example.shenhaichen.mobileassistant.common.util.ACache;
import com.example.shenhaichen.mobileassistant.common.util.DataCleanManager;

import cn.qqtheme.framework.picker.FilePicker;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class SettingFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);



        initData();
    }

    private void initData() {

        initClearCachePref();
        initDownloadDirPref();
    }

    private void initDownloadDirPref() {
        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_apk_download_dir));
        final String dir = ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR);
        pref.setSummary(dir);

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                dirChoose(pref,dir);
                return false;
            }
        });

    }

    private void dirChoose(final Preference pref, String defualtDir) {


        FilePicker fp = new FilePicker(getActivity(),FilePicker.DIRECTORY);

        fp.setRootPath(defualtDir);
        fp.setItemHeight(50);

        fp.setOnFilePickListener(new FilePicker.OnFilePickListener() {
            @Override
            public void onFilePicked(String currentPath) {

                pref.setSummary(currentPath);
                ACache.get(getActivity()).put(Constant.APK_DOWNLOAD_DIR,currentPath);
            }
        });

        fp.show();

    }

    private void initClearCachePref() {


        final Preference pref = getPreferenceManager().findPreference(getString(R.string.key_clear_cache));

        try {
            pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {


                clearCache().subscribe(new Consumer() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        Toast.makeText(SettingFragment.this.getActivity(),"清理成功",Toast.LENGTH_LONG).show();
                        pref.setSummary(DataCleanManager.getCacheSize(getActivity().getCacheDir()));
                    }
                });


                return true;
            }
        });

    }


    private Observable clearCache(){


       return Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                DataCleanManager.cleanFiles(getActivity());
                DataCleanManager.cleanInternalCache(getActivity());

                DataCleanManager.deleteFolderFile(ACache.get(getActivity()).getAsString(Constant.APK_DOWNLOAD_DIR),false);

                e.onNext(1);
                e.onComplete();

            }
        }).compose(RxSchedulers.<Integer>io_main());



    }





}
