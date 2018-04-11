import java.io.*;
import java.util.*;

/**
 * Created by Sam Baek on 1/24/2017.
 */
public class Merge {

    private static final String FPO_FILE = "fpo.csv";
    private static final String PUB_FILE = "west.csv";

    public static void main(String[] args) {
//        testMerge2016FPO_WEST();
//        testReadingFPO();
//        testReadingPubWest();

        testMergeAll();
    }

    private static void testMergeAll() {
        try {
            // input PubWest
            HashMap<String, PubWest> pubWestObjectHashMap = new HashMap<String, PubWest>();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PUB_FILE), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\uFEFF", "");
                PubWest object = new PubWest(line);
                if (Character.isLetter(line.charAt(0))) {
                    continue;
                }
                pubWestObjectHashMap.put(object.getDocumentNumber(), object);
            }

            // input FPO
            HashMap<String, Fpo> fpoObjectHashMap = new HashMap<String, Fpo>();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(FPO_FILE), "UTF-8"));
            while ((line = br.readLine()) != null) {
                line = line.replace("\uFEFF", "");
                Fpo object = new Fpo(line);
                if (Character.isLetter(line.charAt(1))) {
                    continue;
                }
                fpoObjectHashMap.put(object.getDocumentNumber(), object);
            }

            ArrayList<Fpo> notInWest = new ArrayList<Fpo>();

            HashMap<String, GTPatent> gtPatentHashMap = new HashMap<String, GTPatent>();

            Iterator fpoIterator = fpoObjectHashMap.entrySet().iterator();
            while (fpoIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) fpoIterator.next();
                Fpo fpo = (Fpo) pair.getValue();
                if (pubWestObjectHashMap.containsKey(fpo.getDocumentNumber())) {
                    gtPatentHashMap.put(
                            fpo.getDocumentNumber(),
                            new GTPatent(fpo, pubWestObjectHashMap.get(fpo.getDocumentNumber())));
                } else {
                    notInWest.add(fpo);
                }
            }

            ArrayList<PubWest> notInFpo = new ArrayList<PubWest>();

            Iterator pubIterator = pubWestObjectHashMap.entrySet().iterator();
            while (pubIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) pubIterator.next();
                PubWest object = (PubWest) pair.getValue();
                if (!fpoObjectHashMap.containsKey(object.getDocumentNumber())) {
                    notInFpo.add(object);
                }
            }

            List<GTPatent> gtPatents = new ArrayList<GTPatent>();
            Iterator gtIterator = gtPatentHashMap.entrySet().iterator();
            while (gtIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) gtIterator.next();
                gtPatents.add((GTPatent) pair.getValue());
            }

            Collections.sort(gtPatents);
            Collections.sort(notInFpo);
            Collections.sort(notInWest);
            ArrayList<Fpo> should = new ArrayList<Fpo>();
            int countShould = 0;
            for (Fpo object : notInWest) {
                System.out.println(object.getDocumentNumber() + object.getAssignees() + object.getPublicationDate());
                for (Assignee as : object.getAssignees()) {
                    int techDist = StringTool.techDistance(as.getName());
                    System.out.print("\t" + techDist + " " + as.getName() + "\n");
                    if (techDist < 5) {
                        should.add(object);
                        countShould++;
                    }
                }
            }

            System.out.println(notInWest.size() + " not in WEST");

            for (PubWest object : notInFpo) {
                System.out.println(object);
            }
            System.out.println(notInFpo.size() + " not in FPO");

            System.out.println("Should be included: " + countShould);
            for (Fpo n : should) {
                System.out.println(n.getAssignees() + " " + n);
            }
            System.out.println("\n" + gtPatentHashMap.size());
            System.out.println(fpoObjectHashMap.size() + " " + pubWestObjectHashMap.size());

            // what we have right now
            gtPatents.listIterator();
            notInFpo.listIterator();
            notInWest.listIterator();

            writeGtPatentCSV(gtPatents);
