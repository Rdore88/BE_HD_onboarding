package portal.User;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.hamcrest.Matchers;

public class UserPOJOTest {

    @Test
    public void shouldConstruct() {
        User user = new User("Robby", "password123");

        Assertions.assertThat(user)
                .as("not a null reference")
                .isNotNull();

        Assertions.assertThat(user.getUsername())
                .as("There is a name populated")
                .isNotBlank();

        Assertions.assertThat(user.getPassword())
                .as("There is a password populated")
                .isNotBlank();


        Assert.assertThat(user.getPassword(), Matchers.equalToIgnoringCase("password123"));
        Assert.assertThat(user.getUsername(), Matchers.equalToIgnoringCase("robby"));
        Assert.assertThat(user.getInfo(), Matchers.equalToIgnoringCase("username is robby and password is password123"));
    }
}