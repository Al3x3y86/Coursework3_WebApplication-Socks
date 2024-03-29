package com.socks.coursework3_webapplicationsocks;

import com.socks.coursework3_webapplicationsocks.services.SocksFileService;
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


class SocksFileServiceTest {
    private final SocksFileService socksFileService;
    @TempDir
    File tempDirForTesting;

    private String nameFileForTesting;
    private String pathFileForTesting;

    SocksFileServiceTest() {
        socksFileService = new SocksFileService();
    }

    @BeforeEach
    void setUp(){
        ReflectionTestUtils.setField(socksFileService, "socksListFilePath", tempDirForTesting.getPath());
        ReflectionTestUtils.setField(socksFileService, "socksListFileName", "socksList.json");
        nameFileForTesting = socksFileService.getSocksListFileName();
        pathFileForTesting = socksFileService.getSocksListFilePath();
    }

    @Test
    @DisplayName("Тест метода по очистке Json файла из списка")
    void testCleanSocksListJson() throws IOException {
        //Проверим работу тестируемого метода
        assertTrue(socksFileService.cleanSocksListJson());
        //Проверяем, появился ли файл
        assertTrue(Files.exists(Path.of(pathFileForTesting, nameFileForTesting)));
        //Удалим файл
        assertTrue(Files.deleteIfExists(Path.of(pathFileForTesting, nameFileForTesting)));
        //Проверим удаление файла
        assertFalse(Files.exists(Path.of(pathFileForTesting, nameFileForTesting)));
    }

    @Test
    @DisplayName("Тест метода по получению (ранее сохраненного) Json файла из списка")
    void testGetSocksListJson() {
        //Создадим файл, который будем считывать и проверять
        File testFile = new File(pathFileForTesting + "/" + nameFileForTesting);
        assertEquals(testFile, socksFileService.getSocksListJson());
    }

    @Test
    @DisplayName("Тест метода по сохранению в файл списка носков в формате Json")
    void testSaveSocksListToJsonFile() {
        String stringForSave = "string for save";
        assertTrue(socksFileService.saveSocksListToJsonFile(stringForSave));
    }

    @Test
    @DisplayName("Тест метода по получению списка носков из файла Json")
    void testReadSocksListFromJsonFile() throws IOException {
        String stringForSave = "string for save";
        //Для проверки надо что-нибудь записать в файл, чтобы потом считать
        socksFileService.saveSocksListToJsonFile(stringForSave);
        String savedString = Files.readString(Path.of(pathFileForTesting, nameFileForTesting));
        assertEquals(stringForSave, savedString);
    }
}