//            writeFpoCSV(notInWest);
//            writePubWestCSV(notInFpo);

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFpoCSV(List<Fpo> patents) {
        Collections.sort(patents);
        try {
            FileWriter writer = new FileWriter("Fpo_NotInPubWest.csv");
            writer.append(Fpo.CSV_HEADER);
            writer.append(StringTool.NEW_LINE_SEPARATOR);
            for (Fpo patent : patents) {
//                writer.append(patent.getCsvFormat());
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeGtPatentCSV(List<GTPatent> patents) {
        Collections.sort(patents);
        try {
            FileWriter writer = new FileWriter("GTpatents.csv");
//            final String HEADER = "publicationDate,documentNumber,title," +
//                    "abstract,inventors,assignees,applicationNumber," +
//                    "filingDate,primaryClass,otherClass,famId,cpc,documentType";

            final String HEADER = "filename" + StringTool.COMMA_DELIMITER +
                    "dc.date.issued" + StringTool.COMMA_DELIMITER +
                    "dc.identifier.patentnumber" + StringTool.COMMA_DELIMITER +
                    "dc.title" + StringTool.COMMA_DELIMITER +
                    "dc.description.abstract" + StringTool.COMMA_DELIMITER +
                    "dc.contributor.patentcreator" + StringTool.COMMA_DELIMITER +
                    "dc.description.assignee" + StringTool.COMMA_DELIMITER +
                    "dc.identifier.patentapplicationnumber" + StringTool.COMMA_DELIMITER +
                    "dc.date.filed" + StringTool.COMMA_DELIMITER +
                    "dc.identifier.uspc" + StringTool.COMMA_DELIMITER +
                    "dc.identifier.cpc";
            writer.append(HEADER);
            writer.append(StringTool.NEW_LINE_SEPARATOR);

            for (GTPatent patent : patents) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(patent.getDocumentNumber() + StringTool.FILE_PDF);
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(patent.getPublicationDate().toString());
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(patent.getDocumentNumber());
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(StringTool.QUOTE + StringTool.capitalizeWords(patent.getTitle()) + StringTool.QUOTE);
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(StringTool.QUOTE + patent.getPatentAbstract() + StringTool.QUOTE);
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(StringTool.QUOTE + patent.getInventorsCSV() + StringTool.QUOTE);
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(StringTool.QUOTE + patent.getAssigneeCSV() + StringTool.QUOTE);
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(patent.getApplicationNumber());
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(patent.getFilingDate().toString());
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(patent.getPrimaryClass());
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(StringTool.QUOTE + patent.getCpcCSV() + StringTool.QUOTE);
                stringBuilder.append(StringTool.COMMA_DELIMITER);
                stringBuilder.append(StringTool.NEW_LINE_SEPARATOR);

                writer.append(stringBuilder.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private static void testReadingPubWest() {
//        try {
//            HashMap<String, PubWest> pubWestObjectHashMap = new HashMap<String, PubWest>();
//            File file = new File("files/PUB-WEST/PubWest.csv");
//            BufferedReader br;
//            br = new BufferedReader(new FileReader(file));
//            String line;
//            int size = 0;
//            while ((line = br.readLine()) != null) {
//                if (line.startsWith("RecNo")) {
//                    continue;
//                }
//                PubWest object = new PubWest(line);
//                System.out.println(object);
//                pubWestObjectHashMap.put(object.getDocumentNumber(), object);
//            }
//            Iterator iterator = pubWestObjectHashMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry pair = (Map.Entry) iterator.next();
//                PubWest object = (PubWest) pair.getValue();
//                size++;
//            }
//
//            System.out.println(file + " SIZE: " + size);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void testReadingFPO() {
//        try {
//            HashMap<String, Fpo> fpoObjectHashMap = new HashMap<String, Fpo>();
//            File file = new File("files/FPO/FPO.csv");
//            BufferedReader br;
//            br = new BufferedReader(new FileReader(file));
//            String line;
//            int size = 0;
//            while ((line = br.readLine()) != null) {
//                if (line.startsWith("Document")) {
//                    continue;
//                }
//                Fpo object = new Fpo(line);
//                System.out.println(object);
//                fpoObjectHashMap.put(object.getDocumentNumber(), object);
//            }
//            Iterator iterator = fpoObjectHashMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry pair = (Map.Entry) iterator.next();
//                Fpo object = (Fpo) pair.getValue();
//                size++;
//            }
//            System.out.println(file + " SIZE: " + size);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void testMerge2016FPO_WEST() {
//        try {
//            HashMap<String, Fpo> fpoObjects = new HashMap();
//            BufferedReader br = new BufferedReader(new FileReader("files/FPO/2016 - FPO.csv"));
//            String line;
//            while ((line = br.readLine()) != null) {
//                if (line.startsWith("Document")) {
//                    continue;
//                }
//                Fpo object = new Fpo(line);
//                fpoObjects.put(object.getDocumentNumber(), object);
//            }
//
//            HashMap<String, PubWest> pubWestObjects = new HashMap<String, PubWest>();
//            br = new BufferedReader(new FileReader("files/PUB-WEST/2016-WEST.csv"));
//            while ((line = br.readLine()) != null) {
//                if (line.contains("RecNo")) {
//                    continue;
//                }
//                PubWest object = new PubWest(line);
//                pubWestObjects.put(object.getDocumentNumber(), object);
//            }
//            br.close();
//
//            Iterator iterator = fpoObjects.entrySet().iterator();
//            System.out.println("Found in FPO, but not in PW");
//            while (iterator.hasNext()) {
//                Map.Entry pair = (Map.Entry) iterator.next();
//                Fpo object = (Fpo) pair.getValue();
//                if (!pubWestObjects.containsKey(object.getDocumentNumber())) {
//                    System.out.println(object.getAssignees() + " " + object);
//                }
//            }
//            System.out.println("\nFound in PW, but not in FPO");
//            iterator = pubWestObjects.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry pair = (Map.Entry) iterator.next();
//                PubWest object = (PubWest) pair.getValue();
//                if (!fpoObjects.containsKey(object.getDocumentNumber())) {
//                    System.out.println(object.getAssignee() + " " + object);
//                }
//            }
//            System.out.println("\nPW size: " + pubWestObjects.size() + " FPO size: " + fpoObjects.size());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
