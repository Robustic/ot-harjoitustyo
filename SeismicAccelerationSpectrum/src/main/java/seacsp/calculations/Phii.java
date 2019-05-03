package seacsp.calculations;

/**
 * Class to capsulate damping variable phii.
 */
public class Phii {
    private double phii;
    
    /**
     * Constructor.
     *
     * @param   phii   damping value as input
     */
    public Phii(double phii) {
        this.phii = phii;
    }

    /**
     * Set method for phii-value.
     *
     * @param   phii   damping value as input
     */
    public void setPhii(double phii) {
        this.phii = phii;
    }

    /**
     * Get method for phii-value.
     * 
     * @return damping value phii
     */
    public double getPhii() {
        return phii;
    }
}
