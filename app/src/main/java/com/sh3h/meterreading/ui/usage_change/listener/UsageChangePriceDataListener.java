package com.sh3h.meterreading.ui.usage_change.listener;

import com.example.dataprovider3.entity.UsageChangeInfoPriceEntity;
import com.sh3h.meterreading.ui.base.OnBaseDataListener;

import java.util.List;

public interface UsageChangePriceDataListener extends OnBaseDataListener {

    void getPriceChangesListener(List<UsageChangeInfoPriceEntity> data);

}
