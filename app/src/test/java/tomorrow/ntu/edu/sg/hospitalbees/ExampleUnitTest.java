package tomorrow.ntu.edu.sg.hospitalbees;

import org.junit.Test;

import static org.junit.Assert.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import android.content.SharedPreferences;

@RunWith(MockitoJUnitRunner.class)

public class    ExampleUnitTest {
    private static final String UserName = "username123";
    private static final String PassWord = "pw123";
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Mock
    String mMockContext = "ppppppppppppppp";

    @Test
    public void testLoginActivity(){
        if (PassWord != mMockContext)
            System.out.println("try again");
        else
            System.out.println("done");
    }


}