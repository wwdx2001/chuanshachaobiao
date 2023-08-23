package com.sh3h.meterreading.ui.InspectionInput;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.meterreading.R;
import com.sh3h.meterreading.ui.base.BaseActivity;
import com.sh3h.meterreading.ui.base.ParentFragment;

import java.util.List;

import javax.inject.Inject;

public class RemoteLRFragment extends ParentFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, RemoteLRMvpView {
    @Inject
    RemoteLRPresenter mRemoteLRPresenter;
    protected Activity mActivity;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_remote_lr, container, false);
        mRemoteLRPresenter.attachView(this);

        return view;
    }
    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onLoadImages(List<DUMedia> duMediaList) {

    }

    @Override
    public void onLoadVoices(List<DUMedia> duMediaList) {

    }

    @Override
    public void onSaveNewImage(Boolean aBoolean) {

    }

    @Override
    public void onDeleteImage(Boolean aBoolean, DUMedia duMedia) {

    }

    @Override
    public void onSaveNewVoice(Boolean aBoolean) {

    }

    @Override
    public void onSaveSignup(Boolean aBoolean) {

    }

    @Override
    public void onLoadSignup(DUMedia duMedia) {

    }

    @Override
    public void onDeleteSignup(Boolean aBoolean) {

    }

    @Override
    public void onDeleteVoice(Boolean aBoolean, DUMedia duMedia) {

    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void onLoadVideoImages(List<DUMedia> duMediaList) {

    }

    @Override
    public void onSaveRecordVideo(Boolean aBoolean) {

    }

    @Override
    public void onDeleteVideo(Boolean aBoolean, DUMedia duMedia) {

    }
}
