package com.alexanza.common.api.model;

import com.google.gson.annotations.Expose;

public class Switch implements Comparable {
    @Expose
    private String Name;
    @Expose
    private String Status;
    @Expose
    private String idx;

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