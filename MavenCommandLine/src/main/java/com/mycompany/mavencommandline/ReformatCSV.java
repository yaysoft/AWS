package com.mycompany.mavencommandline;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.csv.*;
import org.apache.commons.lang3.StringUtils;

public class ReformatCSV
{

    public static void ReformatCSV(String inputPath, String outputPath) throws IOException, UnsupportedOperationException {

        CSVFormat inputFormat = CSVFormat.DEFAULT;
        inputFormat = inputFormat.withFirstRecordAsHeader();

        //System.out.println("Processing file \"" + inputPath + "\"...");

        FileReader fileReader = new FileReader(inputPath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String header = bufferedReader.readLine();
        fileReader.close();

        if (StringUtils.countMatches(header, '\t') > 1) {
            inputFormat = inputFormat.withDelimiter('\t');
        } else if (StringUtils.countMatches(header, "|~~|") > 1) {
            inputFormat = inputFormat.withDelimiter('|');
            inputFormat = inputFormat.withCommentMarker(null);
            inputFormat = inputFormat.withQuote(null);
            MassageCSV(inputPath, inputPath + "_massaged");
            inputPath = inputPath + "_massaged";
        } else if (StringUtils.countMatches(header, '|') > 1) {
            inputFormat = inputFormat.withDelimiter('|');
            inputFormat = inputFormat.withCommentMarker(null);
        } else if (StringUtils.countMatches(header, ',') > 1) {
        } else {
            throw new UnsupportedOperationException("Unable to determine file delimter from header");
        }

        CSVFormat outputFormat = CSVFormat.RFC4180;
        outputFormat = outputFormat.withQuoteMode(QuoteMode.MINIMAL);

        ReformatCSV(inputPath, outputPath, inputFormat, outputFormat);
    }

    public static void ReformatCSV(String inputPath, String outputPath, CSVFormat inputFormat, CSVFormat outputFormat) throws IOException {

        File inputFile = new File(inputPath);
        CSVParser inputCSV = CSVParser.parse(inputFile, Charset.defaultCharset(), inputFormat);

        Map<String, Integer> headers = inputCSV.getHeaderMap();
        Integer numColumns = headers.keySet().size();
        String[] header = headers.keySet().toArray(new String[numColumns]);

        Writer outputFile = new FileWriter(outputPath);
        CSVPrinter outputCSV = new CSVPrinter(outputFile, outputFormat.withHeader(header));

        Integer row = 0;
        for (CSVRecord r : inputCSV) {
            row++;
            if (r.size() != numColumns) {
                System.out.println(inputPath + " row " + row + " contains " + r.size() + " columns, expected " + numColumns + ".");
            }
            outputCSV.printRecord(r);
        }

        outputCSV.close();
        //System.out.println("" + row + " rows saved to \"" + outputPath + "\".");
    }
    
    

    public static void MassageCSV(String inputPath, String outputPath) throws IOException, FileNotFoundException {

        FileReader inputFile = new FileReader(inputPath);
        BufferedReader inputBuffer = new BufferedReader(inputFile);
        FileWriter outputFile = new FileWriter(outputPath);
        BufferedWriter outputBuffer = new BufferedWriter(outputFile);

        while (true) {
            String line = inputBuffer.readLine();
            
            if (line == null) {
                break;
            }
            
            line = line.replace("|~~|", "|");
            line = line.replace("Myacide|T","Myacide_T");
            
            outputBuffer.write(line);
            outputBuffer.newLine();
        }

        inputBuffer.close();
        inputFile.close();
        outputBuffer.close();
        outputFile.close();

    }
}
