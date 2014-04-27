package ZooKeeperTEST;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
 
public class Polaczenie {
	java.util.concurrent.CountDownLatch connectedSignal = new java.util.concurrent.CountDownLatch(
			1);

	ZooKeeper zk;
	List<String> listaZNode;
	private Watcher w;

	Polaczenie() throws KeeperException, InterruptedException {

		try {
			zk = new ZooKeeper("10.16.20.1", 2181, new Watcher() {

				public void process(WatchedEvent event) {
					if (event.getState() == KeeperState.SyncConnected) {
						connectedSignal.countDown();
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}

		listaZNode = zk.getChildren("/zoo", null);

		w = new Watcher() {

			public void process(WatchedEvent event) {
				if (event.getType().equals(Watcher.Event.EventType.NodeDeleted)) {
					System.out.format("Zabito %s \n", event.getPath());
				}
				try {
					zk.exists(event.getPath(), this);
				} catch (KeeperException | InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		try {
			addWatchers(w);
		} catch (KeeperException | InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			System.out.println(zk.getChildren("/zoo", null));
		} catch (KeeperException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void addWatchers(Watcher w) throws KeeperException,
			InterruptedException {
		for (String i : listaZNode) {
			zk.exists("/zoo/" + i, w);
		}
	}

	public void createZNode(String nodeName, String dane)
			throws KeeperException, InterruptedException {
		zk.create("/zoo/" + nodeName, dane.getBytes(), Ids.OPEN_ACL_UNSAFE,
				CreateMode.PERSISTENT);
		listaZNode.add(nodeName);
		zk.exists("/zoo/" + nodeName, w);
		Stat stat = new Stat();
		byte[] data = zk.getData("/zoo/" + nodeName, null, stat);
		System.out.println(new String(data));
	}
}
