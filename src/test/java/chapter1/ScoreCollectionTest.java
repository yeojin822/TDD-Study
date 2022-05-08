package chapter1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ScoreCollectionTest {
//    @Test
//    public void test(){
//        fail("Not yet implemented");
//    }
    @Test
    public void answersArithmeticMeanOfTwoNumbers(){
        //준비
        ScoreCollection collection = new ScoreCollection();
        collection.add(()-> 5);
        collection.add(()-> 7);

        //실행
        int actualResult = collection.arithmeticMean();

        //단언
        assertThat(actualResult).isEqualTo(6);
    }
}