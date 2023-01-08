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
        //Проверим работу тестируемого метода
        assertTrue(transactionsFileService.cleanTransactionsListJson());
        //Проверяем, появился ли файл
        assertTrue(Files.exists(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
        //Удалим файл
        assertTrue(Files.deleteIfExists(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
        //Проверим удаление файла
        assertFalse(Files.exists(Path.of(pathJsonFileForTesting, nameJsonFileForTesting)));
    }

    @Test
    @DisplayName("Тест метода по очистке Txt файла из списка транзакций")
    void testCleanTransactionsListTxt() throws IOException {
        //Проверим работу тестируемого метода
        assertTrue(transactionsFileService.cleanTransactionsTxt());
        //Проверяем, появился ли файл
        assertTrue(Files.exists(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
        //Удалим файл
        assertTrue(Files.deleteIfExists(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
        //Проверим удаление файла
        assertFalse(Files.exists(Path.of(pathTXTFileForTesting, nameTXTFileForTesting)));
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
    @DisplayName("Тест метода по сохранению (записи) Json файла в списке транзакций")
    void testSaveTransactionsListToJsonFile() {
        String stringForSave = "string for save";
        assertTrue(transactionsFileService.saveTransactionsListToJsonFile(stringForSave));
    }

    @Test
    @DisplayName("Тест работы метода по сохранению (записи) Txt файла в списке транзакций")
    void testSaveTransactionsToTxtFile() {
        String stringForSave = "string for save";
        assertTrue(transactionsFileService.saveTransactionsToTxtFile(stringForSave));
    }

    @Test
    @DisplayName("Тест работы метода по чтению сохраненного Json файла в списке транзакций")
    void testReadTransactionsListFromJsonFile() throws IOException {
        String stringForSave = "string for save";
        //Для проверки надо что-нибудь записать в файл, чтобы потом считать
        transactionsFileService.saveTransactionsListToJsonFile(stringForSave);
        String savedString = Files.readString(Path.of(pathJsonFileForTesting, nameJsonFileForTesting));
        assertEquals(stringForSave, savedString);
    }
}