package tests;

import net.lingala.zip4j.ZipFile;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.jupiter.api.Test;
import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import utils.Files;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class fileProcessingTests {
    private String filesFolder = "./src/test/resources/files/";

    @Test
    void checkContentOfTxtFile() throws Exception {
        File txtFile = new File(filesFolder + "/file.txt");
        String txtFileContent = utils.Files.readDataFromTxtFile(txtFile);
        assertThat(txtFileContent, containsString("Это txt file."));
    }

    @Test
    void checkContentOfPdfFile() throws Exception {
        PDF pdfFile = new PDF(new File(filesFolder + "/file.pdf"));
        assertThat(pdfFile, PDF.containsText("Это pdf file."));
    }

    @Test
    void checkContentOfXlsFile() throws Exception {
        XLS xlsFile = new XLS(new File(filesFolder + "/file.xls"));
        assertThat(xlsFile, XLS.containsText("Это"));
        assertThat(xlsFile, XLS.containsText("xls"));
        assertThat(xlsFile, XLS.containsText("file"));
        assertThat(xlsFile, XLS.containsText("."));
    }

    @Test
    void checkContentOfXlsxFile() throws Exception {
        XLS xlsFile = new XLS(new File(filesFolder + "/file.xlsx"));
        assertThat(xlsFile, XLS.containsText("Это"));
        assertThat(xlsFile, XLS.containsText("xls"));
        assertThat(xlsFile, XLS.containsText("file"));
        assertThat(xlsFile, XLS.containsText("."));
    }

    @Test
    void checkContentOfZipArchive() throws Exception {
        String zipPath =  filesFolder + "/files.zip";
        String unzipPath = filesFolder + "/unzip";
        String zipPassword = "password";

        ZipFile zip = new ZipFile(zipPath, zipPassword.toCharArray());
        zip.extractAll(unzipPath);

        File txtFile = new File(unzipPath + "/file.txt");
        String txtFileContent = utils.Files.readDataFromTxtFile(txtFile);
        assertThat(txtFileContent, containsString("Это txt file."));
    }

    @Test
    void checkContentOfDocxFile() throws Exception {
        String docxPath =  filesFolder + "/file.docx";

        List<XWPFParagraph> paragraphs = Files.readDocxFile(docxPath);
        String docxContent = "";
        for (XWPFParagraph paragraph: paragraphs) {
            docxContent += paragraph.getText();
        }
        assertThat(docxContent, containsString("Это docx файл."));
    }

    @Test
    void checkContentOfDocFile() throws Exception {
        String docPath =  filesFolder + "/file.doc";

        String[] paragraphs = Files.readDocFile(docPath);
        String docContent = "";
        for (String paragraph: paragraphs) {
            docContent += paragraph;
        }
        assertThat(docContent, containsString("Это doc файл."));
    }
}

