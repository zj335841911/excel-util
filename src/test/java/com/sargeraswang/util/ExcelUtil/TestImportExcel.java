/**
 * @author SargerasWang
 */
package com.sargeraswang.util.ExcelUtil;

import com.myccb.Entity.AppendModel;
import com.myccb.util.ExcelUtil.ExcelLogs;
import com.myccb.util.ExcelUtil.ExcelUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;

/**
 * 测试导入Excel 97/2003
 */
public class TestImportExcel {

  @Test
  public void test() {
    StringBuilder sb = null;
    StringBuilder stringBuilder = new StringBuilder("aaa");
    System.out.println(stringBuilder.append(sb).toString());
  }

  @Test
  public void importXls() throws FileNotFoundException {
    File f=new File("src/test/resources/test.xls");
    InputStream inputStream= new FileInputStream(f);
    
    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "sheet1","yyyy/MM/dd HH:mm:ss", logs , 0);
    
    for(Map m : importExcel){
      System.out.println(m);
    }
  }

  @Test
  public void importAppendModel() throws FileNotFoundException {

    File f = new File("src/test/resources/Generate Script TemplateEDW - F1&F2&F5&Append - 副本.xlsm");
    InputStream in = new FileInputStream(f);

    ExcelLogs logs =new ExcelLogs();

    Collection<AppendModel> append = ExcelUtil.importExcel(AppendModel.class, in, "Append", "yyyy/MM/dd", logs, 0);
    System.out.println(append.size());
    System.out.println(append.stream().filter(x-> !(null ==x.getGenerate_Text()||x.getGenerate_Text().trim().equals(""))).count());
//    System.out.println(append.stream().filter(x-> !(null ==x.getGenerate_Text()||x.getGenerate_Text().trim().equals(""))).findAny());

  }

  @Test
  public void importXlsx() throws FileNotFoundException {
    File f=new File("src/test/resources/test.xlsx");
    InputStream inputStream= new FileInputStream(f);

    ExcelLogs logs =new ExcelLogs();
    Collection<Map> importExcel = ExcelUtil.importExcel(Map.class, inputStream, "sheet0","yyyy/MM/dd HH:mm:ss", logs , 0);

    for(Map m : importExcel){
      System.out.println(m);
    }
  }

}
