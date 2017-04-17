package com.believe.utils;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p> The describe </p>
 *
 * @author lixingping
 * @version at 2017/4/17 15:19
 * @since 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtils {

  public static void export(String fileName, String[] titles, List<Object> contents, HttpServletResponse response) {
    OutputStream os = null;
    try {
      os = response.getOutputStream();
      response.reset();
      response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("GB2312"), "ISO8859-1"));
      response.setContentType("application/msexcel");// 定义输出类型

      WritableWorkbook workbook = Workbook.createWorkbook(os);
      WritableSheet sheet = workbook.createSheet("Sheet1", 0);
      jxl.SheetSettings sheetset = sheet.getSettings();
      sheetset.setProtected(false);
      WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);
      WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
      WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);
      wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
      wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE);
      wcf_center.setAlignment(Alignment.CENTRE);
      wcf_center.setWrap(false);
      WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);
      wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); //
      wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); //
      wcf_left.setAlignment(Alignment.LEFT); //
      wcf_left.setWrap(false); //

      for (int i = 0; i < titles.length; i++) {
        sheet.addCell(new Label(i, 0, titles[i], wcf_center));
      }
      Field[] fields = null;
      int i = 1;
      for (Object obj : contents) {
        fields = obj.getClass().getDeclaredFields();
        int j = 0;
        for (Field v : fields) {
          v.setAccessible(true);
          Object va = v.get(obj);
          if (va == null) {
            va = "";
          }
          sheet.addCell(new Label(j, i, va.toString(), wcf_left));
          j++;
        }
        i++;
      }
      workbook.write();
      workbook.close();
    } catch (Exception e) {
      log.error("导出失败 {} ", e);
    } finally {
      if (null != os) {
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
