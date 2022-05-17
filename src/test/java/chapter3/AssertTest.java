package chapter3;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class AssertTest {
    class InsufficientFundsException extends RuntimeException {
        public InsufficientFundsException(String message) {
            super(message);
        }

        private static final long serialVersionUID = 1L;
    }

    class Account {
        int balance;
        String name;

        Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }
    }

    private Account account;

    @Before
    public void createAccount(){
        account = new Account("accountName");
    }

    @Test
    public void hasPositiveBalance(){
        account.deposit(50);
        assertTrue(account.hasPositiveBalance());
        assertThat(account.getBalance() > 0,is(true));
        assertThat(account.getName(),is("accountName"));
        assertThat(account.getName(),not(equalTo("test")));
        assertThat(account.getName(),is(notNullValue())); // 불필요, 가치없음
    }

    @Test
    public void depositIncreasesBalance(){
        int initialBalance = account.getBalance();
        account.deposit(100);
        assertTrue(account.getBalance() > initialBalance);
    }

    @Test
    public void collectionTest(){
        //assertThat(new String[]{"a","b","c"},equalTo(new String[]{"a","b"}));
        //assertThat(Arrays.asList(new String[]{"a"}),equalTo(Arrays.asList(new String[]{"a","ab"})));
        assertThat(new String[]{"a","b"},equalTo(new String[]{"a","b"}));
    }

    @Test
    public void numTest(){
        //assertThat(2.32 * 3 , equalTo(6.96));
        assertTrue(Math.abs((2.32*3)-6.96) < 0.0005);
        assertThat(2.32*3, closeTo(6.96, 0.0005));
    }

    @Test//(expected = InsufficientFundsException.class)
    public void throwsWhenWithdraingTooMuch(){
        //account.withdraw(100);
        try{
            account.withdraw(100);
            fail(); //강제로 실패
        }catch (InsufficientFundsException ex){
            assertThat(ex.getMessage(), equalTo("balance only 0"));
        }

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void exceptionRule(){
        thrown.expect(InsufficientFundsException.class);
        thrown.expectMessage("balance only 0");

        account.withdraw(100);
    }

    @Test
    public void readsFromTestFile() throws IOException{
        String filename = "test.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("test data");
        writer.close();
    }

    @After
    public void deleteForReadsFromTestFile() {
        new File("test.txt").delete();
    }
}
