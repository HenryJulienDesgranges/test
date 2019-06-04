package io.github.HenryJulienDesgranges;

import static org.junit.Assert.assertEquals;


import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import io.github.HenryJulienDesgranges.extractor_HenryJulienDesgranges.MySimpleExtractor;

public class MySimpleExtractorTest {
	
	@Test
	public void readPDFContent() {
		List<String> expected = Arrays.asList("hello world\n");
		try {
            PDFTextStripper stripper = new PDFTextStripper();
            MySimpleExtractor extractor = new MySimpleExtractor();
            extractor.setStripper(stripper);
            assertEquals(expected, extractor.getAllText(
                    Arrays.asList(Paths.get(ClassLoader.getSystemResource("hello-world.pdf").toURI()))));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
