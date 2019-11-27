package parkingSystem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class SoftParkTest {
    private SoftPark park;
    private Car car;
    private static final String DEFAULT_CAR_MAKE = "Ford";
    private static final String DEFAULT_CAR_NUMBER = "0000";

    @Before
    public void setup() {
        this.park = new SoftPark();
        this.car = new Car(DEFAULT_CAR_MAKE, DEFAULT_CAR_NUMBER);
    }

    @Test
    public void testValidImplementationConstructor() {

        int actualParkingSpotCount = park.getParking().size();

        assertEquals(12, actualParkingSpotCount);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testGetParkingMethodShouldThrowException() {
        park.getParking().remove("A1" );
    }

    @Test (expected = IllegalArgumentException.class)
    public void testParkCarMethodShouldThrowExceptionWhenParkSpotNotExists() {
        Car car = new Car("Ford","PA4455KA");
        park.parkCar("W1", car);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testParkCarMethodShouldThrowExceptionWhenParkSpotNotAvailable() {
        Car car = new Car("Ford","PA4455KA");
        park.parkCar("A1", car);
        park.parkCar("A1", car);
    }

    @Test
    public void testParkCarMethodWorksCorrectly() {
        Car car = new Car("Ford","PA4455KA");
        String actualParkCarRes = park.parkCar("A1", car);

        assertEquals("Car:PA4056BH parked successfully!", actualParkCarRes);
        assertEquals(car, park.getParking().get("A1"));
    }

    @Test
    public void shouldReturnCorrectCarMake() {
        String result = DEFAULT_CAR_MAKE;
        Assert.assertEquals(result, car.getMake());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveCarMethodShouldThrowExceptionWhenParkSpotNotExists() {
        Car car = new Car("Ford","PA4455KA");
        park.removeCar("W1", car);
    }

    @Test (expected = IllegalStateException.class)
    public void testRemoveCarMethodShouldThrowExceptionWhenParkSpotIsNotCorrect() {
        Car car = new Car("Ford","PA4455KA");
        Car carTwo = new Car("BMW","4545");
        park.parkCar("A1", car);
        park.parkCar("A2", carTwo);
        park.removeCar("A2", car);
    }
    @Test
    public void testRemoveCarMethodCorrectlyRemoveCarFromCollection() {
        Car car = new Car("Ford","PA4455KA");
        park.parkCar("A1", car);
        park.removeCar("A1", car);

        Assert.assertNull(park.getParking().get("A1"));
    }

    @Test
    public void testMessageRemoveCarMethodWorksCorrectly() {
        Car car = new Car("Ford","PA4455KA");
        park.parkCar("A1", car);
        String actualRemoveCarRes = park.removeCar("A1", car);

        assertEquals("Remove car:PA4056BH successfully!", actualRemoveCarRes);
    }
}