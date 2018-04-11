import java.util.ArrayList;

/**
 * Created by Sam Baek on 2/6/2017.
 */
public class GTPatent implements Comparable<GTPatent> {
    private Date publicationDate;
    private String patentAbstract;
    private ArrayList<Inventor> inventors;
    private ArrayList<Assignee> assignees;
    private String applicationNumber;
    private Date filingDate;
    private String primaryClass;
    private String otherClass;

    private int recNo;                  // specific to each PubWEST download / not useful
    private String did;                 // document country + number + type
    private String title;
    private String source;
    private String famId;
    private String lang;
    private ArrayList<String> cpc;

    private String documentCntry;
    private String documentNumber;
    private String documentType;

    public GTPatent(Fpo fpo, PubWest pubWest) {
        title = fpo.getTitle();
        publicationDate = fpo.getPublicationDate();
        patentAbstract = fpo.getPatentAbstract();
        inventors = fpo.getInventors();
        assignees = fpo.getAssignees();
        applicationNumber = fpo.getApplicationNumber();
        filingDate = fpo.getFilingDate();
        primaryClass = fpo.getPrimaryClass();
        otherClass = fpo.getOtherClass();

        recNo = pubWest.getRecNo();
        did = pubWest.getDid();
        source = pubWest.getSource();
        famId = pubWest.getFamId();
        lang = pubWest.getLang();
        cpc = pubWest.getCpc();

        documentCntry = pubWest.getDocumentCntry();
        documentNumber = pubWest.getDocumentNumber();
        documentType = pubWest.getDocumentType();
    }

    @Override
    public String toString() {
        return "GTPatent{" +
                "publicationDate=" + publicationDate +
                ", patentAbstract='" + patentAbstract + '\'' +
                ", inventors=" + inventors +
                ", assignees=" + assignees +
                ", applicationNumber='" + applicationNumber + '\'' +
                ", filingDate=" + filingDate +
                ", primaryClass='" + primaryClass + '\'' +
                ", otherClass='" + otherClass + '\'' +
                ", recNo=" + recNo +
                ", did='" + did + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                ", famId='" + famId + '\'' +
                ", lang='" + lang + '\'' +
                ", cpc=" + cpc +
                ", documentCntry='" + documentCntry + '\'' +
                ", documentNumber='" + documentNumber + '\'' +
                ", documentType='" + documentType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GTPatent gtPatent = (GTPatent) o;

        if (recNo != gtPatent.recNo) return false;
        if (publicationDate != null ? !publicationDate.equals(gtPatent.publicationDate) : gtPatent.publicationDate != null)
            return false;
        if (patentAbstract != null ? !patentAbstract.equals(gtPatent.patentAbstract) : gtPatent.patentAbstract != null)
            return false;
        if (inventors != null ? !inventors.equals(gtPatent.inventors) : gtPatent.inventors != null)
            return false;
        if (assignees != null ? !assignees.equals(gtPatent.assignees) : gtPatent.assignees != null)
            return false;
        if (applicationNumber != null ? !applicationNumber.equals(gtPatent.applicationNumber) : gtPatent.applicationNumber != null)
            return false;
        if (filingDate != null ? !filingDate.equals(gtPatent.filingDate) : gtPatent.filingDate != null)
            return false;
        if (primaryClass != null ? !primaryClass.equals(gtPatent.primaryClass) : gtPatent.primaryClass != null)
            return false;
        if (otherClass != null ? !otherClass.equals(gtPatent.otherClass) : gtPatent.otherClass != null)
            return false;
        if (did != null ? !did.equals(gtPatent.did) : gtPatent.did != null)
            return false;
        if (title != null ? !title.equals(gtPatent.title) : gtPatent.title != null)
            return false;
        if (source != null ? !source.equals(gtPatent.source) : gtPatent.source != null)
            return false;
        if (famId != null ? !famId.equals(gtPatent.famId) : gtPatent.famId != null)
            return false;
        if (lang != null ? !lang.equals(gtPatent.lang) : gtPatent.lang != null)
            return false;
        if (cpc != null ? !cpc.equals(gtPatent.cpc) : gtPatent.cpc != null)
            return false;
        if (documentCntry != null ? !documentCntry.equals(gtPatent.documentCntry) : gtPatent.documentCntry != null)
            return false;
        if (documentNumber != null ? !documentNumber.equals(gtPatent.documentNumber) : gtPatent.documentNumber != null)
            return false;
        return documentType != null ? documentType.equals(gtPatent.documentType) : gtPatent.documentType == null;
    }

