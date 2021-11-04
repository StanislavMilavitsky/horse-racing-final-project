package by.milavitsky.horseracing.entity.enumentity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Ratio implements Serializable {
    private static final long serialVersionUID = -192958638933603052L;
    private Long raceId;
    private Long horseId;
    private Long typeId;
    private BigDecimal ratio;

    public Ratio() {
    }

    public Ratio(Long raceId, Long horseId, Long typeId, BigDecimal ratio) {
        this.raceId = raceId;
        this.horseId = horseId;
        this.typeId = typeId;
        this.ratio = ratio;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getHorseId() {
        return horseId;
    }

    public void setHorseId(Long horseId) {
        this.horseId = horseId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder("Ratio{");
        builder.append("raceId=").append(raceId);
        builder.append(", horseId=").append(horseId);
        builder.append(", typeId=").append(typeId);
        builder.append(", ratio=").append(ratio);
        builder.append('}');
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ratio)) return false;
        Ratio ratio1 = (Ratio) o;
        if (getRaceId() != null ? !getRaceId().equals(ratio1.getRaceId()) : ratio1.getRaceId() != null) return false;
        if (getHorseId() != null ? !getHorseId().equals(ratio1.getHorseId()) : ratio1.getHorseId() != null)
            return false;
        if (getTypeId() != null ? !getTypeId().equals(ratio1.getTypeId()) : ratio1.getTypeId() != null) return false;
        return getRatio() != null ? getRatio().equals(ratio1.getRatio()) : ratio1.getRatio() == null;
    }

    @Override
    public int hashCode() {
        int result = getRaceId() != null ? getRaceId().hashCode() : 0;
        result = 17 * result + (getHorseId() != null ? getHorseId().hashCode() : 0);
        result = 17 * result + (getTypeId() != null ? getTypeId().hashCode() : 0);
        result = 17 * result + (getRatio() != null ? getRatio().hashCode() : 0);
        return result;
    }
}
