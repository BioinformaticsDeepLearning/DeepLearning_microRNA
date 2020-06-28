/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_datasetcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import jxl.write.WriteException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//Added
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
//added
import java.io.FileWriter;
import com.csvreader.CsvWriter; 
//import java.io.IOExceptio;


/**
 *
 * @author alisha
 */
public class new_datasetcsv {

    public static void main(String[] args) throws IOException, WriteException {
        String Path_in = "/home/alisha/Downloads/CSV Code+Libraries/IN/";// give the input foleder path
        String Path_out = "/home/alisha/Downloads/CSV Code+Libraries/out/";// give the output folder path
        File folder = new File(Path_in);
        File[] listOfFiles = folder.listFiles();
        FileWriter writer = null;

        for (int f = 0; f < listOfFiles.length; f++) {
            System.out.println("Start " + listOfFiles[f].getName());
            //if (listOfFiles[f].getName().equalsIgnoreCase("positive0")) {
           // Workbook workbook = new SXSSFWorkbook();
            //Sheet sheet = workbook.createSheet("Datatypes in Java");
            //int rowNum = 0;
              CsvWriter csvOutput = new CsvWriter(new FileWriter(Path_out + listOfFiles[f].getName().replace(".fasta", ".csv"),true),',');
            // TODO code application logic here
            BufferedReader br = null;
            FileReader fr = null;
            br = new BufferedReader(new FileReader(Path_in + listOfFiles[f].getName()));
            //int i = 0;
            String sCurrentLine = null;
            String miRNA = null;
            String Mrna = null;
            String Rvalue = null;
            String R1 = null;
            String R2 = null;
            String R  = null;
             int match = 0;
             int mismatch  =0;
             int N_AU  =0;
             int N_GC = 0;
            ArrayList<String> features = new ArrayList<String>();
            int n_feat = 0;
            int flag1 = 0;
            int flag2 = 0;
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains(">hsa-miR-")) {
                    String[] temp = sCurrentLine.split("\\s+");
                    miRNA = temp[0].replaceAll(">", "").trim();

                    Mrna = temp[1].trim();
                    flag1 = 1;
                    //  Rvalue.add(temp[2].replaceAll("R:", "").trim());

                }else if(sCurrentLine.contains("Forward:")){
                    
                 //String[] temp1 = sCurrentLine.split("\\s+");
                 //R1 = temp1[7].replaceAll("R:", "").trim();
                 //R2=  " "+temp1[8]+" "+temp1[9];
                 //R = R1+R2;
                   R = sCurrentLine;
                   R = R.replaceAll("   Forward:	Score: ","" );
                   R = R.trim();
                   String temp1[] = R.split("\\s+");
                   temp1[7] = null;
                   temp1[8] = null;
                   temp1[9] = null;
                   temp1[10] = null;
                   temp1[11] = null;
                   temp1[12] = null;
                   R = temp1[4].replaceAll("R:", "")+" "+temp1[5]+" "+temp1[6];
                 
                } else if (sCurrentLine.contains("Ref:      5' ")) {
                    String ref = sCurrentLine;
                    sCurrentLine = br.readLine();
                    sCurrentLine = br.readLine();
                    String query = sCurrentLine;
                    ref = ref.replaceAll("Ref:      5'", "");
                    ref = ref.replaceAll("3'", "");
                    ref = ref.trim();
                    ref = ref.replaceAll("[a-z]", "");
                    ref = ref.trim();
                    
                    query = query.replaceAll("Query:    3'", "");
                    query = query.replaceAll("5'", "");
                    query = query.trim();
                    query = query.replaceAll("[a-z]", "");
                    query = query.trim();
                    System.out.println("Ref " + ref + "  " + query);
                    System.out.println(R);
                    int start = 0;
                   
                    if (ref.length() == query.length()) {
                        start = ref.length();

                    }
                    //features.add(new ArrayList<String>());
                    /*
                    if (ref.length() == query.length()) {
                        start = ref.length() - 1;
                        end = 0;
                    } else if (ref.length() > query.length()) {
                        int mis = ref.length() - query.length();
                        for (int i = 0; i < mis; i++) {
                            query = "*" + query;
                        }
                        start = ref.length() - 1;
                        end = mis;
                    }
                    */
                    for (int i = 0; i < start; i++) {
                        if (!ref.isEmpty() && !query.isEmpty()) {
                            //System.out.println("Length "+ref.length());
                            //int temp_num = Integer.parseInt(temp[i].trim());
                            String feat_temp = "";
                            feat_temp = "" + ref.charAt(i) + query.charAt(i);
                            //feat_temp=feat_temp+;
                            features.add(feat_temp);
                        }
                    }
                    flag2 = 1;

                }

                if (flag1 == 1 && flag2 == 1) {
                   // int colNum = 0;
                   /* Row row = sheet.createRow(rowNum++);
                    int colNum = 0;
                    Cell cell = row.createCell(colNum++);
                    cell.setCellValue((String) miRNA);
                    cell = row.createCell(colNum++);
                    cell.setCellValue((String) Mrna);*/
                    csvOutput.write(miRNA); 
                    csvOutput.write(Mrna); 
                    
                     
                    //setting up extra condition to check array boundary indices//
                    for (int j = 0; j < features.size(); j++) {
                        String feat_temp = features.get(j);
                        double feat_num = -2;
                        if (feat_temp.contentEquals("AT") || feat_temp.contentEquals("GC")) {
                            match++;
                            if(feat_temp.contentEquals("GC")){
                             N_GC++;   
                            }
                            feat_num = 1;
                        } else if (feat_temp.contentEquals("AU") || feat_temp.contentEquals("UG")) {
                            feat_num = 2;
                            if (feat_temp.contentEquals("AU")){
                                N_AU++;
                            }
                        } else if (feat_temp.contentEquals("GT") || feat_temp.contentEquals("TC") || feat_temp.contentEquals("AG") || feat_temp.contentEquals("AC")
                                || feat_temp.contentEquals("TU")) {
                            feat_num = 0;
                            mismatch++;
                        } else if (feat_temp.contentEquals("AA") || feat_temp.contentEquals("TT") || feat_temp.contentEquals("GG") || feat_temp.contentEquals("CC")) {
                            feat_num = 0;
                        } else if (feat_temp.contentEquals("A-") || feat_temp.contentEquals("T-") || feat_temp.contentEquals("G-") || feat_temp.contentEquals("C-")) {
                            feat_num = -1;
                        }
                       
                    }
                       /* cell = row.createCell(colNum++);
                        cell.setCellValue((String) R);
                        cell = row.createCell(colNum++);
                        cell.setCellValue((int) match);
                        cell = row.createCell(colNum++);
                        cell.setCellValue((int) mismatch);
                        cell = row.createCell(colNum++);
                        cell.setCellValue((int) N_AU);
                        cell = row.createCell(colNum++);
                        cell.setCellValue((int) N_GC);*/
                       csvOutput.write(R); 
                       csvOutput.write(String.valueOf(match));
                       csvOutput.write(String.valueOf(mismatch));
                       csvOutput.write(String.valueOf(N_AU));
                       csvOutput.write(String.valueOf(N_GC));
     //Start valaues (Again Loop)
     for (int j = 0; j < features.size() ; j++) {
                        String feat_temp = features.get(j);
                        double feat_num = -2;
                        if (feat_temp.contentEquals("AT") || feat_temp.contentEquals("GC")) {
                         
                            feat_num = 1;
                        } else if (feat_temp.contentEquals("AU") || feat_temp.contentEquals("UG")) {
                            feat_num = 2;
                            
                        } else if (feat_temp.contentEquals("GT") || feat_temp.contentEquals("TC") || feat_temp.contentEquals("AG") || feat_temp.contentEquals("AC")
                                || feat_temp.contentEquals("TU")) {
                            feat_num = 0;
     
                        } else if (feat_temp.contentEquals("AA") || feat_temp.contentEquals("TT") || feat_temp.contentEquals("GG") || feat_temp.contentEquals("CC")) {
                            feat_num = 0;
                        } else if (feat_temp.contentEquals("A-") || feat_temp.contentEquals("T-") || feat_temp.contentEquals("G-") || feat_temp.contentEquals("C-")) {
                            feat_num = -1;
                        }
                        /*cell = row.createCell(colNum++);
                        cell.setCellValue((Double) feat_num);*/
                       csvOutput.write(String.valueOf(feat_num));
                        
                    }
     //End Values   
                    csvOutput.endRecord(); 
                    features.clear();
                    flag1 = 0;
                    flag2 = 0;
                    match  =0;
                    mismatch = 0;
                    N_AU =0;
                    N_GC = 0;
                    n_feat++;
                    R1 =null;
                    R2= null;
                    R = null;
                }
                if (n_feat == 1000000) {
                    break;
                }
            }

            try {
               
                 csvOutput.close();
                FileOutputStream outputStream = new FileOutputStream("/home/alisha/Downloads/CSV Code+Libraries/out/empty.csv");
                //workbook.write(outputStream);*/
                // workbook.close();
                
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Done" + listOfFiles[f].getName());
        }
    }
}
