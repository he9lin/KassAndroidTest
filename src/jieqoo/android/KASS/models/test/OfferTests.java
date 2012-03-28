package jieqoo.android.KASS.models.test;

import jieqoo.android.KASS.models.Offer;
import junit.framework.TestCase;

public class OfferTests extends TestCase {
	
	private Offer offer;
	
	public OfferTests(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		offer = new Offer();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public final void testDefaultPrice() {
		assertEquals(Offer.INVALID_OFFER_PRICE, offer.getPrice());
		assertFalse(offer.isPriceSet());
	}
	
	public final void testSetListingId() {
		offer.setListingId("123");
		assertEquals("123", offer.getListingId());
	}
	
	public final void testSetListingPrice() {
		double price = 37.37;
		offer.setListingPrice(price);
		assertEquals(price, offer.getListingPrice());
	}
	
	public final void testSetPrice() {
		double price = 37.37;
		offer.setPrice(price);
		assertEquals(price, offer.getPrice());
	}
	
	public final void testSetMessage() {
		String message = "Raise your price";
		offer.setMessage(message);
		assertEquals(message, offer.getMessage());
	}
	
	public final void testClear() {
		double price = 37.37;
		String message = "Raise your price";
		offer.setPrice(price);
		offer.setMessage(message);
		offer.clear();
		assertEquals(Offer.INVALID_OFFER_PRICE, offer.getPrice());
		assertEquals(null, offer.getMessage());
		assertEquals(null, offer.getId());
	}
	
	public final void testIsNew() {
		assertTrue(offer.isNew());
		offer.setId("123");
		assertFalse(offer.isNew());
	}
	
	public final void testSaveNewRecord() {
		
	}
	
	public final void testSaveExistingRecord() {
		
	}
}
