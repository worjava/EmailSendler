package email.send.emailsendler.sevice;

public interface  ParsingExcelService {

  void   processExcelFile(String excelFilePath, String outputFilePath, int columnIndex);
}
