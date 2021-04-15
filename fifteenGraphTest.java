import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class fifteenGraphTest {
    @Test
    void testNodeGetDepth() {
        Node n = new Node(null, 1);
        int expected = 1;
        int actual = n.getDepth();
        assertEquals(expected, actual);
    }
}