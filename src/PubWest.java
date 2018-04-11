import java.util.*;

/**
 * Created by Sam Baek on 1/24/2017.
 */
public class PubWest implements Comparable<PubWest> {
    private static Map<String, Integer> indexMap;

    private int recNo;
    private String did;
    private String title;
    private String assignee;
    private String issueDate;
    private String source;
    private String inventor;
    private String invCity;
    private String invState;
    private String invCntry;
    private String famId;
    private String lang;
    private ArrayList<String> cpc;

    private String documentCntry;
    private String documentNumber;
    private String documentType;

    private Date publishDate;

    private static void initIndexMap(String obj) {
        indexMap = new HashMap<String, Integer>();
        String[] headers = obj.split(",");
        for (int i = 0; i < headers.length; i++) {
            String header = headers[i].toLowerCase();
            indexMap.put(header, i);
        }
    }


    public PubWest(String obj) {
        String[] arr = obj.split(",");
        // check if patent
        if (arr[0].contains("RecNo")) {
            initIndexMap(obj);
            return;
        }
        recNo = Integer.parseInt(arr[indexMap.get("recno")]);
        did = arr[indexMap.get("did")];
        title = arr[indexMap.get("title")];
        assignee = arr[indexMap.get("assignee")];
        issueDate = arr[indexMap.get("issuedate")];
        source = arr[indexMap.get("source")];
        inventor = arr[indexMap.get("inventor")];
        invCity = arr[indexMap.get("invcity")];
        invState = arr[indexMap.get("invstate")];
        invCntry = arr[indexMap.get("invcntry")];
        famId = arr[indexMap.get("famid")];
        lang = arr[indexMap.get("lang")];
        cpc = new ArrayList<String>(Arrays.asList(arr[indexMap.get("cpc")].split(" ")));
        cutList(cpc, 3);

        String[] didSplit = did.split(" ");
        documentCntry = didSplit[0];
        documentNumber = didSplit[1];
        documentType = didSplit[2];

        publishDate = new Date(
                issueDate.substring(0, 4),
                issueDate.substring(4, 6),
                issueDate.substring(6));
    }

    @Override
    public String toString() {
        return "PubWest{" +
                "recNo='" + String.format("%03d", recNo) + '\'' +
                ", documentCntry='" + documentCntry + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                ", publishDate=" + publishDate +
                ", title='" + title + '\'' +
                ", assignee='" + assignee + '\'' +
                ", source='" + source + '\'' +
                ", inventor='" + inventor + '\'' +
                ", invCity='" + invCity + '\'' +
                ", invState='" + invState + '\'' +
                ", invCntry='" + invCntry + '\'' +
                ", famId='" + famId + '\'' +
                ", lang='" + lang + '\'' +
                ", cpc='" + cpc + '\'' +
                '}';
    }

    private static <T> void cutList(List<T> list, int max) {
        while (list.size() > max) {
            list.remove(list.size() - 1);
        }
    }

    public static Comparator<PubWest> getComparatorPublishedDates() {
        return new Comparator<PubWest>() {
            @Override
            public int compare(PubWest o1, PubWest o2) {
                return o1.publishDate.compareTo(o2.publishDate);
            }
        };
    }

    @Override
    public int compareTo(PubWest o) {
        return this.documentNumber.compareTo(o.documentNumber);
    }

    public int getRecNo() {
        return recNo;
    }

    public String getDid() {
        return did;
    }

    public String getTitle() {
        return title;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getSource() {
        return source;
    }

    public String getInventor() {
        return inventor;
    }

    public String getInvCity() {
        return invCity;
    }

    public String getInvState() {
        return invState;
    }

    public String getInvCntry() {
        return invCntry;
    }

    public String getFamId() {
        return famId;
    }

    public String getLang() {
        return lang;
    }

    public ArrayList<String> getCpc() {
        return cpc;
    }

    public String getDocumentCntry() {
        return documentCntry;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public Date getPublishDate() {
        return publishDate;
    }
}
