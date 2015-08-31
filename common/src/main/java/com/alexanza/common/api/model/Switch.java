package com.alexanza.common.api.model;

import com.google.gson.annotations.Expose;
import com.google.android.gms.wearable.DataMap;

import java.io.Serializable;

public class Switch implements Comparable, Serializable {
    @Expose
    private String Name;
    @Expose
    private String Status;
    @Expose
    private String idx;

    public Switch(String Name, String Status, String idx) {
        this.Name = Name;
        this.Status = Status;
        this.idx = idx;
    }

    public Switch(DataMap map) {
        this(
                map.getString("Name"),
                map.getString("Status"),
                map.getString("idx")
        );
    }

    public DataMap putToDataMap(DataMap map) {
        map.getString("Name", Name);
        map.getString("Status", Status);
        map.putString("idx", idx);
        return map;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     *
     * @param Status
     * The Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     *
     * @return
     * The idx
     */
    public String getIdx() {
        return idx;
    }

    /**
     *
     * @param idx
     * The idx
     */
    public void setIdx(String idx) {
        this.idx = idx;
    }

    @Override
    public int compareTo(Object o) {
        Switch f = (Switch)o;
        return this.getName().compareTo(f.getName());
    }
}