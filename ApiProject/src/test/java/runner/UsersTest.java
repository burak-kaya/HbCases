package runner;


import com.intuit.karate.junit5.Karate;

class UsersTest {

    @Karate.Test
    Karate testUi() {
        return Karate.run("");
    }

}
