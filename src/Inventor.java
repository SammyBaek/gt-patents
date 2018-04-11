import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sam Baek on 1/25/2017.
 */
public class Inventor {
    private String lastName = "";
    private String firstName = "";
    private String middleName = "";
    private String city = "";
    private String state = "";
    private String country = "";
    private String location = "";

    private String line;

    public Inventor(String line) {
        this.line = line;
        String[] spaceSplit = line.split(" ");
        // remove any empty indexes
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(spaceSplit));
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isEmpty()) {
                list.remove(i);
                i--;
            }
        }
        //
        // edge case: only two names:
        if (list.size() == 2) {
            lastName = list.get(0);
            firstName = list.get(1);
            return;
        }
        if (list.size() == 3) {
            lastName = list.get(0);
            firstName = list.get(1);
            middleName = list.get(2);
            return;
        }

        // insert last name
        int ind = 0;
        do {
            lastName = lastName + " " + list.get(ind);
        } while (!list.get(ind++).contains(","));
        lastName = lastName.trim();
        lastName = lastName.substring(0, lastName.indexOf(","));

        // insert first name
        firstName = list.get(ind++);
        firstName = firstName.trim();

        // check if the next index is not a city
        while (ind < list.size() && !list.get(ind).contains("(")) {
            middleName = middleName + " " + list.get(ind++);
        }
        middleName = middleName.trim();

        // check if invetor has city / state / country
        if (ind >= list.size()) {
            return;
        }

        for (int i = ind; i < list.size(); i++) {
            location += list.get(i);
        }
        location = location.substring(location.indexOf("("), location.indexOf(")"));
        if (list.size() - ind > 3 || list.size() - ind < 2) { // too much or little info (e.g. street address / zipcode)
            return;
        }
        // insert city
        do {
            city = city + " " + list.get(ind);
        } while (!list.get(ind++).contains(","));
        city = city.trim();
        city = city.substring(city.indexOf("(") + 1, city.indexOf(","));

        // check if the next index is not US
        if (!list.get(ind).contains(")")) {
            state = list.get(ind++);
            state = state.substring(0, state.indexOf(","));
        }

        // insert country
        country = list.get(ind);
        country = country.trim();
        country = country.substring(0, country.indexOf(")"));
    }

    public String getNameCsv() {
        String mid = StringTool.capitalizeWords(middleName);
        if (middleName.length() == 1) {
            mid = mid + ".";
        }
        String first = StringTool.capitalizeWords(firstName);
        if (firstName.length() == 1) {
            first = first + ".";
        }
        String last = "";
        String[] lastArr = lastName.split(" ");
        for (int i = 0; i < lastArr.length; i++) {
            if (i == 0) {
                last += StringTool.capitalizeWords(lastArr[i]) + " ";
            } else {
                last += lastArr[i] + " ";
            }
        }
        if (last.contains(",")) {
            last = last.substring(0, last.indexOf(","));
        }
        last = last.trim();
        return (last + ", " + first + " " + mid).trim();
    }

    @Override
    public String toString() {
        return "Inventor{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Inventor inventor = (Inventor) o;

        if (lastName != null ? !lastName.equals(inventor.lastName) : inventor.lastName != null)
            return false;
        if (firstName != null ? !firstName.equals(inventor.firstName) : inventor.firstName != null)
            return false;
        if (middleName != null ? !middleName.equals(inventor.middleName) : inventor.middleName != null)
            return false;
        if (city != null ? !city.equals(inventor.city) : inventor.city != null)
            return false;
        if (state != null ? !state.equals(inventor.state) : inventor.state != null)
            return false;
        if (country != null ? !country.equals(inventor.country) : inventor.country != null)
            return false;
        return line != null ? line.equals(inventor.line) : inventor.line == null;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (line != null ? line.hashCode() : 0);
        return result;
    }

    public String toOriginalString() {
        StringBuilder result = new StringBuilder();
        result.append(lastName + ", ");
        result.append(firstName + " ");
        if (!middleName.isEmpty()) {
            result.append(middleName + " ");
        }
        result.append("(" + city + ", ");
        if (!state.isEmpty()) {
            result.append(state + ", ");
        }
        result.append(country + ")");

        return result.toString();
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
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

    public String getName() {
        String name = line;
        if (line.indexOf("(") != -1) {
            name = line.substring(0, line.indexOf("("));
        }
        return StringTool.capitalizeWords(name);
    }


}
