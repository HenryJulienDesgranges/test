package io.github.HenryJulienDesgranges.extractor_HenryJulienDesgranges;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import io.github.oliviercailloux.y2019.extractor.SimpleExtractor;

/**
 * 
 * @author hjd
 *
 */
public class MySimpleExtractor implements SimpleExtractor {

    private PDFTextStripper stripper;

    public MySimpleExtractor() {
    }

    @Override
    public void setStripper(PDFTextStripper stripper) {
        this.stripper = stripper;
    }

    @Override
    public void writeText(PDDocument document, Writer output) throws IOException {
        if (document == null) {
            return; // nothing is written
        }
        stripper.writeText(document, output);
    }

    @Override
    public void writeText(InputStream input, Writer output) throws IOException {
        if (input == null) {
            return; // nothing is written
        }
        try (PDDocument document = PDDocument.load(input)) {
            writeText(document, output);
        }
    }

    @Override
    public void writeAllText(Collection<Path> inputPaths, Writer output) throws IOException {
        Preconditions.checkNotNull(inputPaths, "no input path was given");
        for (Path path : inputPaths) {
            writeText(PDDocument.load(path.toFile()), output);
        }
    }

    @Override
    public List<String> getAllText(Collection<Path> inputPaths) throws IOException {
        Preconditions.checkNotNull(inputPaths, "no input path was given");
        List<String> fileTexts = new ArrayList<>(inputPaths.size());
        for (Path path : inputPaths) {
            fileTexts.add(readFromPath(path));
        }
        return new ImmutableList.Builder<String>().addAll(fileTexts).build();
    }

    private String readFromPath(Path path) throws IOException {
        StringWriter stringWriter = new StringWriter();
        try (FileInputStream inputStream = new FileInputStream(path.toFile())) {
            writeText(inputStream, stringWriter);
        }
        return stringWriter.toString();
    }
}
