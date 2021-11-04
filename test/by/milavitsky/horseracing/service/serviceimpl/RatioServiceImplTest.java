package by.milavitsky.horseracing.service.serviceimpl;


import by.milavitsky.horseracing.dao.DaoFactory;
import by.milavitsky.horseracing.dao.daoabstract.RatioDaoAbstract;
import by.milavitsky.horseracing.entity.Horse;
import by.milavitsky.horseracing.entity.Ratio;
import by.milavitsky.horseracing.exception.DaoException;
import by.milavitsky.horseracing.exception.ServiceException;

import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.HorseServiceInterface;
import by.milavitsky.horseracing.service.serviceinterface.RatioServiceInterface;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.*;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.testng.Assert.*;
import static org.testng.Assert.fail;


@SuppressStaticInitializationFor({"by.milavitsky.horseracing.dao.DaoFactory", "by.milavitsky.horseracing.dao.Dao", "by.milavitsky.horseracing.service.ServiceFactory"})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.*", "javax.management.*"})
@PrepareForTest({DaoFactory.class, HorseServiceInterface.class, ServiceFactory.class})
public class RatioServiceTest extends PowerMockTestCase {

    @Mock
    private ServiceFactory serviceFactory;
    @Mock
    private DaoFactory daoFactory;
    @Mock
    private RatioDaoAbstract ratioDao;
    @Mock
    private HorseServiceInterface horseService;

    private RatioServiceInterface service;

    @BeforeMethod
    public void setUp() {
        mockStatic(DaoFactory.class);
        when(DaoFactory.getInstance()).thenReturn(daoFactory);
        when((RatioDaoAbstract) daoFactory.getClass(Mockito.any())).thenReturn(ratioDao);
        service = RatioService.getInstance();
        mockStatic(ServiceFactory.class);
        when(ServiceFactory.getInstance()).thenReturn(serviceFactory);
        when((HorseServiceInterface) serviceFactory.getClass(Mockito.any())).thenReturn(horseService);
    }

    @DataProvider(name = "validMap")
    public Object[][] createValidMap() {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("12|8|win", "2.35");
        parameterMap.put("7|9|show", "5.89");
        return new Object[][]{{parameterMap}};
    }

    @DataProvider(name = "invalidMap")
    public Object[][] createInvalidMap() {
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put("12|8|win", "2.35");
        parameterMap.put("7|9|shows", "5.89");
        return new Object[][]{{parameterMap}};
    }

    @Test
    public void testFindRatioPositive() {
        try {
            List<Ratio> expected = new ArrayList<>();
            when(ratioDao.findRatio(Mockito.anyLong())).thenReturn(expected);
            List<Ratio> actual = service.findRatio("1");
            assertEquals(actual, expected);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testFindRatioNegative() {
        try {
            when(ratioDao.findRatio(Mockito.anyLong())).thenReturn(null);
            List<Ratio> condition = service.findRatio("1");
            assertNull(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class)
    public void testFindRatioException() throws ServiceException, DaoException {
        when(ratioDao.findRatio(Mockito.anyLong())).thenThrow(DaoException.class);
        service.findRatio("1");
        fail("No exception was thrown!");
    }

    @Test(dataProvider = "validMap")
    public void testAddRatiosValidMap(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            Set<Horse> horses = new HashSet<>();
            horses.add(new Horse(1L, "Horse", 5, "Jockey"));
            when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
            boolean condition = service.addRatios(parameterMap);
            assertTrue(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "validMap")
    public void testAddRatiosNegative(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(false);
            Set<Horse> horses = new HashSet<>();
            horses.add(new Horse(1L, "Horse", 5, "Jockey"));
            when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
            boolean condition = service.addRatios(parameterMap);
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "invalidMap")
    public void testAddRatiosInvalidMap(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            Set<Horse> horses = new HashSet<>();
            horses.add(new Horse(1L, "Horse", 5, "Jockey"));
            when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
            boolean condition = service.addRatios(parameterMap);
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(dataProvider = "validMap")
    public void testAddRatiosInvalidRatioCount(Map<String, String> parameterMap) {
        try {
            when(ratioDao.setRatios(Mockito.anySet())).thenReturn(true);
            when(horseService.showByRace(Mockito.anyString())).thenReturn(new HashSet<>());
            boolean condition = service.addRatios(parameterMap);
            assertFalse(condition);
        } catch (ServiceException | DaoException e) {
            fail(e.getMessage());
        }
    }

    @Test(expectedExceptions = ServiceException.class, dataProvider = "validMap")
    public void testAddRatiosException(Map<String, String> parameterMap) throws ServiceException, DaoException {
        when(ratioDao.setRatios(Mockito.anySet())).thenThrow(DaoException.class);
        Set<Horse> horses = new HashSet<>();
        horses.add(new Horse(1L, "Horse", 5, "Jockey"));
        when(horseService.showByRace(Mockito.anyString())).thenReturn(horses);
        service.addRatios(parameterMap);
        fail("No exception was thrown!");
    }
}