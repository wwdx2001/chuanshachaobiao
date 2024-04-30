package com.sh3h.meterreading.aliyun;

import android.content.Context;

public class AlivcRecorderFactory {

    public static AlivcIMixRecorderInterface createAlivcRecorderFactory(RecorderType type, Context context) {

//        return type == RecorderType.GENERAL ? new AlivcRecorder(context) : new AlivcMixRecorder(context);
        return new AlivcRecorder(context);
    }

    public enum RecorderType {
        GENERAL(1),
        MIX(2);
        private int value;

        RecorderType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
