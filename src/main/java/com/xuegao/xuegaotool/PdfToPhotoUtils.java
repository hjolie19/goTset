package com.xuegao.xuegaotool;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author xuegao
 * @version 1.0
 * @date 2021/12/29 15:58
 */
public class PdfToPhotoUtils {
    private static final Logger log = LoggerFactory.getLogger(PdfToPhotoUtils.class);

    public static void pdfToPhoto(File pdfFile, String photoFile) {
        boolean suffixEquals = FileDeal.isSuffixEquals(pdfFile, FileDeal.PDF_COMMON);
        if (!suffixEquals) {
            return;
        }

        int pageNum = 1;
        try {
            PDDocument pdfDocument = PDDocument.load(pdfFile);
            int pageCount = pdfDocument.getNumberOfPages();
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                String imgPath = photoFile + File.separator + FileDeal.getFileNameNoSuffix(pdfFile.getName()) + " " + pageNum + ".jpg";
                System.out.println("fjmcs:imgPath " + imgPath);
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
                ImageIO.write(image, "jpg", new File(imgPath));
                log.info("第{}张生成的图片路径为：{}", pageIndex, imgPath);
                pageNum++;
            }
            pdfDocument.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
