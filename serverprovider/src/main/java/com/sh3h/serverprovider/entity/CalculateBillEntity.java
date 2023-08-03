package com.sh3h.serverprovider.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhangjing on 2017/9/7.
 */

public class CalculateBillEntity {

    /// <summary>
    /// 任务编号
    /// </summary>
    private int taskId;
    ///
    ///表卡编号
    ///
    private String cid;
    /// <summary>
    /// 抄表记录编号
    /// </summary>
    private int recordId;
    /// <summary>
    /// 抄码
    /// </summary>
    private int reading;
    /// <summary>
    /// 子表抄码
    /// </summary>
    private int reading1;
    /// <summary>
    /// 抄表状态
    /// </summary>
    private int readState;
    /// <summary>
    /// 抄表水量
    /// </summary>
    private int readWater;

    private int meterType;
    private int readTimes;

    public CalculateBillEntity(int taskId, String cid, int recordId, int reading, int reading1,
                               int readState, int readWater, int meterType, int readTimes) {
        this.taskId = taskId;
        this.cid = cid;
        this.recordId = recordId;
        this.reading = reading;
        this.reading1 = reading1;
        this.readState = readState;
        this.readWater = readWater;
    }

    public static JSONObject toJSON(CalculateBillEntity calculateBillEntity) throws JSONException {
        JSONObject object = new JSONObject();
        object.put("TaskId", calculateBillEntity.taskId);
        object.put("Cid", calculateBillEntity.cid);
        object.put("RecordId", calculateBillEntity.recordId);
        object.put("Reading", calculateBillEntity.reading);
        object.put("Reading1", calculateBillEntity.reading1);
        object.put("ReadWater", calculateBillEntity.readWater);
        object.put("ReadState", calculateBillEntity.readState);
        object.put("MeterType", calculateBillEntity.meterType);
        object.put("ReadTimes", calculateBillEntity.readTimes);
        return object;
    }

    public static JSONArray toJSONArray(List<CalculateBillEntity> list)
            throws JSONException {
        JSONArray array = new JSONArray();

        for (CalculateBillEntity calculateBillEntity : list) {
            JSONObject object = toJSON(calculateBillEntity);
            array.put(object);
        }
        return array;
    }
}
