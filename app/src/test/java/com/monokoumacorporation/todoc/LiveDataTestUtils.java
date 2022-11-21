package com.monokoumacorporation.todoc;

import androidx.lifecycle.LiveData;

public class LiveDataTestUtils {
    public static <T> T getValueForTesting(final LiveData<T> liveData) {
        liveData.observeForever(ignored -> {
        });

        return liveData.getValue();
    }
}
