package util;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class pdfCoverter {
	
	@SuppressWarnings("deprecation")
	public static File convert(File file) throws IOException {
		
        PDFTextStripper pdfStripper = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
            PDFParser parser = new PDFParser(new RandomAccessFile(file,"r"));//new PDFParser(new FileInputStream(file));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
//            pdfStripper.setStartPage(1);
//            pdfStripper.setEndPage(5);
            String parsedText = pdfStripper.getText(pdDoc);
            File temp = new File(file.getAbsolutePath()+".txt");
            temp.createNewFile();
            //PrintWriter out = new PrintWriter(temp);
            //out.println( parsedText );
            //System.out.println(parsedText);
            pdDoc.close();
            FileUtils.writeStringToFile(temp, parsedText);
            return temp;
            
		
	}

}
