package com.sh3h.meterreading.event;

import com.sh3h.serverprovider.entity.BiaoKaWholeEntity;

/**
 * 巡检回填
 *
 * @author Administrator
 */
public class XunJianDataUploadEvent {

    public static String TYPE_COMMITED = "commited";
    public static String TYPE_IMAGE_UPLOADED = "imageuploaded";
    public static String TYPE_SAVED = "saved";

    private String type;
    private BiaoKaWholeEntity biaoKaWholeEntity;


    public XunJianDataUploadEvent(String type, BiaoKaWholeEntity biaoKaWholeEntity) {
        this.type = type;
        this.biaoKaWholeEntity = biaoKaWholeEntity;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static String getTypeCommited() {
        return TYPE_COMMITED;
    }

    public static void setTypeCommited(String typeCommited) {
        TYPE_COMMITED = typeCommited;
    }

    public BiaoKaWholeEntity getBiaoKaWholeEntity() {
        return biaoKaWholeEntity;
    }

    public void setBiaoKaWholeEntity(BiaoKaWholeEntity biaoKaWholeEntity) {
        this.biaoKaWholeEntity = biaoKaWholeEntity;
    }
}
