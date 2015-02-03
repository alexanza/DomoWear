package com.alexanza.common.api.model;

import com.google.gson.annotations.Expose;

public class Switch {

    @Expose
    private boolean IsDimmer;
    @Expose
    private String Name;
    @Expose
    private String SubType;
    @Expose
    private String Type;
    @Expose
    private String idx;

    /**
     *
     * @return
     * The IsDimmer
     */
    public boolean isIsDimmer() {
        return IsDimmer;
    }

    /**
     *
     * @param IsDimmer
     * The IsDimmer
     */
    public void setIsDimmer(boolean IsDimmer) {
        this.IsDimmer = IsDimmer;
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
     * The SubType
     */
    public String getSubType() {
        return SubType;
    }

    /**
     *
     * @param SubType
     * The SubType
     */
    public void setSubType(String SubType) {
        this.SubType = SubType;
    }

    /**
     *
     * @return
     * The Type
     */
    public String getType() {
        return Type;
    }

    /**
     *
     * @param Type
     * The Type
     */
    public void setType(String Type) {
        this.Type = Type;
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

}