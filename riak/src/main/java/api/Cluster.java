package api;

import com.basho.riak.client.api.RiakClient;
import com.basho.riak.client.core.RiakCluster;
import com.basho.riak.client.core.RiakNode;

import java.util.LinkedList;

/**
 * Created by shavene on 3/21/2017.
 */
public class Cluster {
    private RiakClient client;

    public Cluster() {
        // Keep 10-100 connections to each node.
        RiakNode.Builder nodeTemplate = new RiakNode.Builder();
//                .withMinConnections(10)
//                .withMaxConnections(100);

        // Create a list of nodes to connect to.  must use protocol buffer method.
        LinkedList<RiakNode> nodes = new LinkedList<RiakNode>();
        nodes.add(nodeTemplate.withRemoteAddress("172.18.81.155").withRemotePort(8087).build());
        nodes.add(nodeTemplate.withRemoteAddress("172.18.81.156").withRemotePort(8087).build());
        nodes.add(nodeTemplate.withRemoteAddress("172.17.84.90").withRemotePort(8087).build());


        //  attempts (retries) to 5
        RiakCluster cluster = RiakCluster.builder(nodes)
//                .withExecutionAttempts(5)
                .build();

        // start cluster
        cluster.start();

//        List<RiakNode> nodex = cluster.getNodes();
//        for (RiakNode node : nodex)
//        {
//            RiakNode.State state = node.getNodeState();
//            System.out.println(state);
//        }

        RiakClient client = new RiakClient(cluster);

        this.setClient(client);
    }

    public RiakClient getClient() {
        return client;
    }

    public void setClient(RiakClient client) {
        this.client = client;
    }
}
