package com.EcoMentor_backend.EcoMentor.Address.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.locationtech.jts.geom.Point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddressTest {

    @Mock
    private Point location;

    @InjectMocks
    private Address address;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        address = Address.builder()
                .addressId(1L)
                .addressName("Main Street")
                .addressNumber("123")
                .zipcode(12345)
                .town("Sample Town")
                .region("Sample Region")
                .province("Sample Province")
                .location(location)
                .build();
    }

    @Test
    public void testGetAddressId() {
        assertEquals(1L, address.getAddressId());
    }

    @Test
    public void testSetAddressId() {
        address.setAddressId(2L);
        assertEquals(2L, address.getAddressId());
    }

    @Test
    public void testGetAddressName() {
        assertEquals("Main Street", address.getAddressName());
    }

    @Test
    public void testSetAddressName() {
        address.setAddressName("New Street");
        assertEquals("New Street", address.getAddressName());
    }

    @Test
    public void testGetAddressNumber() {
        assertEquals("123", address.getAddressNumber());
    }

    @Test
    public void testSetAddressNumber() {
        address.setAddressNumber("456");
        assertEquals("456", address.getAddressNumber());
    }

    @Test
    public void testGetZipcode() {
        assertEquals(12345, address.getZipcode());
    }

    @Test
    public void testSetZipcode() {
        address.setZipcode(54321);
        assertEquals(54321, address.getZipcode());
    }

    @Test
    public void testGetTown() {
        assertEquals("Sample Town", address.getTown());
    }

    @Test
    public void testSetTown() {
        address.setTown("New Town");
        assertEquals("New Town", address.getTown());
    }

    @Test
    public void testGetRegion() {
        assertEquals("Sample Region", address.getRegion());
    }

    @Test
    public void testSetRegion() {
        address.setRegion("New Region");
        assertEquals("New Region", address.getRegion());
    }

    @Test
    public void testGetProvince() {
        assertEquals("Sample Province", address.getProvince());
    }

    @Test
    public void testSetProvince() {
        address.setProvince("New Province");
        assertEquals("New Province", address.getProvince());
    }

    @Test
    public void testGetLocation() {
        when(location.getX()).thenReturn(10.0);
        when(location.getY()).thenReturn(20.0);
        assertEquals(location, address.getLocation());
    }

    @Test
    public void testSetLocation() {
        Point newLocation = mock(Point.class);
        address.setLocation(newLocation);
        assertEquals(newLocation, address.getLocation());
    }
}