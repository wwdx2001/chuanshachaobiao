//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
package com.sh3h.meterreading.ui.InspectionInput.image;

import android.os.Environment;
import com.aliyun.svideo.sdk.external.struct.common.VideoDisplayMode;
import com.aliyun.svideo.sdk.external.struct.common.VideoQuality;
import com.aliyun.svideo.sdk.external.struct.encoder.VideoCodecs;
import com.aliyun.svideo.sdk.external.struct.recorder.CameraType;
import com.aliyun.svideo.sdk.external.struct.recorder.FlashType;
import java.io.File;

public class LittleVideoParamConfig {
    public static String DIR_CACHE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlivcQuVideo/cache";
    public static String DIR_DOWNLOAD = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlivcQuVideo/download";
    public static String DIR_COMPOSE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AlivcQuVideo/compose";

    public LittleVideoParamConfig() {
    }

    public static class Player {
        public static String REGION = "cn-shanghai";
        public static boolean LOG_ENABLE = true;

        public Player() {
        }
    }

    public static class Editor {
        public static final VideoDisplayMode VIDEO_SCALE;
        public static final VideoQuality VIDEO_QUALITY;
        public static final int VIDEO_FRAMERATE = 25;
        public static final int VIDEO_GOP = 125;
        public static final VideoCodecs VIDEO_CODEC;

        public Editor() {
        }

        static {
            VIDEO_SCALE = VideoDisplayMode.FILL;
            VIDEO_QUALITY = VideoQuality.HD;
            VIDEO_CODEC = VideoCodecs.H264_HARDWARE;
        }
    }

    public static class Crop {
        public static final int MIN_VIDEO_DURATION = 4000;
        public static final int MAX_VIDEO_DURATION = 29000;
        public static final int MIN_CROP_DURATION = 29000;
        public static final int FRAME_RATE = 25;
        public static final VideoDisplayMode CROP_MODE;

        public Crop() {
        }

        static {
            CROP_MODE = VideoDisplayMode.SCALE;
        }
    }

    public static class Recorder {
        public static final int RESOLUTION_MODE = 1;
        public static final int RATIO_MODE = 0;
        public static final int BEAUTY_LEVEL = 80;
        public static final boolean BEAUTY_STATUS = false;
        public static final CameraType CAMERA_TYPE;
        public static final FlashType FLASH_TYPE;
        public static final boolean NEED_CLIP = true;
        public static final int MAX_DURATION = 30000;
        public static final int MIN_DURATION = 2000;
        public static final VideoQuality VIDEO_QUALITY;
        public static final int GOP = 5;
        public static final VideoCodecs VIDEO_CODEC;
        public static final String OUTPUT_PATH;

        public Recorder() {
        }

        static {
            CAMERA_TYPE = CameraType.BACK;
            FLASH_TYPE = FlashType.ON;
            VIDEO_QUALITY = VideoQuality.SD;
            VIDEO_CODEC = VideoCodecs.H264_SOFT_FFMPEG;
            OUTPUT_PATH = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "巡检视频" + File.separator + "VIO_" + System.currentTimeMillis() + ".mp4";
        }
    }
}
