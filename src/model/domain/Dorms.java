package model.domain;

public class Dorms {
    private long did;
    private String dorm_id;
    private int actual;
    private int remaining_beds;
    private long domotry;
    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public String getDorm_id() {
        return dorm_id;
    }

    public void setDorm_id(String dorm_id) {
        this.dorm_id = dorm_id;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getRemaining_beds() {
        return remaining_beds;
    }

    public void setRemaining_beds(int remaining_beds) {
        this.remaining_beds = remaining_beds;
    }

    public long getDomotry() {
        return domotry;
    }

    public void setDomotry(long domotry) {
        this.domotry = domotry;
    }
}