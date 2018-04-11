import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sam Baek on 1/24/2017.
 */
public class Fpo implements Comparable<Fpo> {
    public static final String CSV_HEADER = "publicationDate,documentNumber,documentType," +
            "title,patentAbstract,inventors,assignees,applicationNumber," +
            "filingDate,primaryClass,otherClass";

    private static Map<String, Integer> indexMap;

    private String documentNumber;
    private String documentType;
    private Date publicationDate;
    private String title;
    private String patentAbstract;
    private ArrayList<Inventor> inventors;
    private ArrayList<Assignee> assignees;
    private String applicationNumber;
    private Date filingDate;
    private String primaryClass;  // uspc
    private String otherClass;

    private static String dateSplitRegex = null;
    private static String filingSplitRegex = null;

    private static void initIndexMap(String obj) {
        indexMap = new HashMap<String, Integer>();
        String[] headers = obj.split(",");
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i].toLowerCase();
            indexMap.put(header, i);
        }
    }

    public Fpo(String obj) {
        // header
        if (Character.isLetter(obj.charAt(1)) || indexMap == null) {
            initIndexMap(obj);
            return;
        }

        try {
            // split based on csv commas
            String[] arr = obj.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
            // clean quotation marks
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].length() > 0) {
                    arr[i] = arr[i].trim();
                    if (arr[i].charAt(0) == '\"') {
                        arr[i] = arr[i].substring(1);
                    }
                    if (arr[i].charAt(arr[i].length() - 1) == '\"') {
                        arr[i] = arr[i].substring(0, arr[i].length() - 1);
                    }
                    arr[i] = arr[i].trim();
                }
            }
            documentNumber = arr[indexMap.get("document number")].trim();
            documentType = arr[indexMap.get("document type")].trim();
            title = arr[indexMap.get("title")].trim();
            patentAbstract = arr[indexMap.get("abstract")].trim();
            applicationNumber = arr[indexMap.get("application number")].trim();
            primaryClass = arr[indexMap.get("primary class")].trim();
            otherClass = arr[indexMap.get("other class")].trim();

            if (dateSplitRegex == null) {
                if (arr[indexMap.get("publication date")].contains("/")) {
                    dateSplitRegex = "/";
                } else if (arr[indexMap.get("publication date")].contains("-")) {
                    dateSplitRegex = "-";
                }
            }

            String[] dateSplit = arr[indexMap.get("publication date")].split(dateSplitRegex);
            try {
                publicationDate = new Date(dateSplit[0], dateSplit[1], dateSplit[2]);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(Arrays.toString(dateSplit));
            }
//        publicationDate = new Date(dateSplit[2], dateSplit[0], dateSplit[1]);

            if (filingSplitRegex == null) {
                if (arr[indexMap.get("filing date")].contains("/")) {
                    filingSplitRegex = "/";
                } else if (arr[indexMap.get("filing date")].contains("-")) {
                    filingSplitRegex = "-";
                }
            }

            String[] filingSplit = arr[indexMap.get("filing date")].split(filingSplitRegex);
            filingDate = new Date(filingSplit[0], filingSplit[1], filingSplit[2]);

            String[] inventorNames = arr[indexMap.get("inventor name")].split(";");
            inventors = new ArrayList<Inventor>();
            for (String inventorName : inventorNames) {
                inventors.add(new Inventor(inventorName.trim()));
            }

            String[] assigneeNames = arr[indexMap.get("assignee")].split(";");
            assignees = new ArrayList<Assignee>();
            for (String name : assigneeNames) {
                assignees.add(new Assignee(name.trim()));
            }
        } catch (Exception e) {
//            System.out.println(obj);
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(Fpo o) {
        return this.documentNumber.compareTo(o.documentNumber);
    }

    @Override
    public String toString() {
        return "Fpo{" +
                "documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", publicationDate='" + publicationDate + '\'' +
                ", title='" + title + '\'' +
                ", inventorName='" + inventors + '\'' +
                ", assignee='" + assignees + '\'' +
                ", applicationNumber='" + applicationNumber + '\'' +
                ", filingDate='" + filingDate + '\'' +
                ", primaryClass='" + primaryClass + '\'' +
                ", otherClass='" + otherClass + '\'' +
                ", patentAbstract='" + patentAbstract + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fpo object = (Fpo) o;

        if (documentNumber != object.documentNumber) return false;
        if (documentType != null ? !documentType.equals(object.documentType) : object.documentType != null)
            return false;
        if (publicationDate != null ? !publicationDate.equals(object.publicationDate) : object.publicationDate != null)
            return false;
        if (title != null ? !title.equals(object.title) : object.title != null)
            return false;
        if (patentAbstract != null ? !patentAbstract.equals(object.patentAbstract) : object.patentAbstract != null)
            return false;
        if (inventors != null ? !inventors.equals(object.inventors) : object.inventors != null)
            return false;
        if (assignees != null ? !assignees.equals(object.assignees) : object.assignees != null)
            return false;
        if (applicationNumber != null ? !applicationNumber.equals(object.applicationNumber) : object.applicationNumber != null)
            return false;
        if (filingDate != null ? !filingDate.equals(object.filingDate) : object.filingDate != null)
            return false;
        if (primaryClass != null ? !primaryClass.equals(object.primaryClass) : object.primaryClass != null)
            return false;
        return otherClass != null ? otherClass.equals(object.otherClass) : object.otherClass == null;
    }

    @Override
    public int hashCode() {
        int result = documentNumber.hashCode();
        result = 31 * result + (documentType != null ? documentType.hashCode() : 0);
        result = 31 * result + (publicationDate != null ? publicationDate.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (patentAbstract != null ? patentAbstract.hashCode() : 0);
        result = 31 * result + (inventors != null ? inventors.hashCode() : 0);
        result = 31 * result + (assignees != null ? assignees.hashCode() : 0);
        result = 31 * result + (applicationNumber != null ? applicationNumber.hashCode() : 0);
        result = 31 * result + (filingDate != null ? filingDate.hashCode() : 0);
        result = 31 * result + (primaryClass != null ? primaryClass.hashCode() : 0);
        result = 31 * result + (otherClass != null ? otherClass.hashCode() : 0);
        return result;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public String getPatentAbstract() {
        return patentAbstract;
    }

    public ArrayList<Inventor> getInventors() {
        return inventors;
    }

    public ArrayList<Assignee> getAssignees() {
        return assignees;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public String getPrimaryClass() {
        return primaryClass;
    }

    public String getOtherClass() {
        return otherClass;
    }
}
