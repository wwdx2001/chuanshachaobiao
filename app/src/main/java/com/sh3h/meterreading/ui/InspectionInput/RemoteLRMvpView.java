package com.sh3h.meterreading.ui.InspectionInput;

import com.sh3h.datautil.data.entity.DUMedia;
import com.sh3h.meterreading.ui.base.MvpView;

import java.util.List;

public interface RemoteLRMvpView extends MvpView {

    void onLoadImages(List<DUMedia> duMediaList);

    void onLoadVoices(List<DUMedia> duMediaList);

    void onSaveNewImage(Boolean aBoolean);

    void onDeleteImage(Boolean aBoolean, DUMedia duMedia);

    void onSaveNewVoice(Boolean aBoolean);

    void onSaveSignup(Boolean aBoolean);

    void onLoadSignup(DUMedia duMedia);

    void onDeleteSignup(Boolean aBoolean);

    void onDeleteVoice(Boolean aBoolean, DUMedia duMedia);

    void onError(String error);

    void onLoadVideoImages(List<DUMedia> duMediaList);

    void onSaveRecordVideo(Boolean aBoolean);

    void onDeleteVideo(Boolean aBoolean, DUMedia duMedia);
}
