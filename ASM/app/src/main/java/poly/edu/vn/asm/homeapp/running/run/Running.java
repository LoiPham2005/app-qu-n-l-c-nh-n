package poly.edu.vn.asm.homeapp.running.run;

public class Running {
    String id;
    int stepCount;

    public Running(String id, int stepCount) {
        this.id = id;
        this.stepCount = stepCount;
    }

    public Running() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }
}
