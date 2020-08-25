package productAndconsumer;
/*
if判断因为只有一次判断可能会产生虚假唤醒的问题
所以使用while循环就不会产生这样的问题
 */
public class TestA {
    public static void main(String[] args) {
        Data data = new Data();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        data.increment();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "A").start();

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        data.decrement();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "B").start();
        }
}
class Data{
    private  int num=0;
    //对资源类进行加操作
    public  synchronized  void increment() throws Exception{
        while(num!=0){
            //等待
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        //制造完之后通知其他线程即消费者进行消费
        this.notifyAll();
    }
    //对资源进行减操作
    public synchronized void decrement()throws  Exception{
        while(num==0){
            //等待
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "=>" + num);
        //消费完了通知生产者生产
        this.notifyAll();
    }
}
