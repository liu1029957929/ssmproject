package MyTest;

import com.atguigu.atcrowdfunding.util.Page;
import org.junit.Test;

public class Test1 {
    {
        System.out.println("haha");
    }
    private Page page = new Page(1,2);

    @Test
    public void test1(){
        /*
        *   haha
            com.atguigu.atcrowdfunding.util.Page@7dc5e7b4
        * */
        System.out.println(page);
    }

    @Test
    public void test2(){
        /*
        *   haha
            com.atguigu.atcrowdfunding.util.Page@7dc5e7b4
        * */
        System.out.println(page);
    }
}
