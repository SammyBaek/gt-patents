/**
 * Created by Sam Baek on 1/31/2017.
 */
public class Assignee {
    private String name = "";
    private String city = "";
    private String state = "";
    private String country = "";

    private String line = "";

    public Assignee(String line) {
        this.line = line;
        int cur = line.indexOf("(");
        if (cur == -1) {
            name = line;
            return;
        }
        name = line.substring(0, cur).trim();
        String location[] = line.substring(cur + 1, line.indexOf(")")).split(",");
        if (location.length == 1) {
            country = location[0];
            return;
        }
        int ind = 0;
        city = location[ind++].trim();
        if (location.length != 2) {
            state = location[ind++].trim();
        }
        country = location[ind++].trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Assignee assignee = (Assignee) o;

        if (name != null ? !name.equals(assignee.name) : assignee.name != null)
            return false;
        if (city != null ? !city.equals(assignee.city) : assignee.city != null)
            return false;
        if (state != null ? !state.equals(assignee.state) : assignee.state != null)
            return false;
        return country != null ? country.equals(assignee.country) : assignee.country == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Assignee{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getName() {
        return StringTool.capitalizeWords(name);
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getLine() {
        return line;
    }

}
