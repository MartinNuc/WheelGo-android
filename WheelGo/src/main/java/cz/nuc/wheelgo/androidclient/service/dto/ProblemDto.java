package cz.nuc.wheelgo.androidclient.service.dto;

/**
 * Created with IntelliJ IDEA.
 * User: mist
 * Date: 13.11.13
 * Time: 18:12
 */
public class ProblemDto {
    public long id;
    public String description;
    public double latitude;
    public double longitude;
    public String name;
    public Long expirationTimestamp;
    public long reportedTimestamp;
    public Category category;
    public String imageBase64;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProblemDto that = (ProblemDto) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
