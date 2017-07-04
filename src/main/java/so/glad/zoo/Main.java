package so.glad.zoo;

import org.apache.zookeeper.*;

/**
 * @author palmtale
 * @since 2017/7/4.
 */
public class Main {

    public static void main(String[] args) {
        try {
            ZooKeeper zk = new ZooKeeper("localhost:2181",
                    60000, new Watcher() {

                public void process(WatchedEvent event) {
                    System.out.println("已经触发了" + event.getType() + "事件！");
                }
            });

            String n1 = zk.create("/testRootPathz", "testRootData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);

            String n2 = zk.create("/testRootPathz/testChildPathOne", "testChildDataOne".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(new String(zk.getData("/testRootPath", false, null)));

            System.out.println(zk.getChildren("/testRootPathz", true));

            zk.setData("/testRootPathz/testChildPathOne", "modifyChildDataOne".getBytes(), -1);
            System.out.println("目录节点状态：[" + zk.exists("/testRootPath", true) + "]");

            String n3 = zk.create("/testRootPathz/testChildPathTwo", "testChildDataTwo".getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(new String(zk.getData("/testRootPath/testChildPathTwo", true, null)));

            zk.delete("/testRootPathz/testChildPathTwo", -1);
            zk.delete("/testRootPathz/testChildPathOne", -1);

            zk.delete("/testRootPathz", -1);

            zk.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
