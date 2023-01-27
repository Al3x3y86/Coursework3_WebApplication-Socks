package com.socks.coursework3_webapplicationsocks;

import com.socks.coursework3_webapplicationsocks.characteristic.SocksColor;
import com.socks.coursework3_webapplicationsocks.characteristic.SocksSize;
import com.socks.coursework3_webapplicationsocks.characteristic.TransactionsType;
import com.socks.coursework3_webapplicationsocks.repository.TransactionRepository;
import com.socks.coursework3_webapplicationsocks.repository.WarehouseRepository;
import com.socks.coursework3_webapplicationsocks.services.SocksService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SocksServiceTest {

    private final SocksService socksService;
    private final WarehouseRepository warehouseRepository = Mockito.mock(WarehouseRepository.class);
    private final TransactionRepository transactionRepository = Mockito.mock(TransactionRepository.class);

    SocksColor socksColor = SocksColor.BLUE;
    SocksSize socksSize = SocksSize.L;
    private static final int cottonPart = 50;
    private static final int cottonMin = 45;
    private static final int cottonMax = 70;
    private static final int expectedValueSocks = 10;
    private static final int quantity = 100;

    public SocksServiceTest() {
        socksService = new SocksService(transactionRepository, warehouseRepository);
    }

    @Test
    @DisplayName("Тест метода по поиску носков с содержанием хлопка МЕНЬШЕ заданной величины")
    void testFindByCottonPartLessThan() {
        when (warehouseRepository.findByCottonPartLessThan(socksColor, socksSize, cottonMin)).thenReturn(expectedValueSocks);
        int socksValue = socksService.findByCottonPartLessThan(socksColor, socksSize, cottonMin);
        assertEquals(expectedValueSocks, socksValue);
    }

    @Test
    @DisplayName("Тест метода по поиску носков с содержанием хлопка БОЛЬШЕ заданной величины")
    void testFindByCottonPartMoreThan() {
        when (warehouseRepository.findByCottonPartMoreThan(socksColor, socksSize, cottonMax)).thenReturn(expectedValueSocks);
        int socksValue = socksService.findByCottonPartMoreThan(socksColor, socksSize, cottonMax);
        assertEquals(expectedValueSocks, socksValue);
    }

    @Test
    @DisplayName("Тест метода - добавления носков в список (хранилище) и добавления транзакции (поступившие носки)")
    void testAddToWarehouse() {
        when(warehouseRepository.addInWarehouseRepository(cottonPart,socksColor,socksSize,quantity)).thenReturn(true);
        when(transactionRepository.addTransaction(cottonPart, socksColor, socksSize, quantity, TransactionsType.INCOMING)).thenReturn(true);
        boolean correctAddInStorage = socksService.addToWarehouse(socksColor,socksSize, cottonPart,quantity);
        assertTrue(correctAddInStorage);
    }

    @Test
    @DisplayName("Тест метода - на неуспешное добавления носков в список")
    void testAddToWarehouseNotSuccess() {
        when(warehouseRepository.addInWarehouseRepository(cottonPart,socksColor,socksSize,quantity)).thenReturn(false);
        when(transactionRepository.addTransaction(cottonPart, socksColor, socksSize, quantity, TransactionsType.INCOMING)).thenReturn(false);
        boolean notCorrectAddInStorage = socksService.addToWarehouse(socksColor,socksSize, cottonPart,quantity);
        assertFalse(notCorrectAddInStorage);
    }

    @Test
    @DisplayName("Тест метода - удаления(списания) носков из списка (хранилища) и добавления транзакции(списанные носки)")
    void testDelete() {
        when(warehouseRepository.delete(cottonPart,socksColor,socksSize,quantity)).thenReturn(true);
        when(transactionRepository.addTransaction(cottonPart, socksColor, socksSize, quantity, TransactionsType.CANCELLATION)).thenReturn(true);
        boolean correctDeleteFromStorage = socksService.delete(socksColor,socksSize, cottonPart,quantity);
        assertTrue(correctDeleteFromStorage);
    }

    @Test
    @DisplayName("Тест метода - отпуск партии носков со склада и добавления транзакции (отправленные со склада носки)")
    void testReleaseFromWarehouse() {
        when(warehouseRepository.outFromWarehouse(cottonPart,socksColor,socksSize,quantity)).thenReturn(true);
        when(transactionRepository.addTransaction(cottonPart, socksColor, socksSize, quantity, TransactionsType.OUTGOING)).thenReturn(true);
        boolean correctReleaseFromStorage = socksService.releaseFromWarehouse(socksColor,socksSize,cottonPart,quantity);
        assertTrue(correctReleaseFromStorage);
    }
}
