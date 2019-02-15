package com.alef0;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_RESOURCES)
public class Generate extends AbstractMojo {

    private static final String NEW_LINE = "\n";
    private static final String COMMENT = "#";

    @Parameter(defaultValue = "${project.build.sourceDirectory}/messages.xlsx", property = "spreadsheetFile")
    private File spreadsheetFile;

    @Parameter(defaultValue = "${project.build.sourceDirectory}/i18n", property = "outputDirectory")
    private File outputDirectory;

    public void execute() throws MojoExecutionException {
        if (!spreadsheetFile.exists())
            throw new MojoExecutionException("Cannot find spreadsheet file " + spreadsheetFile);
        else
            getLog().info("Found spreadsheet file " + spreadsheetFile);

        if (!outputDirectory.exists()) {
            getLog().info("Creating directory " + outputDirectory + ": " + outputDirectory.mkdirs());
        }

        try (OPCPackage opcPackage = OPCPackage.open(spreadsheetFile)) {
            XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);

            for (Sheet sheet : workbook)
                writePropertyFile(workbook.getSheet(sheet.getSheetName()));
        } catch (Exception e) {
            throw new MojoExecutionException("Unable to process spreadsheet file", e);
        }
    }

    private void writePropertyFile(XSSFSheet sheet) throws MojoExecutionException {
        File outputFile = new File(outputDirectory, "messages_" + sheet.getSheetName() + ".properties");

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_16)) {

            writer.write("# Automagically generated from " + spreadsheetFile.getName() + " at " + LocalDateTime.now() +
                    NEW_LINE);

            String label = "";
            String value = "";
            for (Row row : sheet) {
                if (row.getCell(0) != null)
                    label = row.getCell(0).getStringCellValue();

                if (row.getCell(1) != null && !label.startsWith(COMMENT))
                    value = row.getCell(1).getStringCellValue();

                if (StringUtils.isEmpty(value) || label.startsWith(COMMENT))
                    writer.write(label + NEW_LINE);
                else
                    writer.write(label + "=" + value + NEW_LINE);
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Unable to write messages file " + outputFile, e);
        }

        getLog().info("Wrote " + outputDirectory.getName() + "/" + outputFile.getName() +
                " from " + spreadsheetFile.getName() + "[" + sheet.getSheetName() + "]");
    }
}
