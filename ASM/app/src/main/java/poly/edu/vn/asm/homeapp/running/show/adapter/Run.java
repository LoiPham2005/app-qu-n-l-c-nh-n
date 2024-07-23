package poly.edu.vn.asm.homeapp.running.show.adapter;

public class Run {
    String id;
    int step;
    long timestamp;

    public Run() {
    }

    public Run(String id, int step, long timestamp) {
        this.id = id;
        this.step = step;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
