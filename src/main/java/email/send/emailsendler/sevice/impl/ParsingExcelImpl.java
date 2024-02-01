package email.send.emailsendler.sevice.impl;

import email.send.emailsendler.sevice.ParsingExcelService;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class ParsingExcelImpl implements ParsingExcelService {

    @SneakyThrows
    public  void processExcelFile(String excelFilePath, String outputFilePath, int columnIndex) {
        try (Workbook workbook = new XSSFWorkbook(new File(excelFilePath))) {
            Sheet sheet = workbook.getSheetAt(0); // Предполагаем, что данные находятся в первом листе

            // Путь к файлу блокнота
            try (PrintWriter writer = new PrintWriter(new FileWriter(outputFilePath))) {
                for (Row row : sheet) {

                    Cell cell = row.getCell(columnIndex);
                    if (cell != null) {
                        String email = cell.getStringCellValue().trim();
                        // Проверка на пустые значения
                        if (!email.isEmpty()) {
                            writer.println(formatEmails(email));
                        }
                    }
                }
                System.out.println("Данные успешно записаны в файл: " + outputFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatEmails(String email) {
        // Разделить адреса, добавить каждый адрес в новую строку
        return email.replaceAll("\\s*,\\s*", "\n");
    }

    public static void main(String[] args) {
        String excelFilePath = "src/main/resources/Бизнес-2.xlsx";
        String outputFilePath = "src/main/resources/emails.txt";
        int columnIndex = 0; // Замените на индекс вашего столбца

      ParsingExcelImpl p = new ParsingExcelImpl();
      p.processExcelFile(excelFilePath, outputFilePath, columnIndex);
    }
}




