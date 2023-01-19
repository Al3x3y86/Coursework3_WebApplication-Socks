package com.socks.coursework3_webapplicationsocks;

import com.socks.coursework3_webapplicationsocks.services.TransactionsFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsFileServiceTest {

    @TempDir
    File tempDirForTesting;
    private String nameJsonFileForTesting;
    private String pathJsonFileForTesting;

    private String nameTXTFileForTesting;
    private String pathTXTFileForTesting;

    private final TransactionsFileService transactionsFileService;

    TransactionsFileServiceTest() {
        transactionsFileService = new TransactionsFileService();
    }

    @BeforeEach
    void setUp(){
        //Устанавливаем путь и название файла для Json файла
        ReflectionTestUtils.setField(transactionsFileService, "transactionsListFilePath", tempDirForTesting.getPath());
        ReflectionTestUtils.setField(transactionsFileService, "transactionsListFileName", "transactions.json");
        //Устанавливаем путь и название файла для TXT файла
        ReflectionTestUtils.setField(transactionsFileService, "transactionsTxtFilePath", tempDirForTesting.getPath());
        ReflectionTestUtils.setField(transactionsFileService, "transactionsTxtFileName", "transactions.txt");
        //Присвоим переменной для Json файла значения
        nameJsonFileForTesting = transactionsFileService.getTransactionsListFileName();
        pathJsonFileForTesting = transactionsFileService.getTransactionsListFilePath();
        //Присвоим переменной для TXT файла значения
        nameTXTFileForTesting = transactionsFileService.getTransactionsTxtFileName();
        pathTXTFileForTesting = transactionsFileService.getTransactionsTxtFilePath();
    }

    @Test
    @DisplayName("Тест метода по очистке Json файла из списка транзакций")
    void testCleanTransactionsListJson() throws IOException {
        //Проверяем метод и файл
        assertTrue(transactionsFileService.cleanTransactionsListJson());
        assertTrue(Files.exists(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
        //Читаем файл и возвращаем подтверждения что файл пустой
        List<String> testJson = (Files.readAllLines(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
        assertTrue(testJson.isEmpty());

    }

    @Test
    @DisplayName("Тест метода по очистке Txt файла из списка транзакций")
    void testCleanTransactionsListTxt() throws IOException {
        //Проверяем метод и файл
        assertTrue(transactionsFileService.cleanTransactionsTxt());
        assertTrue(Files.exists(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
       //Читаем файл и возвращаем подтверждения что файл пустой
        List<String> testTXT = (Files.readAllLines(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
        assertTrue(testTXT.isEmpty());

    }

    @Test
    @DisplayName("Тест метода по получению Txt файла из списка транзакций")
    void testGetTxtFile() {
        //Создадим файл, который будем считывать и проверять
        File testFile = new File(pathTXTFileForTesting + "/" + nameTXTFileForTesting);
        assertEquals(testFile, transactionsFileService.getTxtFile());
    }

    @Test
    @DisplayName("Тест метода по получению Json файла из списка транзакций")
    void testGetTransactionsListJson() {
        //Создадим файл, который будем считывать и проверять
        File testFile = new File(pathJsonFileForTesting + "/" + nameJsonFileForTesting);
        assertEquals(testFile, transactionsFileService.getTransactionsListJson());
    }

    @Test
    @DisplayName("Тест метода по чтению сохраненного TXT файла в списке транзакций")
    void testSaveAndReadTransactionsToTxtFile() throws IOException {
        String stringForSave = "string for save";
        //Для проверки надо что-нибудь записать в файл, чтобы потом считать
        transactionsFileService.saveTransactionsToTxtFile(stringForSave);
        String savedString = Files.readString(Path.of(pathTXTFileForTesting, nameTXTFileForTesting));
        assertEquals(stringForSave,savedString);
    }

    @Test
    @DisplayName("Тест метода по чтению сохраненного Json файла в списке транзакций")
    void testSaveAndReadTransactionsListFromJsonFile() throws IOException {
        String stringForSave = "string for save";
        //Для проверки надо что-нибудь записать в файл, чтобы потом считать
        transactionsFileService.saveTransactionsListToJsonFile(stringForSave);
        String savedString = Files.readString(Path.of(pathJsonFileForTesting, nameJsonFileForTesting));
        assertEquals(stringForSave, savedString);
    }
}