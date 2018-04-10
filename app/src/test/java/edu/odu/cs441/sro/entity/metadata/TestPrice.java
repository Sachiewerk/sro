package edu.odu.cs441.sro.entity.metadata;

import org.junit.Test;
import java.util.UUID;

/**
 * Created by michael on 2/16/18.
 * Test edu.odu.cs441.sro.Model.metadata.Price
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class TestPrice {

    private final UUID uuid = UUID.randomUUID();

    @Test
    public void TestPrice() {
        Price price = new Price(uuid, "54");

        assertEquals("", price.getPrice(), "54.00");
        assertEquals("", price.toString(), "$54.00");
    }

    @Test
    public void TestToString() throws Exception {
    }

}