package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class BurgerTest {

    private Burger burger;

    @Mock
    private Bun mockBun;

    @Mock
    private Ingredient mockIngredient1;

    @Mock
    private Ingredient mockIngredient2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        when(mockBun.getName()).thenReturn("black bun");
        when(mockBun.getPrice()).thenReturn(100.0f);

        burger.setBuns(mockBun);
        assertSame(mockBun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        when(mockIngredient1.getName()).thenReturn("cutlet");
        when(mockIngredient1.getPrice()).thenReturn(150.0f);
        when(mockIngredient1.getType()).thenReturn(IngredientType.FILLING);

        burger.addIngredient(mockIngredient1);
        assertEquals(1, burger.ingredients.size());
        assertSame(mockIngredient1, burger.ingredients.get(0));
    }

    @Test
    public void removeIngredientTest() {
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);

        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertSame(mockIngredient2, burger.ingredients.get(0));
    }

    @Test
    public void moveIngredientTest() {
        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);

        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        burger.moveIngredient(0, 1); // move index 0 to 1

        assertEquals(mockIngredient2, burger.ingredients.get(0));
        assertEquals(mockIngredient1, burger.ingredients.get(1));
    }
    @Test
    public void generateCorrectReceiptWithIngredientsTest() {
        when(mockBun.getName()).thenReturn("black bun");

        when(mockIngredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(mockIngredient1.getName()).thenReturn("hot sauce");

        when(mockIngredient2.getType()).thenReturn(IngredientType.FILLING);
        when(mockIngredient2.getName()).thenReturn("dinosaur");

        when(mockBun.getPrice()).thenReturn(100.0f);
        when(mockIngredient1.getPrice()).thenReturn(100.0f);
        when(mockIngredient2.getPrice()).thenReturn(200.0f);

        burger.setBuns(mockBun);
        burger.addIngredient(mockIngredient1);
        burger.addIngredient(mockIngredient2);

        String expected = String.format("(==== black bun ====)%n" +
                "= sauce hot sauce =%n" +
                "= filling dinosaur =%n" +
                "(==== black bun ====)%n" +
                "%n" +
                "Price: 500,000000%n");

        String actual = burger.getReceipt();

        assertEquals(expected, actual);
    }

    @Test
    public void generateReceiptWithNoIngredientsTest() {
        when(mockBun.getName()).thenReturn("white bun");
        when(mockBun.getPrice()).thenReturn(200.0f);

        burger.setBuns(mockBun);

        String expected = String.format("(==== white bun ====)%n" +
                "(==== white bun ====)%n" +
                "%n" +
                "Price: 400,000000%n");

        String actual = burger.getReceipt();

        assertEquals(expected, actual);
    }

}