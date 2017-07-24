package com.mycompany.mavencommandline;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Map;
import org.apache.commons.csv.*;

public class ReformatCSV
{

    public static void ReformatCSV(String inputPath, String outputPath) throws IOException {

        CSVFormat tabDelimited = CSVFormat.DEFAULT;
        tabDelimited = tabDelimited.withDelimiter('\t');
        tabDelimited = tabDelimited.withFirstRecordAsHeader();

        CSVFormat outputFormat = CSVFormat.RFC4180;
        outputFormat = outputFormat.withQuoteMode(QuoteMode.MINIMAL);

        ReformatCSV(inputPath, outputPath, tabDelimited, outputFormat);
    }

    public static void ReformatCSV(String inputPath, String outputPath, CSVFormat inputFormat, CSVFormat outputFormat) throws IOException {

        File inputFile = new File(inputPath);
        CSVParser inputCSV = CSVParser.parse(inputFile, Charset.defaultCharset(), inputFormat);

        Map<String, Integer> headers = inputCSV.getHeaderMap();
        String[] header = headers.keySet().toArray(new String[headers.keySet().size()]);

        Writer outputFile = new FileWriter(outputPath);
        CSVPrinter outputCSV = new CSVPrinter(outputFile, outputFormat.withHeader(header));

        for (CSVRecord r : inputCSV) {
            outputCSV.printRecord(r);
        }

        outputCSV.close();
    }
}
