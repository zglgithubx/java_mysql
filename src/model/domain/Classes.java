package model.domain;

public class Classes {
    private long cid;
    private String clss;
    private long monitor;
    private long tzs;
    private int actual;

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getClss() {
        return clss;
    }

    public void setClss(String clss) {
        this.clss = clss;
    }

    public long getMonitor() {
        return monitor;
    }

    public void setMonitor(long monitor) {
        this.monitor = monitor;
    }

    public long getTzs() {
        return tzs;
    }

    public void setTzs(long tzs) {
        this.tzs = tzs;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }
}
