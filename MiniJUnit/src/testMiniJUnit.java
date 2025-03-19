public class testMiniJUnit {
    public static void main(String[] args) {

    }
    @Test
    void log(){
        System.out.println("执行Test");
    }

    void test1(){
        System.out.println("执行test1");
    }

    void test2(){
        System.out.println("执行无参test2");
    }

    void test2(int i){
        System.out.println("执行有参test2");
    }

}
