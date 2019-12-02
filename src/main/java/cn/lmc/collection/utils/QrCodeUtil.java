package main.java.cn.lmc.collection.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * QrCodeUtil
 *
 * @author limingcheng
 * @Date 2019/12/2
 */
public class QrCodeUtil {
    /**
     * 生成一个二维码图片
     * @param width
     * @param height
     * @param content
     * @return
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createQRCode(int width, int height, String content) throws WriterException, IOException {

        // 二维码基本参数设置
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");// 设置编码字符集utf-8
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);// 设置纠错等级L/M/Q/H,纠错等级越高越不易识别，当前设置等级为最高等级H
        hints.put(EncodeHintType.MARGIN, 0);// 可设置范围为0-10，但仅四个变化0 1(2) 3(4 5 6) 7(8 9 10)
        // 生成图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        // 创建位矩阵对象
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, width, height, hints);
        // 设置位矩阵转图片的参数
//      MatrixToImageConfig config = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());
        // 位矩阵对象转流对象
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", os);
        return os.toByteArray();

    }
    public static void main(String[] args) throws WriterException, IOException {
        byte[] b = createQRCode(100, 100, "http://www.sinobest.cn/");
        OutputStream os = new FileOutputStream("E:\\hz.png");
        os.write(b);
        os.close();
    }
}
