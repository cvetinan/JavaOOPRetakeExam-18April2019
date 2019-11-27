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
        int actualParkingSpotCount = this.park.getParking().size();

        assertEquals(12, actualParkingSpotCount);
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testGetParkingMethodShouldThrowExceptionUnmodifiableCollection() {
        this.park.getParking().remove("A1" );
    }

    @Test (expected = IllegalArgumentException.class)
    public void testParkCarMethodShouldThrowExceptionWhenParkSpotNotExists() {
        this.park.parkCar("W1", this.car);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testParkCarMethodShouldThrowExceptionWhenParkSpotNotAvailable() {
        this.park.parkCar("A1", this.car);
        this.park.parkCar("A1", this.car);
    }

    @Test
    public void testParkCarMethodWorksCorrectly() {
        String actualParkCarRes = park.parkCar("A1", this.car);

        assertEquals("Car:0000 parked successfully!", actualParkCarRes);
        assertEquals(this.car, this.park.getParking().get("A1"));
    }

    @Test
    public void testShouldReturnCorrectCarMake() {
        String result = DEFAULT_CAR_MAKE;
        Assert.assertEquals(result, this.car.getMake());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveCarMethodShouldThrowExceptionWhenParkSpotNotExists() {
        park.removeCar("W1", this.car);
    }

    @Test (expected = IllegalStateException.class)
    public void testRemoveCarMethodShouldThrowExceptionWhenParkSpotIsNotCorrect() {
        Car carTwo = new Car("BMW","4545");
        this.park.parkCar("A1", this.car);
        this.park.parkCar("A2", carTwo);
        this.park.removeCar("A2", this.car);
    }
    @Test
    public void testRemoveCarMethodCorrectlyRemoveCarFromCollection() {
        this.park.parkCar("A1", this.car);
        this.park.removeCar("A1", this.car);

        Assert.assertNull(this.park.getParking().get("A1"));
    }

    @Test
    public void testMessageRemoveCarMethodWorksCorrectly() {
        this.park.parkCar("A1", this.car);
        String actualRemoveCarRes = this.park.removeCar("A1", this.car);

        assertEquals("Remove car:0000 successfully!", actualRemoveCarRes);
    }
}