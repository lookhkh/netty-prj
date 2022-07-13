package temp;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class URLT {

    @Test
    public void t() throws IOException {
        
        Thread t = new Thread(()->{
            try {
                throw new RuntimeException("Happend at Thread ");
            }catch(RuntimeException e) {
                System.out.println(e.getMessage()+Thread.currentThread().getName());
            }
        });
        
        
        try {
            t.run();
            throw new RuntimeException("happend at main ");
        }catch(RuntimeException e) {
            System.out.println(e.getMessage()+Thread.currentThread().getName());
        }
       
    }
    
}

