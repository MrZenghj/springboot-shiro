package com.example.demo.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *  二维码工具
 */
public class QRCodeUtil {
    private static final int width = 300;//默认二维码宽度
    private static final int height = 300;//默认二维码高度
    private static final String format = "png";//默认二维码文件格式
    private static final Map<EncodeHintType,Object> hints = new HashMap<>();//默认二维码宽度

    static {
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8"); //字符编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); //容错等级L、M、Q、H 其中L为最低，H为最高
        hints.put(EncodeHintType.MARGIN,2); //二维码与图片距离
    }

    /**
     * 返回一个BufferedImage 对象
     * @param content 二维码内容
     * @throws WriterException
     */
    public static BufferedImage toBufferedImage(String content) throws WriterException {
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE,width,height,hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * 将二维码图片输出到一个流中
     * @param content 二维码内容
     * @param stream 输入流
     * @throws WriterException
     * @throws IOException
     */
    public static void writeToStream(String content, OutputStream stream) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE,width,height,hints);
        MatrixToImageWriter.writeToStream(bitMatrix,format,stream);
    }

    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param path 文件保存路径
     * @throws WriterException
     * @throws IOException
     */
    public static void createQRCode(String content, String path) throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE,width,height,hints);
        MatrixToImageWriter.writeToPath(bitMatrix,format,new File(path).toPath());
    }
}