    @Override
    public int hashCode() {
        int result = publicationDate != null ? publicationDate.hashCode() : 0;
        result = 31 * result + (patentAbstract != null ? patentAbstract.hashCode() : 0);
        result = 31 * result + (inventors != null ? inventors.hashCode() : 0);
        result = 31 * result + (assignees != null ? assignees.hashCode() : 0);
        result = 31 * result + (applicationNumber != null ? applicationNumber.hashCode() : 0);
        result = 31 * result + (filingDate != null ? filingDate.hashCode() : 0);
        result = 31 * result + (primaryClass != null ? primaryClass.hashCode() : 0);
        result = 31 * result + (otherClass != null ? otherClass.hashCode() : 0);
        result = 31 * result + recNo;
        result = 31 * result + (did != null ? did.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (famId != null ? famId.hashCode() : 0);
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        result = 31 * result + (cpc != null ? cpc.hashCode() : 0);
        result = 31 * result + (documentCntry != null ? documentCntry.hashCode() : 0);
        result = 31 * result + (documentNumber != null ? documentNumber.hashCode() : 0);
        result = 31 * result + (documentType != null ? documentType.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(GTPatent o) {
        return this.documentNumber.compareTo(o.documentNumber);
    }

    public String getInventorsCSV() {
        if (inventors.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Inventor inventor : inventors) {
            builder.append(inventor.getNameCsv() + StringTool.DOUBLE_BAR);
        }
        return builder.toString().substring(0, builder.length() - StringTool.DOUBLE_BAR.length()).trim();
    }

    public String getAssigneeCSV() {
        if (assignees.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Assignee assignee : assignees) {
            builder.append(assignee.getName() + StringTool.DOUBLE_BAR);
        }
        return builder.toString().substring(0, builder.length() - StringTool.DOUBLE_BAR.length()).trim();
    }

    public String getCpcCSV() {
        if (cpc.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String str : cpc) {
            builder.append(str + StringTool.DOUBLE_BAR);
        }
        return builder.toString().substring(0, builder.length() - StringTool.DOUBLE_BAR.length()).trim();
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getPatentAbstract() {
        return patentAbstract;
    }

    public void setPatentAbstract(String patentAbstract) {
        this.patentAbstract = patentAbstract;
    }

    public ArrayList<Inventor> getInventors() {
        return inventors;
    }

    public void setInventors(ArrayList<Inventor> inventors) {
        this.inventors = inventors;
    }

    public ArrayList<Assignee> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<Assignee> assignees) {
        this.assignees = assignees;
    }

    public String getApplicationNumber() {
        return applicationNumber;
    }

    public void setApplicationNumber(String applicationNumber) {
        this.applicationNumber = applicationNumber;
    }

    public Date getFilingDate() {
        return filingDate;
    }

    public void setFilingDate(Date filingDate) {
        this.filingDate = filingDate;
    }

    public String getPrimaryClass() {
        return primaryClass;
    }

    public void setPrimaryClass(String primaryClass) {
        this.primaryClass = primaryClass;
    }

    public String getOtherClass() {
        return otherClass;
    }

    public void setOtherClass(String otherClass) {
        this.otherClass = otherClass;
    }

    public int getRecNo() {
        return recNo;
    }

    public void setRecNo(int recNo) {
        this.recNo = recNo;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFamId() {
        return famId;
    }

    public void setFamId(String famId) {
        this.famId = famId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<String> getCpc() {
        return cpc;
    }

    public void setCpc(ArrayList<String> cpc) {
        this.cpc = cpc;
    }

    public String getDocumentCntry() {
        return documentCntry;
    }

    public void setDocumentCntry(String documentCntry) {
        this.documentCntry = documentCntry;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
}
