/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package new_datasetsmall;

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
public class new_datasetsmall {

    public static void main(String[] args) throws IOException, WriteException {
        String Path_in = "/home/alisha/Downloads/Final Updated Code+ Libraries/in_positive/";// give the input foleder path
        String Path_out = "/home/alisha/Downloads/Final Updated Code+ Libraries/pos_out_lowercase/";// give the output folder path
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
            String R = null;
             int match = 0;
             int mismatch  =0;
             int othermismatch = 0;
             int N_AU  =0;
             int N_GC = 0;
             int N_UG = 0;

            ArrayList<String> features = new ArrayList<String>();
            int n_feat = 0;
            int flag1 = 0;
            int flag2 = 0;
            csvOutput.write("miRNA");
            csvOutput.write("Mrna");
            csvOutput.write("R");
            csvOutput.write("Total-Matches");
            csvOutput.write("Total-Mismatces");
            csvOutput.write("Total-gc");
            csvOutput.write("Total-au");
            csvOutput.write("Total-ug");
            csvOutput.write("Total-Othermismatces");
            csvOutput.endRecord();
         
            while ((sCurrentLine = br.readLine()) != null) {
                if (sCurrentLine.contains(">hsa-miR-")) {
                    String[] temp = sCurrentLine.split("\\s+");
                    miRNA = temp[0].replaceAll(">", "").trim();

                    Mrna = temp[1].trim();
                    flag1 = 1;
                    //  Rvalue.add(temp[2].replaceAll("R:", "").trim());

                }else if(sCurrentLine.contains("Forward:")){
                 R = sCurrentLine;
                 //System.out.println(R);
                 R = R.replaceAll("   Forward:	Score: ","");
                 R = R.replaceAll("Q:", "");
                 R = R.replaceAll("R:", "");
                 R = R.replaceAll("Align Len ", "");
                 //System.out.println(R);
                 R = R.replaceAll("Energy:", "");
                 //System.out.println(R);
                 R = R.substring(21);
                 R = R.replaceAll("to", "");
                 String[] temp1 = R.split("\\s+");
                 R = temp1[0].trim()+" "+"to"+" "+temp1[1].trim();
                 
                } else if (sCurrentLine.contains("Ref:      5' ")) {
                    String ref = sCurrentLine;
                    sCurrentLine = br.readLine();
                    sCurrentLine = br.readLine();
                    String query = sCurrentLine;
                    ref = ref.replaceAll("Ref:      5'", "");
                    ref = ref.replaceAll("3'", "");
                    ref = ref.trim();
                    ref = ref.replaceAll("[A-Z]", "");
                    ref = ref.replaceAll("[\\-]", "");
                    ref = ref.trim();
                    
                    
                    query = query.replaceAll("Query:    3'", "");
                    query = query.replaceAll("5'", "");
                    query = query.trim();
                    query = query.replaceAll("[A-Z]", "");
                    query = query.replaceAll("-", "");
                    query = query.trim();
                    System.out.println("Ref " + ref + "  " + query);
                   // System.out.println(R);
                    int start = 0;
                   
                    if (ref.length() == query.length()) {
                        start = ref.length();

                    }
                   /*  //features.add(new ArrayList<String>());
                    int start = -1;
                    int end = -1;
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
                    }*/
                    for (int i = 0; i < start; i++) {
                        if (!ref.isEmpty() && !query.isEmpty()) {
                            //System.out.println("Length "+ref.length());
                            //int temp_num = Integer.parseInt(temp[i].trim());
                            String feat_temp = "";
                            feat_temp = "" + ref.charAt(i) + query.charAt(i);
                            //feat_temp=feat_temp+;
                            features.add(feat_temp);
                            //System.out.println(feat_temp);
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
                        //double feat_num = -2;
                        if (feat_temp.contentEquals("at") || feat_temp.contentEquals("gc")||feat_temp.contentEquals("ta") || feat_temp.contentEquals("cg")) {
                            match++;
                            if(feat_temp.contentEquals("gc")||feat_temp.contentEquals("cg")){
                             N_GC++;   
                            }
                           // feat_num = 1;
                        } else if (feat_temp.contentEquals("au") || feat_temp.contentEquals("ug")) {
                            //feat_num = 2;
                            if(feat_temp.contentEquals("ug")||feat_temp.contentEquals("gu")){
                            N_UG++;
                            }
                            
                            if (feat_temp.contentEquals("au")||feat_temp.contentEquals("ua")){
                                N_AU++;
                            }
                        } else if (feat_temp.contentEquals("gt") || feat_temp.contentEquals("tc") || feat_temp.contentEquals("ag") || feat_temp.contentEquals("ac")
                                || feat_temp.contentEquals("tu") || feat_temp.contentEquals("cu")||feat_temp.contentEquals("tg") || feat_temp.contentEquals("ct") || feat_temp.contentEquals("ga") || feat_temp.contentEquals("ca")
                                || feat_temp.contentEquals("ut") || feat_temp.contentEquals("uc")) {
                            //feat_num = 0;
                            mismatch++;
                        } else if (feat_temp.contentEquals("aa") || feat_temp.contentEquals("tt") || feat_temp.contentEquals("gg") || feat_temp.contentEquals("cc")) {
                           // feat_num = 0;
                           othermismatch++;
                        } else if (feat_temp.contentEquals("A-") || feat_temp.contentEquals("T-") || feat_temp.contentEquals("G-") || feat_temp.contentEquals("C-")) {
                           // feat_num = -1;
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
                       csvOutput.write(String.valueOf(N_GC));
                       csvOutput.write(String.valueOf(N_AU));
                       csvOutput.write(String.valueOf(N_UG));
                       csvOutput.write(String.valueOf(othermismatch));
     //Start valaues (Again Loop)

     //End Values   
                    csvOutput.endRecord(); 
                    features.clear();
                    flag1 = 0;
                    flag2 = 0;
                    match  =0;
                    mismatch = 0;
                    othermismatch = 0;
                    N_UG = 0;
                    N_AU =0;
                    N_GC = 0;
                    n_feat++;
                }
                if (n_feat == 22704425) {
                    break;
                }
            }

            try {
               
                csvOutput.close();
                FileOutputStream outputStream = new FileOutputStream("/home/alisha/Downloads/Final Updated Code+ Libraries/pos_out_upparcase/empty.csv");
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
