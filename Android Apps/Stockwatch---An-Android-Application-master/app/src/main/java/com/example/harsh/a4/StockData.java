package com.example.harsh.a4;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class StockData implements Serializable
{
    public static final long iij=1L;

    //StockSymbol, CompanyName -> String datatype
    //Price,PriceChange,ChangePercentage -> double datatype

    private String id;
    private String t;
    private String e;
    private double l;
    private double c;
    private double c_fix;
    private String l_fix;
    private String l_cur;
    private String s;
    private String ltt;
    private String lt;
    private String lt_dts;
    private String cp;
    private String cp_fix;
    private String ccol;
    private String pcls_fix;
    private String stock;
    private String code;
    private String companyName;

    public double getL() {
        return l;
    }

    public double getC() {
        return c;
    }

    public double getC_fix() {
        return c_fix;
    }

    public void setL(double l) {
        this.l = l;
    }

    public void setC(double c) {
        this.c = c;
    }

    public void setC_fix(double c_fix) {
        this.c_fix = c_fix;
    }

    public StockData(String t, String part2)
    {
        this.t=t;
        this.companyName=part2;
    }


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    private String sansex;
    private String symbol;

    public String getCp() {
        return cp;
    }

    public StockData(String id, String t, String e, double l, String l_fix, String l_cur, String s, String ltt, String lt, String lt_dts, double c, double c_fix, String cp, String cp_fix, String ccol, String pcls_fix) {
        this.id = id;
        this.t = t;
        this.e = e;
        this.l = l;
        this.l_fix = l_fix;
        this.l_cur = l_cur;
        this.s = s;
        this.ltt = ltt;
        this.lt = lt;
        this.lt_dts = lt_dts;
        this.c = c;
        this.c_fix = c_fix;
        this.cp = cp;
        this.cp_fix = cp_fix;
        this.ccol = ccol;
        this.pcls_fix = pcls_fix;
    }

    public StockData(String t, double l, double c, String cp, String cp_fix)
    {
        this.t = t;
        this.l = l;
        this.c = c;
        this.companyName = cp;
        this.cp_fix = cp_fix;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setT(String t) {
        this.t = t;
    }

    public void setE(String e) {
        this.e = e;
    }

    public void setL_fix(String l_fix) {
        this.l_fix = l_fix;
    }

    public void setL_cur(String l_cur) {
        this.l_cur = l_cur;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setLtt(String ltt) {
        this.ltt = ltt;
    }

    public void setLt(String lt) {
        this.lt = lt;
    }

    public void setLt_dts(String lt_dts) {
        this.lt_dts = lt_dts;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public void setCp_fix(String cp_fix) {
        this.cp_fix = cp_fix;
    }

    public void setCcol(String ccol) {
        this.ccol = ccol;
    }

    public void setPcls_fix(String pcls_fix) {
        this.pcls_fix = pcls_fix;
    }

    public static long getIij() {
        return iij;
    }

    public String getId() {
        return id;
    }

    public String getT() {
        return t;
    }

    public String getE() {
        return e;
    }

    public String getL_fix() {
        return l_fix;
    }

    public String getL_cur() {
        return l_cur;
    }

    public String getS() {
        return s;
    }

    public String getLtt() {
        return ltt;
    }

    public String getLt() {
        return lt;
    }

    public String getLt_dts() {
        return lt_dts;
    }

    public String getCp_fix() {
        return cp_fix;
    }

    public String getCcol() {
        return ccol;
    }

    public String getPcls_fix() {
        return pcls_fix;
    }

    public String getSymbol() {
        return symbol;
    }

}
